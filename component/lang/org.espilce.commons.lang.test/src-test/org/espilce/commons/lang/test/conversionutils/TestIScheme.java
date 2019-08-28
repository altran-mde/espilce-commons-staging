package org.espilce.commons.lang.test.conversionutils;

public interface TestIScheme {
	
	void emptyWithScheme() throws Exception;
	
	void fragment() throws Exception;
	
	void query() throws Exception;
	
	void fragmentQuery() throws Exception;
	
	void relativeUri() throws Exception;
	
	void noSchema() throws Exception;
	
	void otherSchema() throws Exception;
	
	void inputBroken() throws Exception;
}
