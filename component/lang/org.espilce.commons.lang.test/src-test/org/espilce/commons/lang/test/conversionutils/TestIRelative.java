package org.espilce.commons.lang.test.conversionutils;

public interface TestIRelative {
	
	void relativeNestedFile() throws Exception;
	
	void relativeFile() throws Exception;
	
	void relativeFileSlashesExcess() throws Exception;
	
	void relativeFolderSlash() throws Exception;
	
	void relativeFolderSlashesInbetween() throws Exception;
	
	void relativePath() throws Exception;
	
	void multiRelativePath() throws Exception;
	
	void current() throws Exception;
	
	void currentSlash() throws Exception;
	
	void currentRelativeNestedFile() throws Exception;
	
	void startRelativePath() throws Exception;
	
	void parent() throws Exception;
	
	void relativePseudoFragment() throws Exception;
	
}
