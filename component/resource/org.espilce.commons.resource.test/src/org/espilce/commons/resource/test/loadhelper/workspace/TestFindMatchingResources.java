package org.espilce.commons.resource.test.loadhelper.workspace;

import static org.espilce.commons.emf.resource.WorkspaceUtils.waitForWorkspaceChanges;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
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

	private static IProject createProject(final String name) throws Exception {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		project.create(null);
		project.open(null);
		return project;
	}

	private static IFolder createFolder(final IContainer container, final String name) throws Exception {
		final IFolder folder = container.getFolder(Path.fromPortableString(name));
		folder.create(true, true, null);
		return folder;
	}

	private static IFile createFile(final IContainer container, final String name) throws Exception {
		final IFile file = container.getFile(Path.fromPortableString(name));
		file.create(IOUtils.toInputStream(name + " in " + container.getFullPath().toPortableString()), true, null);
		return file;
	}

	@Before
	public void createProjects() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.emptyDir = createProject(emptyDir());

			this.manyDirectFilesDir = createProject(manyDirectFilesDir());
			createFile(this.manyDirectFilesDir, file1());
			createFile(this.manyDirectFilesDir, file2());
			createFile(this.manyDirectFilesDir, file3());

			this.manyNestedFilesDir = createProject(manyNestedFilesDir());
			createFolder(this.manyNestedFilesDir, emptyDir());
			final IFolder f1 = createFolder(this.manyNestedFilesDir, manyDirectFilesDir());
			createFile(f1, file1());
			createFile(f1, file2());
			final IFolder f2 = createFolder(this.manyNestedFilesDir, manyNestedFilesDir());
			createFile(f2, file1());
			createFile(f2, file2());
			final IFolder f3 = createFolder(f2, singleFileNestedDir());
			final IFolder f4 = createFolder(f3, dir());
			createFile(f4, file1());

			final IFolder f5 = createFolder(this.manyNestedFilesDir, singleFileDir());
			createFile(f5, file1());

			final IFolder f6 = createFolder(this.manyNestedFilesDir, someDir());
			final IFolder f7 = createFolder(f6, dir());
			final IFolder f8 = createFolder(f7, dir());
			createFile(f8, file2());

			createFile(this.manyNestedFilesDir, file3());

			this.singleDirDir = createProject(singleDirDir());
			createFolder(this.singleDirDir, emptyDir());

			this.singleFileDir = createProject(singleFileDir());
			createFile(this.singleFileDir, file1());

			this.singleFileNestedDir = createProject(singleFileNestedDir());
			final IFolder f9 = createFolder(this.singleFileNestedDir, dir());
			final IFolder f10 = createFolder(f9, dir());
			createFile(f10, file1());

			this.some = createProject(someDir());
			final IFolder f11 = createFolder(this.some, dir());
			createFile(f11, file1());
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
