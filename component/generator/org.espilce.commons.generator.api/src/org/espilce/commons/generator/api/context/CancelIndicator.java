package org.espilce.commons.generator.api.context;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public interface CancelIndicator {
	
	boolean isCanceled();

	public final static CancelIndicator NullImpl = new CancelIndicator() {
		@Override
		public boolean isCanceled() {
			return false;
		}
	};
}