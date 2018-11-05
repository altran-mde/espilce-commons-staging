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

/**
 * Base class for Ecore EValidator tests.
 */
public abstract class ATestValidator {
	/**
	 * Magic value to mark the index of an element in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many}
	 * containment is not relevant.
	 */
	public final static int INSIGNIFICANT_INDEX = -1;

	/**
	 * Magic value to ignore the severity of an assert*() call.
	 */
	protected final static int SEVERITY_IGNORE = -1;

	private Diagnostic diagnostic;

	/**
	 * Validates {@code modelResource}.
	 *
	 * @param modelResource
	 *            Ecore resource to validate.
	 *
	 * @return The first root EObject in {@code modelResource}.
	 */
	public EObject validateModel(final Resource modelResource) {
		this.diagnostic = validateModels(modelResource.getResourceSet());
		return modelResource.getContents().isEmpty() ? null : modelResource.getContents().iterator().next();
	}

	/**
	 * Asserts that neither {@code source} nor any of its descendants have been marked with the specified issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment. Use
	 *            {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use {@code null} to ignore.
	 */
	public void assertIssueAbsentRecursive(final int severity, final String message, final EObject source,
			final EStructuralFeature feature, final int index, final String code, final Object... issueData) {
		assertIssueAbsent(severity, message, source, feature, index, code, issueData);
		source.eAllContents().forEachRemaining(
				descendant -> assertIssueAbsent(severity, message, descendant, feature, index, code, issueData));
	}

