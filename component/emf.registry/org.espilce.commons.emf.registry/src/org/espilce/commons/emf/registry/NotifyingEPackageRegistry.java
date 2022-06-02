/*******************************************************************************
 * Copyright (C) 2020 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.registry;

import java.io.Serializable;
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

public class NotifyingEPackageRegistry implements EPackage.Registry, Serializable {
	private static final long serialVersionUID = -8041178609149335697L;

	// Delegating instead of extending to ensures that default java.util.Map
	// implementations will be invoked instead of optimized HashMap implementation
	// which alternates the Map contents directly
	private final EPackage.Registry delegate = new NotifyingEPackageRegistryImpl();
	
	public EPackage getEPackage(String nsURI) {
		return delegate.getEPackage(nsURI);
	}

	public EFactory getEFactory(String nsURI) {
		return delegate.getEFactory(nsURI);
	}

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	public Object get(Object key) {
		return delegate.get(key);
	}

	public Object put(String key, Object value) {
		return delegate.put(key, value);
	}

	public Object remove(Object key) {
		return delegate.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		delegate.putAll(m);
	}

	public void clear() {
		delegate.clear();
	}

	public Set<String> keySet() {
		return delegate.keySet();
	}

	public Collection<Object> values() {
		return delegate.values();
	}

	public Set<Entry<String, Object>> entrySet() {
		return delegate.entrySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotifyingEPackageRegistry other = (NotifyingEPackageRegistry) obj;
		if (delegate == null) {
			if (other.delegate != null)
				return false;
		} else if (!delegate.equals(other.delegate))
			return false;
		return true;
	}

	private static final class NotifyingEPackageRegistryImpl extends EPackageRegistryImpl {
		private static final long serialVersionUID = -5377442334573160215L;

		private static final String EXSTENSION_POINT = EspilceCommonsEmfRegistryBundleActivator.SYMBOLIC_NAME
				+ ".epackage_registry_observer";

		private static final String ELEMENT_OBSERVER = "observer";
		private static final String ATTR_CLASS = "class";
		private static final String ATTR_URI = "uri";
		
		@Override
		public Object put(String uri, Object value) {
			final Object previousValue = super.put(uri, value);
			if (previousValue != value) {
				final List<Notification> notificationChain = new ArrayList<>(2);
				addNotification(Notification.Type.UNREGISTERED, uri, previousValue, notificationChain);
				addNotification(Notification.Type.REGISTERED, uri, value, notificationChain);
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
		public Object remove(Object uri) {
			final Object previousValue = super.remove(uri);
			if (previousValue != null) {
				final List<Notification> notificationChain = new ArrayList<>(1);
				addNotification(Notification.Type.UNREGISTERED, uri, previousValue, notificationChain);
				notifyObservers(notificationChain);
			}
			return previousValue;
		}

		@Override
		public void clear() {
			final List<Notification> notificationChain = new ArrayList<>(super.size());
			for (Map.Entry<String, Object> entry : super.entrySet()) {
				addNotification(Notification.Type.UNREGISTERED, entry.getKey(), entry.getValue(), notificationChain);
			}
			super.clear();
			notifyObservers(notificationChain);
		}
		
		// Prevent direct alternations to the map. Enforce that alternations are only
		// done via put/remove methods as listed above

		@Override
		public Set<String> keySet() {
			return Collections.unmodifiableSet(super.keySet());
		}

		@Override
		public Collection<Object> values() {
			return Collections.unmodifiableCollection(super.values());
		}

		@Override
		public Set<Entry<String, Object>> entrySet() {
			return Collections.unmodifiableSet(super.entrySet());
		}
		
		// Notification support

		private void addNotification(Notification.Type type, Object uri, Object ePackage,
				List<Notification> notificationChain) {
			if (type != null && uri instanceof String && ePackage instanceof EPackage) {
				notificationChain.add(new Notification(type, (String) uri, (EPackage) ePackage));
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
				for (IConfigurationElement observerElement : extension.getConfigurationElements()) {
					notifyObserver(observerElement, notificationChain);
				}
			}
		}

		private void notifyObserver(IConfigurationElement observerElement, List<Notification> notificationChain) {
			if (!observerElement.isValid() || !ELEMENT_OBSERVER.equals(observerElement.getName())) {
				return;
			}
			final String uri = observerElement.getAttribute(ATTR_URI);
			final Iterator<Notification> notifications;
			if (uri == null) {
				// Send all notifications
				notifications = notificationChain.iterator();
			} else {
				// Filter notifications for matching uri
				notifications = notificationChain.stream().filter(n -> Objects.equals(uri, n.uri)).iterator();
			}
			if (!notifications.hasNext()) {
				// Skip if no (matching) notifications need to be send
				return;
			}
			try {
				EPackageRegistryObserver observer = EPackageRegistryObserver.class
						.cast(observerElement.createExecutableExtension(ATTR_CLASS));
				notifications.forEachRemaining(n -> n.notify(observer));
			} catch (CoreException e) {
				EspilceCommonsEmfRegistryBundleActivator.getLog()
						.log(new Status(IStatus.ERROR, EspilceCommonsEmfRegistryBundleActivator.SYMBOLIC_NAME,
								String.format("Failed to notify EPackageRegistryObserver of plug-in %s: %s",
										observerElement.getContributor().getName(), observerElement.getAttribute(ATTR_CLASS)),
								e));
			}
		}
	}

	private static final class Notification {
		enum Type {
			REGISTERED, UNREGISTERED
		};

		private final Type type;
		private final String uri;
		private final EPackage ePackage;

		Notification(Type type, String uri, EPackage ePackage) {
			this.type = type;
			this.uri = uri;
			this.ePackage = ePackage;
		}

		void notify(EPackageRegistryObserver observer) {
			switch (type) {
			case REGISTERED:
				observer.ePackageRegistered(uri, ePackage);
				break;
			case UNREGISTERED:
				observer.ePackageUnregistered(uri, ePackage);
				break;
			}
		}
	}
}
