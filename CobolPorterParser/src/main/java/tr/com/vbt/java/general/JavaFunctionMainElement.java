package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.screen.JavaAtEndOfPageElement;
import tr.com.vbt.java.screen.JavaAtTopOfPageElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;


public class JavaFunctionMainElement extends  AbstractJavaElement{

	final static Logger logger = LoggerFactory.getLogger(JavaFunctionMainElement.class);
	
	//Paramaters: functionName;
	String functionName="main";
	
	//Fonksiyona ait değişken tanımları (JavaFunctionVariablesElement) children da tutulur.
	
	//Fonksiyona ait komutlar  children da tutulur.
	
	@Override
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		removeAtTopOfPageAndAtTopPage();
		
		try {
			//AbstractJavaElement.javaCodeBuffer.append("public static void main(String[] args)"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			if(logModel.isMap()){
				AbstractJavaElement.javaCodeBuffer.append("public void parseMap()"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
				AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
				
				String implClass=null;
				try {
					implClass = ConversionLogModel.getInstance().getMapsProgram(ConversionLogModel.getInstance().getFileName()).getProgramName();
				} catch (Exception e) {
					implClass="NATSOURCEYOK";
				}
				AbstractJavaElement.javaCodeBuffer.append(implClass+"Impl natprog = ("+implClass+"Impl) program"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
				
			}else{
				AbstractJavaElement.javaCodeBuffer.append("public void mainProgram()"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			}
			
			this.writeChildrenJavaToStream();
			
			AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET+ "//Main Program End");
		
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}

	private void removeAtTopOfPageAndAtTopPage() {
		
		for(int i=0; i<this.children.size(); i++){
			
			if(this.getChildren().get(i) instanceof JavaAtTopOfPageElement
					||this.getChildren().get(i) instanceof JavaAtEndOfPageElement){
				
				children.remove(i);
			}
		}
		
	}



}
