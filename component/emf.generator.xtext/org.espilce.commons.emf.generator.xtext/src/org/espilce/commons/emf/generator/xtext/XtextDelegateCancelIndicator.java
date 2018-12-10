package org.espilce.commons.emf.generator.xtext;

public class XtextDelegateCancelIndicator implements org.espilce.commons.generator.api.context.CancelIndicator {
	
	private org.eclipse.xtext.util.CancelIndicator delegate;
	
	public XtextDelegateCancelIndicator(final org.eclipse.xtext.util.CancelIndicator delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean isCanceled() {
		return this.delegate.isCanceled();
	}
}
