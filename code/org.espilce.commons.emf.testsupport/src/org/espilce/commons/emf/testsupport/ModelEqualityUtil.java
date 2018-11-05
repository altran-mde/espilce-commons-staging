package org.espilce.commons.emf.testsupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.espilce.commons.emf.textrenderer.EmfStringRenderer;

public class ModelEqualityUtil {
	private static ModelEqualityUtil instance;

	protected ModelEqualityUtil() {
	}

	public static ModelEqualityUtil getInstance() {
		if (instance == null) {
			instance = new ModelEqualityUtil();
		}

		return instance;
	}

	/**
	 * Asserts that two Ecore Resources are equal. That is, they have {@link EcoreUtil#equals(List, List) equal}
	 * {@link Resource#getContents() contents}. If they are not equal, an {@link AssertionError} is thrown with a
	 * human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected Ecore Resource
	 * @param actual
	 *            actual Ecore Resource
	 */
	public void assertModelEquals(final Resource expected, final Resource actual) {
		assertModelEquals(expected.getContents(), actual.getContents());
	}

	/**
	 * Asserts that two EObject lists are {@link EcoreUtil#equals(List, List) equal}. If they are not, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject lists
	 * @param actual
	 *            actual EObject lists
	 */
	public void assertModelEquals(final List<EObject> expected, final List<EObject> actual) {
		final boolean equals = EcoreUtil.equals(expected, actual);
		if (!equals) {
			assertEquals("EObjects do not match.", new EmfStringRenderer(expected).render(),
					new EmfStringRenderer(actual).render());
			assertTrue("EObjects do not match. expected: " + expected + ", actual: " + actual, equals);
		}
	}

	/**
	 * Asserts that two EObjects are {@link EcoreUtil#equals(EObject, EObject) equal}. If they are not, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject
	 * @param actual
	 *            actual EObject
	 */
	public void assertModelEquals(final EObject expected, final EObject actual) {
		final boolean equals = EcoreUtil.equals(expected, actual);
		if (!equals) {
			assertEquals("EObjects do not match.", new EmfStringRenderer(expected).render(),
					new EmfStringRenderer(actual).render());
			assertTrue("EObjects do not match. expected: " + expected + ", actual: " + actual, equals);
		}
	}

}
