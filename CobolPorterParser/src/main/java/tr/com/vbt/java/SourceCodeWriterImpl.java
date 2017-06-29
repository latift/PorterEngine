package tr.com.vbt.java;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.CustomStreamTokenizer;
import tr.com.vbt.util.ConverterConfiguration;

public class SourceCodeWriterImpl implements SourceCodeWriter {
	
	final static Logger logger = LoggerFactory.getLogger(SourceCodeWriterImpl.class);
	
	List<SourceLine> orjinalSources=new ArrayList<SourceLine>();
	
	
	private static SourceCodeWriter instance;
	
	public static SourceCodeWriter getInstance() {
		if (instance==null){
			instance=new SourceCodeWriterImpl();
		}
		return  instance;
	}

	private SourceCodeWriterImpl() {
		super();
		ConversionLogModel logModel=ConversionLogModel.getInstance();
		tokenizeSourceFile(logModel.getFullNaturalInputFileName());
	}

	@Override
	public void writeSourceCode(AbstractJavaElement aje) {
		int sourceLineNumberOfAJE=aje.getSourceCode().getSatirNumarasi();
		if(sourceLineNumberOfAJE==0){
			return;
		}
		for (SourceLine sourceLine : orjinalSources) {
			if(sourceLine.lineNumber==sourceLineNumberOfAJE){
				
				if(ConverterConfiguration.customer.equals("THY")){
					////NATURAL CODE:*S **1.0 #SAY ( N10 ) 
					JavaClassElement.javaCodeBuffer.append("//NATURAL CODE:"+sourceLine.getLineNumber()+"   :"+sourceLine.getSourceCodeInLine().substring(5)); //remove *S **
				}else if(ConverterConfiguration.customer.equals("MB")){
					JavaClassElement.javaCodeBuffer.append("//NATURAL CODE:"+sourceLine.getLineNumber()+"   :"+sourceLine.getSourceCodeInLine().substring(4)); //remove 2200
				}
				sourceLine.setLineWritten(true);
				sourceLine.setWriteCause(SourceCodeWriteCauses.NORMAL);
				return;
			}else if(sourceLine.lineNumber<sourceLineNumberOfAJE && sourceLine.lineWritten==false){
				writeCodeLineAutomatically(sourceLine);
			}
		}

	}

	



	private void writeCodeLineAutomatically(SourceLine sourceLine) {
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		try {
			if(ConverterConfiguration.customer.equals("THY")){
				JavaClassElement.javaCodeBuffer.append("//NATURAL CODE: AUTOMATIC: "+sourceLine.getLineNumber()+"   :"+sourceLine.getSourceCodeInLine().substring(5)); //remove *S **
			}else if(ConverterConfiguration.customer.equals("MB")){
				JavaClassElement.javaCodeBuffer.append("//NATURAL CODE: AUTOMATIC: "+sourceLine.getLineNumber()+"   :"+sourceLine.getSourceCodeInLine().substring(4)); //remove 2200
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		sourceLine.setLineWritten(true);
		sourceLine.setWriteCause(SourceCodeWriteCauses.AUTOMATICALLY);
		
	}

	public void tokenizeSourceFile(String sourceFileFullName ){
		CustomStreamTokenizer currentTokenizer =null;
		SourceLine sourceLine;
		StringBuffer sourceLineBuffer;
		int charNumberInLine=0;
		sourceLine=new SourceLine(); //İlk satir için yarat. Sonra EOL gördükçe yeniden yarat.
		sourceLine.setLineNumber(1);
		try {
			  		InputStream inputStream       = new FileInputStream(sourceFileFullName);
			  		Reader      inputStreamReader = new InputStreamReader(inputStream);
			  		currentTokenizer = new CustomStreamTokenizer(inputStreamReader);
			  		
			  		currentTokenizer.eolIsSignificant(true);
			  		currentTokenizer.ordinaryChar('/');
			  		currentTokenizer.wordChars('_', '_');
			  		currentTokenizer.ordinaryChar('.');
			  		
			  	    boolean eof = false;
			        do {
			            int token = currentTokenizer.nextToken();
			            logger.debug("Token:"+token+ " Sval:"+currentTokenizer.sval+ " NVal:"+currentTokenizer.nval);
			            switch (token) {
			               case CustomStreamTokenizer.TT_EOF:
			            	  logger.trace("End of File encountered.");
			            	  orjinalSources.add(sourceLine);
			            	  eof=true;
			                  break;
			               case CustomStreamTokenizer.TT_EOL:
			            	  logger.trace("End of Line encountered.");
			            	  orjinalSources.add(sourceLine);
			            	  sourceLine=new SourceLine();
			            	  sourceLine.setLineNumber(currentTokenizer.lineno());
			                  break;
			               case CustomStreamTokenizer.TT_WORD:
			            	  sourceLine.getSourceCodeInLine().append(currentTokenizer.sval+" ");
			            	  break;
				           case CustomStreamTokenizer.TT_NUMBER:
			                  sourceLine.getSourceCodeInLine().append(currentTokenizer.nval+" ");
			            	  break;
				           case CustomStreamTokenizer.DOUBLE_QUOTA:
				        	  sourceLine.getSourceCodeInLine().append(currentTokenizer.sval+" ");
				              break;
			               case CustomStreamTokenizer.UST_VIRGUL:
			            	  sourceLine.getSourceCodeInLine().append(currentTokenizer.sval+" ");
				              break;
				           case CustomStreamTokenizer.STAR:
				        	   sourceLine.getSourceCodeInLine().append(((char)token));
					           break;
				           case CustomStreamTokenizer.SLASH:
				        	   sourceLine.getSourceCodeInLine().append(((char)token));
					           break;
				           case CustomStreamTokenizer.DOT:
				        	   sourceLine.getSourceCodeInLine().append(((char)token));
					           break;                  
				           case CustomStreamTokenizer.SHARP:
				        	   sourceLine.getSourceCodeInLine().append(((char)token));
					           break;	  
			                default:
			                	sourceLine.getSourceCodeInLine().append(((char)token)+" ");
						        break;
			            }
			        }while (!eof);
		         inputStreamReader.close();
				      } catch (Exception ex) {
		         ex.printStackTrace();
		      }
		
	}


	class SourceLine{
		
		boolean lineWritten; //0 yazilmadi
						 //1 yazildi
		int lineNumber;
		
		SourceCodeWriteCauses writeCause;
		
		StringBuffer sourceCodeInLine=new StringBuffer();
		
		public int getLineNumber() {
			return lineNumber;
		}
		public void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}
		public StringBuffer getSourceCodeInLine() {
			return sourceCodeInLine;
		}
		public void setSourceCodeInLine(StringBuffer sourceCodeInLine) {
			this.sourceCodeInLine = sourceCodeInLine;
		}
		public boolean isLineWritten() {
			return lineWritten;
		}
		public void setLineWritten(boolean lineWritten) {
			this.lineWritten = lineWritten;
		}
		public SourceCodeWriteCauses getWriteCause() {
			return writeCause;
		}
		public void setWriteCause(SourceCodeWriteCauses writeCause) {
			this.writeCause = writeCause;
		}
		
		
	}


	@Override
	public void resetSourceCode() {
		instance=null;
		
	}
}
