package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;


//SUBTRACT A B FROM C D --> C=C-(A+B); D:D-(A+B);
/*
 * In syntax-1, A and B are added and subtracted from C. The Result is stored in C (C=C-(A+B)). 
 * A and B are added and subtracted from D. The result is stored in D (D=D-(A+B)).
 */
public class JavaSubtractElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaSubtractElement.class);
	
	
	private List<AbstractToken> sourceNum=new ArrayList<AbstractToken>();
	
	private List<AbstractToken> destNum=new ArrayList<AbstractToken>();
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		sourceNum=(List<AbstractToken>) this.getParameters().get("sourceNum");
		
		destNum=(List<AbstractToken>) this.getParameters().get("destNum");
		
		try{
				String srcNumList="(";
				for (AbstractToken srcNum1 : sourceNum) {
					srcNumList=srcNumList+srcNum1.getDeger()+"+";
				}
				srcNumList=srcNumList.substring(0,srcNumList.length()-1); // Sondaki + yi kaldir.
				srcNumList=srcNumList+")";
				
				 
				for (AbstractToken destNum1 : destNum) {
					JavaClassElement.javaCodeBuffer.append(destNum1.getDeger()+"="+destNum1.getDeger()+"-"+srcNumList);
					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				}
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
