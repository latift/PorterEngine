package tr.com.vbt.lexer;

import java.io.InputStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.List;

import tr.com.vbt.token.AbstractToken;

public class CustomStreamTokenizer extends StreamTokenizer{

	 public static final int TT_WORD = -3;
	 public static final char Left_Bracket = '(';
	public static final char Right_Bracket = ')';
	public static final char Unknown = '?';
	public static final char LEFT_SQUARE_BRACKET = '[' ; 
	public static final char RIGHT_SQUARE_BRACKET = ']' ; 
	public static final char STAR ='*'; 
	public static final char SHARP='#';
	public static final char PLUS ='+'; 
	public static final char MINUS ='-'; 
	public static final char DOT = '.';
	public static final char AMPERSAND = '&';
	public static final char SLASH ='/'; 
	public static final char COLON =':'; 
	public static final char SEMI_COLON =';'; 
	public static final char LESS_THAN ='<'; 
	public static final char EQUALS ='=' ; 
	public static final char GREATER_THAN ='>'; 
	public static final char WHITESPACE =' '; 
	public static final char UNRECOGNIZED = '?';
	public static final char UST_VIRGUL='\'';
	public static final char DOUBLE_QUOTA='\"';
	

	
	 public CustomStreamTokenizer(InputStream is) {
		super(is);
		// TODO Auto-generated constructor stub
	}
	 
	   public CustomStreamTokenizer(Reader r) {
	        super(r);
	   }
	   
	   
	
	   
	   
	
}
