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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Base class for EMF EValidator tests.
 * 
 * @since 0.1
 * 
 */
public abstract class ATestValidator {
	/**
	 * Magic value to mark the index of an element in a
	 * {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment is
	 * not relevant.
	 */
	public final static int INSIGNIFICANT_INDEX = -1;

	/**
	 * Magic value to ignore the severity of an assert*() call.
	 */
	protected final static int SEVERITY_IGNORE = -1;

	private Diagnostic diagnostic;

	/**
	 * Validates <code>modelResource</code>.
	 *
	 * @param modelResource
	 *            EMF resource to validate.
	 *
	 * @return The first root EObject in <code>modelResource</code>.
	 * @since 0.1
	 */
	public @Nullable EObject validateModel(final @NonNull Resource modelResource) {
		this.diagnostic = validateModels(modelResource.getResourceSet());
		return modelResource.getContents().isEmpty() ? null : modelResource.getContents().iterator().next();
	}

	/**
	 * Asserts that neither <code>source</code> nor any of its descendants have been
	 * marked with the specified issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use
	 *            {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use
	 *            {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment. Use {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use
	 *            {@code null} to ignore.
	 * @since 0.1
	 */
	public void assertIssueAbsentRecursive(final int severity, final @Nullable String message,
			final @NonNull EObject source, final @Nullable EStructuralFeature feature, final int index,
			final @Nullable String code, final @Nullable Object... issueData) {
		assertIssueAbsent(severity, message, source, feature, index, code, issueData);
		source.eAllContents().forEachRemaining(
				descendant -> assertIssueAbsent(severity, message, descendant, feature, index, code, issueData));
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use
	 *            {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use
	 *            {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment. Use {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use
	 *            {@code null} to ignore.
	 * @since 0.1
	 */
	public void assertIssuePresent(final int severity, final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code,
			final @Nullable Object... issueData) {
		assertNotNull("EObject is null", source);
		final Set<Diagnostic> diagnostics = findDiagnostics(source);
		assertFalse("Didn\'t find diagnostics for " + source, diagnostics.isEmpty());

		Set<Diagnostic> diagnosticsToCheck;
		if (diagnostics.size() == 1) {
			diagnosticsToCheck = new LinkedHashSet<>(diagnostics);
		} else {
			diagnosticsToCheck = diagnostics;
		}
		final String assertMsg = "Missing issue "
				+ String.join(" ", findIssue(diagnostics, severity, message, source, feature, index, code, issueData));

		assertFalse(assertMsg, diagnosticsToCheck.isEmpty());

		for (final Diagnostic diag : diagnosticsToCheck) {
			{
				assertSeverity(assertMsg, diag, severity);
				assertMessage(assertMsg, diag, message);
				assertFeatureAndIndex(assertMsg, diag, feature, index);
				assertCode(assertMsg, diag, code);
				assertIssueData(assertMsg, diag, issueData);
			}
		}
	}

	/**
	 * Asserts that <code>source</code> has not been marked with the specified
	 * issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use
	 *            {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use
	 *            {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment. Use {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use
	 *            {@code null} to ignore.
	 * @since 0.1
	 */
	public void assertIssueAbsent(final int severity, final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code,
			final @Nullable Object... issueData) {
		assertNotNull("EObject is null", source);
		final Set<Diagnostic> diagnostics = findDiagnostics(source);
		final List<String> assertMsg = findIssue(diagnostics, severity, message, source, feature, index, code,
				issueData);
		assertTrue("Found unexpected issue " + String.join(" ", assertMsg), diagnostics.isEmpty());
	}

	/**
	 * Asserts that neither <code>source</code> nor any of its descendants have been
	 * marked with an error.
	 * 
	 * @since 0.1
	 */
	public void assertNoErrors(final @NonNull EObject source) {
		assertIssueAbsentRecursive(Diagnostic.ERROR, null, source, null, ATestValidator.INSIGNIFICANT_INDEX, null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @NonNull EObject source, final @Nullable String code) {
		assertErrorPresent(null, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @NonNull EObject source, final @Nullable EStructuralFeature feature,
			final @Nullable String code) {
		assertErrorPresent(null, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source) {
		assertErrorPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable String code) {
		assertErrorPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature) {
		assertErrorPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final @Nullable String code) {
		assertErrorPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index) {
		assertErrorPresent(message, source, feature, index, null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code) {
		assertErrorPresent(message, source, feature, index, code, (Object[]) null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 *
	 * @param code
	 *            Issue's code.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}.
	 * @since 0.1
	 */
	public void assertErrorPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code,
			final @Nullable Object... issueData) {
		assertIssuePresent(Diagnostic.ERROR, message, source, feature, index, code, issueData);
	}

	/**
	 * Asserts that neither <code>source</code> nor any of its descendants have been
	 * marked with a warning or error.
	 * 
	 * @since 0.1
	 */
	public void assertNoErrorsOrWarnings(final @NonNull EObject source) {
		assertNoErrors(source);
		assertNoWarnings(source);
	}

	/**
	 * Asserts that neither <code>source</code> nor any of its descendants have been
	 * marked with a warning.
	 * 
	 * @since 0.1
	 */
	public void assertNoWarnings(final @NonNull EObject source) {
		assertIssueAbsentRecursive(Diagnostic.WARNING, null, source, null, ATestValidator.INSIGNIFICANT_INDEX, null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @NonNull EObject source, final @Nullable String code) {
		assertWarningPresent(null, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @NonNull EObject source, final @Nullable EStructuralFeature feature,
			final @Nullable String code) {
		assertWarningPresent(null, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source<code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source) {
		assertWarningPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable String code) {
		assertWarningPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature) {
		assertWarningPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final @Nullable String code) {
		assertWarningPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index) {
		assertWarningPresent(message, source, feature, index, null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 *
	 * @param code
	 *            Issue's code.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code) {
		assertWarningPresent(message, source, feature, index, code, (Object[]) null);
	}

	/**
	 * Asserts that <code>source</code> has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param index
	 *            Issue's index in a
	 *            {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 *            containment.
	 *
	 * @param code
	 *            Issue's code.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}.
	 * @since 0.1
	 */
	public void assertWarningPresent(final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code,
			final @Nullable Object... issueData) {
		assertIssuePresent(Diagnostic.WARNING, message, source, feature, index, code, issueData);
	}

	/**
	 * Creates the EMF ResourceSet to load models into.
	 *
	 * <p>
	 * Override to provide specialized ResourceSets.
	 * </p>
	 * 
	 * @since 0.1
	 */
	protected @NonNull ResourceSet createResourceSet() {
		return new ResourceSetImpl();
	}

	private @NonNull List<@NonNull String> findIssue(final @NonNull Set<@NonNull Diagnostic> diagnostics,
			final int severity, final @Nullable String message, final @NonNull EObject source,
			final @Nullable EStructuralFeature feature, final int index, final @Nullable String code,
			final @Nullable Object... issueData) {
		final List<String> assertMsg = new ArrayList<>();

		if (isValidSeverity(severity)) {
			diagnostics.removeIf(it -> severity != it.getSeverity());
			assertMsg.add("severity=" + Integer.valueOf(severity));
		}

		if (isValidMessage(message)) {
			diagnostics.removeIf(it -> !Objects.equals(message, it.getMessage()));
			assertMsg.add("message=\"" + message + "\"");
		}

		if (isValidFeature(feature)) {
			diagnostics.removeIf(it -> it.getData().size() < 2 || !Objects.equals(feature, it.getData().get(1)));
			@SuppressWarnings("null")
			final String featureName = feature.getName();
			assertMsg.add("feature=" + featureName);

			if (isValidIndex(index)) {
				diagnostics.removeIf(it -> it.getData().size() < 3 || index != (Integer) it.getData().get(2));
				assertMsg.add("index=" + Integer.valueOf(index));
			}
		}

		if (isValidCode(code)) {
			diagnostics.removeIf(it -> it.getData().stream().anyMatch(d -> Objects.equals(d, code)));
			assertMsg.add("code=" + code);
		}

		if (isValidIssueData(issueData)) {
			diagnostics.removeIf(it -> !it.getData().containsAll(Arrays.asList(issueData)));
			assertMsg.add("issueData=[" + issueData + "]");
		}

		return assertMsg;
	}

	private boolean isValidSeverity(final int severity) {
		return severity != ATestValidator.SEVERITY_IGNORE;
	}

	private boolean isValidMessage(final @Nullable String message) {
		return message != null;
	}

	private boolean isValidFeature(final @Nullable EStructuralFeature feature) {
		return feature != null;
	}

	private boolean isValidIndex(final int index) {
		return index != ATestValidator.INSIGNIFICANT_INDEX;
	}

	private boolean isValidCode(final @Nullable String code) {
		return code != null;
	}

	private boolean isValidIssueData(final @Nullable Object[] issueData) {
		return issueData != null && issueData.length > 0;
	}

	private @NonNull Set<@NonNull Diagnostic> findDiagnostics(final @NonNull EObject eObject) {
		final Set<Diagnostic> result = new LinkedHashSet<>();

		assertNotNull("Didn\'t find root diagnostic. Didn\'t run validation?", this.diagnostic);

		return findDiagnosticsRecursive(eObject, result, this.diagnostic);
	}

	private @NonNull Set<@NonNull Diagnostic> findDiagnosticsRecursive(final @NonNull EObject eObject,
			final @NonNull Set<@NonNull Diagnostic> result, final @NonNull Diagnostic diagnostic) {
		if (diagnostic.getData() != null && !diagnostic.getData().isEmpty()
				&& Objects.equals(eObject, diagnostic.getData().iterator().next())) {
			result.add(diagnostic);
		}

		for (final Diagnostic child : diagnostic.getChildren()) {
			findDiagnosticsRecursive(eObject, result, child);
		}

		return result;
	}

	private @NonNull Diagnostic validateModels(final @NonNull ResourceSet resourceSet) {
		final Diagnostician diagnostician = new Diagnostician();

		final BasicDiagnostic allDiagnostic = new BasicDiagnostic();

		for (final Resource resource : new CopyOnWriteArrayList<>(resourceSet.getResources())) {
			final BasicDiagnostic resourceDiagnostic = new BasicDiagnostic();
			allDiagnostic.add(resourceDiagnostic);

			for (final EObject eObject : resource.getContents()) {
				final BasicDiagnostic diagnostic = new BasicDiagnostic();
				resourceDiagnostic.add(diagnostic);
				diagnostician.validate(eObject, diagnostic);
			}
		}

		return allDiagnostic;
	}

	private void assertSeverity(final @Nullable String message, final @NonNull Diagnostic diagnostic,
			final int severity) {
		if (isValidSeverity(severity)) {
			assertEquals(message, severity, diagnostic.getSeverity());
		}
	}

	private void assertMessage(final @Nullable String message, final @NonNull Diagnostic diagnostic,
			final @Nullable String errorMsg) {
		if (isValidMessage(errorMsg)) {
			assertEquals(message, errorMsg, diagnostic.getMessage());
		}
	}

	private void assertFeatureAndIndex(final @Nullable String message, final @NonNull Diagnostic diagnostic,
			final @Nullable EStructuralFeature feature, final int index) {
		if (isValidFeature(feature)) {
			assertTrue(message, diagnostic.getData().size() >= 2);
			final Object diagnosticFeature = diagnostic.getData().get(1);
			if (!(diagnosticFeature instanceof EStructuralFeature)) {
				assertEquals(message, feature, diagnosticFeature);
			} else if (!Objects.equals(feature, diagnosticFeature)) {
				@SuppressWarnings("null")
				final String featureName = feature.getName();
				assertEquals(message, "feature=" + featureName,
						"feature=" + ((EStructuralFeature) diagnosticFeature).getName());
				// if the feature names would be equal by chance, this triggers a failure
				assertEquals(message, feature, diagnosticFeature);
			}

			if (isValidIndex(index)) {
				assertTrue(message, diagnostic.getData().size() >= 3);
				@SuppressWarnings("null")
				final int actualIndex = (Integer) diagnostic.getData().get(2);
				assertEquals(message, index, actualIndex);
			}
		}
	}

	private void assertCode(final @Nullable String message, final @NonNull Diagnostic diagnostic,
			final @Nullable String code) {
		if (isValidCode(code)) {
			assertEquals(message, code, diagnostic.getData().stream().filter(String.class::isInstance).findAny());
		}
	}

	private void assertIssueData(final @Nullable String message, final @NonNull Diagnostic diagnostic,
			final @Nullable Object... issueData) {
		if (isValidIssueData(issueData)) {
			final List<Object> cleansedIssueData = new ArrayList<>(diagnostic.getData());
			if (!cleansedIssueData.isEmpty() && cleansedIssueData.iterator().next() instanceof EObject) {
				cleansedIssueData.remove(0);

				if (!cleansedIssueData.isEmpty() && cleansedIssueData.iterator().next() instanceof EStructuralFeature) {
					cleansedIssueData.remove(0);

					if (!cleansedIssueData.isEmpty() && cleansedIssueData.iterator().next() instanceof Integer) {
						cleansedIssueData.remove(0);
					}
				}
			}

			if (!cleansedIssueData.isEmpty() && cleansedIssueData.iterator().next() instanceof String) {
				cleansedIssueData.remove(0);
			}

			assertArrayEquals(message, issueData, cleansedIssueData.toArray());
		}
	}
}
