package org.espilce.commons.emf.testsupport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;

public class ClassloaderTestModelLoader extends ATestModelLoadHelper {

	@Override
	protected String getContentTypeId(Class<?> classInContext, String resourceRelativePath) {
		return null;
	}

	@Override
	public InputStream getContents(Class<?> classInContext, String resourceRelativePath) throws IOException {
		File file = toFile(classInContext, resourceRelativePath);
		if (file != null) {
			return new BufferedInputStream(new FileInputStream(file));
		}

		return classInContext.getClassLoader().getResourceAsStream(resourceRelativePath);
	}

	@Override
	public List<URL> findMatchingResources(Class<?> classInContext, String parentRelativePath) {
		File file = toFile(classInContext, parentRelativePath);

		if (file != null) {
			Path basePath = Paths.get(file.getAbsolutePath());
			try (Stream<Path> fileWalker = Files.walk(basePath)) {
				return fileWalker.filter(Files::isRegularFile).map(path -> {
					try {
						return path.toUri().toURL();
					} catch (MalformedURLException e) {
						return null;
					}
				}).filter(Objects::nonNull).collect(Collectors.toList());
			} catch (IOException e1) {
				// ignore
			}
		}

		Enumeration<URL> resources;
		try {
			resources = classInContext.getClassLoader().getResources(parentRelativePath);
			return Collections.list(resources);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public URI toLocalmostUri(Class<?> classInContext, String resourceRelativePath) {
		File file = toFile(classInContext, resourceRelativePath);

		if (file != null) {
			return URI.createFileURI(file.getAbsolutePath());
		}

		return URI.createFileURI(resourceRelativePath);
	}

	protected File toFile(Class<?> classInContext, String resourceRelativePath) {
		File file = new File(resourceRelativePath);

		if (file.canRead()) {
			return file.getAbsoluteFile();
		}

		return null;
	}
}
