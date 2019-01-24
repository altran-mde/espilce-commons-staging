/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource;

import static org.eclipse.core.resources.IResourceChangeEvent.POST_BUILD;
import static org.eclipse.core.resources.IResourceChangeEvent.POST_CHANGE;
import static org.eclipse.core.resources.IResourceChangeEvent.PRE_BUILD;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ISafeRunnable;

/**
 * Utilities for {@linkplain org.eclipse.core.resources.IWorkspace Eclipse
 * Workspace}.
 * 
 * @since 0.4
 */
public class WorkspaceUtils {
	/**
	 * Executes <code>work</code> only after all workspace changes are done.
	 * 
	 * <p>
	 * <b>Caution:</b> This method works on best-effort basis, and might not be
	 * 100&nbsp;% reliable in all cases!
	 * </p>
	 * 
	 * @param work
	 *            Code to execute after all workspace changes are done.
	 * @throws InterruptedException
	 *             If waiting for the workspace was interrupted.
	 * @throws Exception
	 *             Forwarded if thrown by <code>work</code>.
	 * @since 0.4
	 */
	public static void waitForWorkspaceChanges(final ISafeRunnable work) throws InterruptedException, Exception {
		final AtomicBoolean finished = new AtomicBoolean(false);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			private boolean buildStarted = false;
			private boolean changed = false;
			
			private boolean isType(final IResourceChangeEvent event, final int typeFlag) {
				return (event.getType() & typeFlag) != 0;
			}
			
			@Override
			public void resourceChanged(final IResourceChangeEvent event) {
				if (isType(event, PRE_BUILD)) {
					this.buildStarted = true;
				}
				if (isType(event, POST_BUILD)) {
					this.buildStarted = false;
				}
				if (isType(event, POST_CHANGE)) {
					this.changed = true;
				}
				
				if (!this.buildStarted && this.changed) {
					finished.set(true);
					ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
				}
				
			}
		}, PRE_BUILD | POST_CHANGE | POST_BUILD);
		
		work.run();
		
		while (!finished.get()) {
			Thread.sleep(272);
		}
	}
}
