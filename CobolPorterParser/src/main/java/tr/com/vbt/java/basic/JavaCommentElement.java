package tr.com.vbt.java.basic;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.util.ConverterConfiguration;


//MULTIPLY A BY B C --> 	B=A*B, C=A*C
public class JavaCommentElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaCommentElement.class);
	//Paramaters: functionName;
	
	private List<String> commentList;
	
	
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE);
		
		if(this.parameters.get("commentList") !=null){
			commentList = (List<String> ) this.parameters.get("commentList");
		
			try{
				
				for (String comment : commentList) {
					if(ConverterConfiguration.convertComments){
						JavaClassElement.javaCodeBuffer.append(comment+" ");
					}
				}
			} catch (Exception e) {
				logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
				JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
						+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
				logger.error("//Conversion Error:"+e.getMessage(), e); 
				ConvertUtilities.writeconversionErrors(e, this); 
			}
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