	/**
	 * Asserts that {@code source} has been marked with the specified issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment. Use
	 *            {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use {@code null} to ignore.
	 */
	public void assertIssuePresent(final int severity, final String message, final EObject source,
			final EStructuralFeature feature, final int index, final String code, final Object... issueData) {
		assertNotNull("EObject is null", source);
		final Set<Diagnostic> diagnostics = findDiagnostics(source);
		assertFalse("Didn\'t find diagnostics for " + source, diagnostics.isEmpty());

		Set<Diagnostic> diagnosticsToCheck;
		if (diagnostics.size() == 1) {
			diagnosticsToCheck = new LinkedHashSet<>(1);
			diagnosticsToCheck.add(diagnostics.iterator().next());
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
	 * Asserts that {@code source} has not been marked with the specified issue.
	 *
	 * @param severity
	 *            Issue's {@linkplain Diagnostic#getSeverity severity}. Use {@link #SEVERITY_IGNORE} to ignore.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}. Use {@code null} to ignore.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature. Use {@code null} to ignore.
	 *
	 * @param index
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment. Use
	 *            {@link #INSIGNIFICANT_INDEX} to ignore.
	 *
	 * @param code
	 *            Issue's code. Use {@code null} to ignore.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}. Use {@code null} to ignore.
	 */
	public void assertIssueAbsent(final int severity, final String message, final EObject source,
			final EStructuralFeature feature, final int index, final String code, final Object... issueData) {
		assertNotNull("EObject is null", source);
		final Set<Diagnostic> diagnostics = findDiagnostics(source);
		final List<String> assertMsg = findIssue(diagnostics, severity, message, source, feature, index, code,
				issueData);
		assertTrue("Found unexpected issue " + String.join(" ", assertMsg), diagnostics.isEmpty());
	}

	/**
	 * Asserts that neither {@code source} nor any of its descendants have been marked with an error.
	 */
	public void assertNoErrors(final EObject source) {
		assertIssueAbsentRecursive(Diagnostic.ERROR, null, source, null, ATestValidator.INSIGNIFICANT_INDEX, null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertErrorPresent(final EObject source, final String code) {
		this.assertErrorPresent(null, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertErrorPresent(final EObject source, final EStructuralFeature feature, final String code) {
		this.assertErrorPresent(null, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 */
	public void assertErrorPresent(final String message, final EObject source) {
		this.assertErrorPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertErrorPresent(final String message, final EObject source, final String code) {
		this.assertErrorPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 */
	public void assertErrorPresent(final String message, final EObject source, final EStructuralFeature feature) {
		this.assertErrorPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
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
	 */
	public void assertErrorPresent(final String message, final EObject source, final EStructuralFeature feature,
			final String code) {
		this.assertErrorPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 */
	public void assertErrorPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index) {
		this.assertErrorPresent(message, source, feature, index, null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertErrorPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index, final String code) {
		this.assertErrorPresent(message, source, feature, index, code, (Object[]) null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified error.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 *
	 * @param code
	 *            Issue's code.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}.
	 */
	public void assertErrorPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index, final String code, final Object... issueData) {
		assertIssuePresent(Diagnostic.ERROR, message, source, feature, index, code, issueData);
	}

	/**
	 * Asserts that neither {@code source} nor any of its descendants have been marked with a warning or error.
	 */
	public void assertNoErrorsOrWarnings(final EObject source) {
		assertNoErrors(source);
		assertNoWarnings(source);
	}

	/**
	 * Asserts that neither {@code source} nor any of its descendants have been marked with a warning.
	 */
	public void assertNoWarnings(final EObject source) {
		assertIssueAbsentRecursive(Diagnostic.WARNING, null, source, null, ATestValidator.INSIGNIFICANT_INDEX, null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertWarningPresent(final EObject source, final String code) {
		this.assertWarningPresent(null, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertWarningPresent(final EObject source, final EStructuralFeature feature, final String code) {
		this.assertWarningPresent(null, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 */
	public void assertWarningPresent(final String message, final EObject source) {
		this.assertWarningPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertWarningPresent(final String message, final EObject source, final String code) {
		this.assertWarningPresent(message, source, null, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
	 *
	 * @param message
	 *            Issue's {@linkplain Diagnostic#getMessage message}.
	 *
	 * @param source
	 *            EObject to check for the specified issue.
	 *
	 * @param feature
	 *            Issue's feature.
	 */
	public void assertWarningPresent(final String message, final EObject source, final EStructuralFeature feature) {
		this.assertWarningPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
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
	 */
	public void assertWarningPresent(final String message, final EObject source, final EStructuralFeature feature,
			final String code) {
		this.assertWarningPresent(message, source, feature, ATestValidator.INSIGNIFICANT_INDEX, code);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 */
	public void assertWarningPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index) {
		this.assertWarningPresent(message, source, feature, index, null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 *
	 * @param code
	 *            Issue's code.
	 */
	public void assertWarningPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index, final String code) {
		this.assertWarningPresent(message, source, feature, index, code, (Object[]) null);
	}

	/**
	 * Asserts that {@code source} has been marked with the specified warning.
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
	 *            Issue's index in a {@linkplain org.eclipse.emf.ecore.ETypedElement#isMany() many} containment.
	 *
	 * @param code
	 *            Issue's code.
	 *
	 * @param issueData
	 *            Issue's {@linkplain Diagnostic#getData additional data}.
	 */
	public void assertWarningPresent(final String message, final EObject source, final EStructuralFeature feature,
			final int index, final String code, final Object... issueData) {
		assertIssuePresent(Diagnostic.WARNING, message, source, feature, index, code, issueData);
	}

	/**
	 * Creates the Ecore ResourceSet to load models into.
	 *
	 * <p>
	 * Override to provide specialized ResourceSets.
	 * </p>
	 */
	protected ResourceSet createResourceSet() {
		return new ResourceSetImpl();
	}

	private List<String> findIssue(final Set<Diagnostic> diagnostics, final int severity, final String message,
			final EObject source, final EStructuralFeature feature, final int index, final String code,
			final Object... issueData) {
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
			assertMsg.add("feature=" + feature.getName());

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

	private boolean isValidMessage(final String message) {
		return message != null;
	}

	private boolean isValidFeature(final EStructuralFeature feature) {
		return feature != null;
	}

	private boolean isValidIndex(final int index) {
		return index != ATestValidator.INSIGNIFICANT_INDEX;
	}

	private boolean isValidCode(final String code) {
		return code != null;
	}

	private boolean isValidIssueData(final Object[] issueData) {
		return issueData != null && issueData.length > 0;
	}

	private Set<Diagnostic> findDiagnostics(final EObject eObject) {
		final Set<Diagnostic> result = new LinkedHashSet<>();

		assertNotNull("Didn\'t find root diagnostic. Didn\'t run validation?", this.diagnostic);

		return findDiagnosticsRecursive(eObject, result, this.diagnostic);
	}

	private Set<Diagnostic> findDiagnosticsRecursive(final EObject eObject, final Set<Diagnostic> result,
			final Diagnostic diagnostic) {
		if (diagnostic.getData() != null && !diagnostic.getData().isEmpty()
				&& Objects.equals(eObject, diagnostic.getData().iterator().next())) {
			result.add(diagnostic);
		}

		for (final Diagnostic child : diagnostic.getChildren()) {
			findDiagnosticsRecursive(eObject, result, child);
		}

		return result;
	}

	private Diagnostic validateModels(final ResourceSet resourceSet) {
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

	private void assertSeverity(final String message, final Diagnostic diagnostic, final int severity) {
		if (isValidSeverity(severity)) {
			assertEquals(message, severity, diagnostic.getSeverity());
		}
	}

	private void assertMessage(final String message, final Diagnostic diagnostic, final String errorMsg) {
		if (isValidMessage(errorMsg)) {
			assertEquals(message, errorMsg, diagnostic.getMessage());
		}
	}

	private void assertFeatureAndIndex(final String message, final Diagnostic diagnostic,
			final EStructuralFeature feature, final int index) {
		if (isValidFeature(feature)) {
			assertTrue(message, diagnostic.getData().size() >= 2);
			final Object diagnosticFeature = diagnostic.getData().get(1);
			if (!(diagnosticFeature instanceof EStructuralFeature)) {
				assertEquals(message, feature, diagnosticFeature);
			} else if (!Objects.equals(feature, diagnosticFeature)) {
				assertEquals(message, "feature=" + feature.getName(),
						"feature=" + ((EStructuralFeature) diagnosticFeature).getName());
				// if the feature names would be equal by chance, this triggers a failure
				assertEquals(message, feature, diagnosticFeature);
			}

			if (isValidIndex(index)) {
				assertTrue(message, diagnostic.getData().size() >= 3);
				assertEquals(message, index, (int) (Integer) diagnostic.getData().get(2));
			}
		}
	}

	private void assertCode(final String message, final Diagnostic diagnostic, final String code) {
		if (isValidCode(code)) {
			assertEquals(message, code,
					diagnostic.getData().stream().filter(String.class::isInstance).findAny());
		}
	}

	private void assertIssueData(final String message, final Diagnostic diagnostic, final Object... issueData) {
		if (isValidIssueData(issueData)) {
			final List<Object> cleansedIssueData = new ArrayList<>();
			cleansedIssueData.addAll(diagnostic.getData());
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
