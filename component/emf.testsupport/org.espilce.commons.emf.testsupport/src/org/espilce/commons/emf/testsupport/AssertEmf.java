/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.testsupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.emf.textrenderer.EmfStringRenderer;

/**
 * Compares EMF artifacts and renders the differences in human-readable manner,
 * if any.
 * 
 * @since 0.2
 */
public class AssertEmf {
	/**
	 * Asserts that two EMF Resources are equal. That is, they have
	 * {@linkplain EcoreUtil#equals(List, List) equal}
	 * {@linkplain Resource#getContents() contents}. If they are not equal, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the
	 * differences.
	 *
	 * @param expected
	 *            expected EMF Resource
	 * @param actual
	 *            actual EMF Resource
	 * @since 0.1
	 */
	public static void assertModelEquals(final @NonNull Resource expected, final @NonNull Resource actual) {
		assertModelEquals(expected.getContents(), actual.getContents());
	}
	
	/**
	 * Asserts that two EObject lists are
	 * {@linkplain EcoreUtil#equals(List, List) equal}. If they are not, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the
	 * differences.
	 *
	 * @param expected
	 *            expected EObject lists
	 * @param actual
	 *            actual EObject lists
	 * @since 0.1
	 */
	public static void assertModelEquals(
			final @NonNull List<@NonNull EObject> expected,
			final @NonNull List<@NonNull EObject> actual
	) {
		final boolean equals = EcoreUtil.equals(expected, actual);
		if (!equals) {
			assertEquals(
					"EObjects do not match.", new EmfStringRenderer(expected).render(),
					new EmfStringRenderer(actual).render()
			);
			assertTrue("EObjects do not match. expected: " + expected + ", actual: " + actual, equals);
		}
	}
	
	/**
	 * Asserts that two EObjects are
	 * {@linkplain EcoreUtil#equals(EObject, EObject) equal}. If they are not,
	 * an {@link AssertionError} is thrown with a human-readable rendering of
	 * the differences.
	 *
	 * @param expected
	 *            expected EObject
	 * @param actual
	 *            actual EObject
	 * @since 0.1
	 */
	public static void assertModelEquals(final @NonNull EObject expected, final @NonNull EObject actual) {
		final boolean equals = EcoreUtil.equals(expected, actual);
		if (!equals) {
			assertEquals(
					"EObjects do not match.", new EmfStringRenderer(expected).render(),
					new EmfStringRenderer(actual).render()
			);
			assertTrue("EObjects do not match. expected: " + expected + ", actual: " + actual, equals);
		}
	}
}
