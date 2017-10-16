package tr.com.vbt.java.subroutines;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;


//FETCH RETURN 'AYKPFMM2' -->

// AYKPFMM2 a
/**
 * JavaClassElement.javaCodeBuffer.append("showInput(new HtmlLink(NaturalTagTypes.LABEL,null,\"\","+token.getDeger()+"));");
	avaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
 * 
 *
 */
public class JavaFetchElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaFetchElement.class);

	private String programName;
	
	List<AbstractToken> paragraghParameters;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		StringBuffer fetchWithParamsStr=new StringBuffer();
		
		AbstractToken parameter, linkedToken;
		ArrayToken arrayParameter;
		String parameterName;
		
		try {
			
			programName=(String) this.parameters.get("programName");
			
			paragraghParameters=(List<AbstractToken>) this.parameters.get("paragraghParameters");
	
			JavaClassElement.javaCodeBuffer.append("fetchV3"+JavaConstants.OPEN_NORMAL_BRACKET+"\""+programName+"\"" +","+" new Parameter[]"+JavaConstants.OPEN_BRACKET);
			
			if(paragraghParameters !=null){

				for (int i=0; i<paragraghParameters.size();i++) {
					
						parameterName="PARAM"+i;
						
						parameter=paragraghParameters.get(i);
						
						if(parameter.isConstantVariableWithQuota()){
						
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameterName+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
						
							//CALLNAT 'TOPCLCN1' TAX-SECURITY(*)
						}else if(parameter.isPojoVariable() && parameter.getColumnNameToken().isAllArrayItems() ){
							
							String param= parameter.getDeger().toString()+"."+Utility.viewNameToPojoGetterName(parameter.getColumnNameToken().getDeger().toString())+"()";
							
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameterName+"\","+param+")");
							
						}else if(parameter.isPojoVariable() && parameter.getColumnNameToken()!=null){
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameterName+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
									
						}else if(parameter.isRecordVariable() && parameter.getLinkedToken()!=null){
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameterName+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
									
						}else {
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameterName+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
									
						}
						
						if(i<paragraghParameters.size()-1){
							
							JavaClassElement.javaCodeBuffer.append(",");
					
						}
						
					}
					
		
			}
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET); 
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}
}
