package tr.com.vbt.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import tr.com.vbt.token.AbstractToken;

public abstract class AbstractLexing {

	CustomStreamTokenizer currentTokenizer =null; 
	
	Scanner currentScanner=null; 
	
	public List<AbstractToken> tokenListesi=new ArrayList<AbstractToken>();
	
	
	
	public AbstractLexing() {
		super();
	}
	
	public abstract void tokenizeSourceFile(String sourceFileFullName, String module , String customer) throws Exception;
	
	public abstract StringBuilder exportLexedData(String fullTokenizeFileName);
	
	public abstract Map<String, String> getIncludeFileList();
	
		
}
