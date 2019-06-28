/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource.test.loadhelper.workspace;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.After;
import org.junit.Before;

public class TestFindMatchingResources extends ATestFindMatchingResources {
	protected IProject emptyDir;
	protected IProject manyDirectFilesDir;
	protected IProject manyNestedFilesDir;
	protected IProject singleDirDir;
	protected IProject singleFileDir;
	protected IProject singleFileNestedDir;
	private IProject some;
	
	// private static IFile createFile(final IContainer container, final String
	// name) throws Exception {
	// final IFile file = container.getFile(Path.fromPortableString(name));
	// file.create(IOUtils.toInputStream(name + " in " +
	// container.getFullPath().toPortableString()), true, null);
	// return file;
	// }
	
	@Before
	public void createProjects() throws Exception {
		waitForWorkspaceChanges(() -> {
			// @formatter:off
			this.emptyDir = new ProjectBuilder(emptyDir())
					.build();
			
			this.manyDirectFilesDir = new ProjectBuilder(manyDirectFilesDir())
					.createFile(file1())
					.createFile(file2())
					.createFile(file3())
					.build();
			
			this.manyNestedFilesDir = new ProjectBuilder(manyNestedFilesDir())
					.createFolder(emptyDir())
					.newFolder(manyDirectFilesDir())
						.createFile(file1())
						.createFile(file2())
					.end()
					.newFolder(manyNestedFilesDir())
						.newFolder(singleFileNestedDir())
							.newFolder(dir())
								.createFile(file1())
							.end()
						.end()
						.createFile(file1())
						.createFile(file2())
					.end()
					.newFolder(singleFileDir())
						.createFile(file1())
					.end()
					.newFolder(someDir())
						.newFolder(dir())
							.newFolder(dir())
								.createFile(file2())
							.end()
						.end()
					.end()
					.createFile(file3())
					.build();
			
			this.singleDirDir = new ProjectBuilder(singleDirDir())
					.createFolder(emptyDir())
					.build();
			
			this.singleFileDir = new ProjectBuilder(singleFileDir())
					.createFile(file1())
					.build();
			
			this.singleFileNestedDir = new ProjectBuilder(singleFileNestedDir())
					.newFolder(dir())
						.newFolder(dir())
							.createFile(file1())
						.end()
					.end()
					.build();
			
			this.some = new ProjectBuilder(someDir())
					.newFolder(dir())
						.createFile(file1())
					.end()
					.build();
			// @formatter:on
		});
	}
	
	private static void destroyProject(final IProject project) throws Exception {
		if (project != null) {
			waitForWorkspaceChanges(() -> project.delete(true, true, null));
		}
	}
	
	@After
	public void destroyProjects() throws Exception {
		destroyProject(this.emptyDir);
		destroyProject(this.manyDirectFilesDir);
		destroyProject(this.manyNestedFilesDir);
		destroyProject(this.singleDirDir);
		destroyProject(this.singleFileDir);
		destroyProject(this.singleFileNestedDir);
		destroyProject(this.some);
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected List<URL> findMatchingResources(final String parentRelativePath) {
		return super.findMatchingResources(parentRelativePath).stream().filter(u -> !u.getFile().endsWith(".project"))
				.collect(Collectors.toList());
	}
	
}
