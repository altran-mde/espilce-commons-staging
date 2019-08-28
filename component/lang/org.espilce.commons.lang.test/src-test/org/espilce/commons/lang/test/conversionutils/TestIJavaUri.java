package org.espilce.commons.lang.test.conversionutils;

public interface TestIJavaUri {
	void inputBroken() throws Exception;
	
	void noSchema() throws Exception;
	
	void absoluteNestedFileNoScheme() throws Exception;
	
	void startRelativePathNoScheme() throws Exception;
	
	void currentRelativeNestedFileNoScheme() throws Exception;
	
	void relativeNestedFileNoScheme() throws Exception;
	
	void rootNoScheme() throws Exception;
	
	void emptyNoScheme() throws Exception;
	
	void currentNoScheme() throws Exception;
	
	void currentSlashNoScheme() throws Exception;
	
	void rootScheme() throws Exception;
}
