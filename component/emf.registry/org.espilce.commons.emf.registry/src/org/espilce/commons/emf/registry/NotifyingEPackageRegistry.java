/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

public class NotifyingEPackageRegistry implements EPackage.Registry {
	private static final String EXSTENSION_POINT = EspilceCommonsEmfRegistryBundleActivator.SYMBOLIC_NAME
			+ ".epackage_registry_observer";
	private static final String ELEMENT_OBSERVER = "observer";
	private static final String ATTR_CLASS = "class";
	private static final String ATTR_NSURI = "nsURI";

	// Delegating instead of extending to ensures that default Map implementations
	// will be invoked
	private final EPackage.Registry delegate;

	public NotifyingEPackageRegistry() {
		this(new EPackageRegistryImpl());
	}

	protected NotifyingEPackageRegistry(EPackage.Registry delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object put(String nsURI, Object value) {
		final Object previousValue = delegate.put(nsURI, value);
		if (previousValue != value) {
			final List<Notification> notificationChain = new ArrayList<>(2);
			addNotification(Notification.Type.UNREGISTERED, nsURI, previousValue, notificationChain);
			addNotification(Notification.Type.REGISTERED, nsURI, value, notificationChain);
			notifyObservers(notificationChain);
		}
		return previousValue;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		// Too much effort (also performance) to build a notification chain
		// as we need to match the previous values with the new values,
		// so just delegate to put
		map.entrySet().forEach(e -> put(e.getKey(), e.getValue()));
	}

	@Override
	public Object remove(Object nsURI) {
		final Object previousValue = delegate.remove(nsURI);
		final List<Notification> notificationChain = new ArrayList<>(1);
		addNotification(Notification.Type.UNREGISTERED, nsURI, previousValue, notificationChain);
		notifyObservers(notificationChain);
		return previousValue;
	}

	@Override
	public void clear() {
		final List<Notification> notificationChain = new ArrayList<>(delegate.size());
		for (Map.Entry<String, Object> entry : delegate.entrySet()) {
			addNotification(Notification.Type.UNREGISTERED, entry.getKey(), entry.getValue(), notificationChain);
		}
		delegate.clear();
		notifyObservers(notificationChain);
	}

	private void addNotification(Notification.Type type, Object nsURI, Object ePackage,
			List<Notification> notificationChain) {
		if (type != null && nsURI instanceof String && ePackage instanceof EPackage) {
			notificationChain.add(new Notification(type, (String) nsURI, (EPackage) ePackage));
		}
	}

	private void notifyObservers(List<Notification> notificationChain) {
		if (notificationChain == null || notificationChain.isEmpty()) {
			// Skip if no notifications need to be send
			return;
		}
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXSTENSION_POINT);
		if (point == null) {
			return;
		}
		for (IExtension extension : point.getExtensions()) {
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				if (!element.isValid() || !ELEMENT_OBSERVER.equals(element.getName())) {
					continue;
				}
				String nsURI = element.getAttribute(ATTR_NSURI);
				Iterator<Notification> notifications;
				if (nsURI == null) {
					// Send all notifications
					notifications = notificationChain.iterator();
				} else {
					// Filter notifications for matching nsURI
					notifications = notificationChain.stream().filter(n -> Objects.equals(nsURI, n.nsURI)).iterator();
				}
				if (!notifications.hasNext()) {
					// Skip if no (matching) notifications need to be send
					continue;
				}
				try {
					EPackageRegistryObserver observer = EPackageRegistryObserver.class
							.cast(element.createExecutableExtension(ATTR_CLASS));
					notifications.forEachRemaining(n -> n.notify(observer));
				} catch (CoreException e) {
					EspilceCommonsEmfRegistryBundleActivator.getLog()
							.log(new Status(IStatus.ERROR, EspilceCommonsEmfRegistryBundleActivator.SYMBOLIC_NAME,
									String.format("Failed to notify EPackageRegistryObserver of plug-in %s: %s",
											element.getContributor().getName(), element.getAttribute(ATTR_CLASS)),
									e));
				}
			}
		}
	}

	// Prevent alternations to the map

	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet(delegate.keySet());
	}

	@Override
	public Collection<Object> values() {
		return Collections.unmodifiableCollection(delegate.values());
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return Collections.unmodifiableSet(delegate.entrySet());
	}

	// Plain delegate methods

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean containsKey(Object nsURI) {
		return delegate.containsKey(nsURI);
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public Object get(Object nsURI) {
		return delegate.get(nsURI);
	}

	@Override
	public EPackage getEPackage(String nsURI) {
		return delegate.getEPackage(nsURI);
	}

	@Override
	public EFactory getEFactory(String nsURI) {
		return delegate.getEFactory(nsURI);
	}

	private static final class Notification {
		enum Type {
			REGISTERED, UNREGISTERED
		};

		private final Type type;
		private final String nsURI;
		private final EPackage ePackage;

		Notification(Type type, String nsURI, EPackage ePackage) {
			this.type = type;
			this.nsURI = nsURI;
			this.ePackage = ePackage;
		}

		void notify(EPackageRegistryObserver observer) {
			switch (type) {
			case REGISTERED:
				observer.ePackageRegistered(nsURI, ePackage);
				break;
			case UNREGISTERED:
				observer.ePackageUnregistered(nsURI, ePackage);
				break;
			}
		}
	}
}
