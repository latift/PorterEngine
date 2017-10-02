package tr.com.vbt.java.subroutines;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


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
	
	List<AbstractToken> fetchParameters;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		StringBuffer fetchWithParamsStr=new StringBuffer();
		try {
			programName=(String) this.parameters.get("programName");
			
			fetchParameters=(List<AbstractToken>) this.parameters.get("paragraghParameters");
			
			if(fetchParameters==null || fetchParameters.isEmpty()){
			
				JavaClassElement.javaCodeBuffer.append("fetch(\""+programName+"\")"+JavaConstants.DOT_WITH_COMMA);
			}else{
				// fetchWithParameter("IDGP0013",new Parameter[]{new Parameter("PARAM1",1)});
				fetchWithParamsStr.append("fetchWithParameter(\""+programName+"\",new Parameter[]");
				for( int i=0;  i<fetchParameters.size(); i++){
					fetchWithParamsStr.append("{new Parameter(\"PARAM"+i+"\","+JavaWriteUtilities.toCustomString(fetchParameters.get(i))+")}");
					if(i<fetchParameters.size()-1){
						fetchWithParamsStr.append(",");
					}
				}
				fetchWithParamsStr.append(")"+JavaConstants.DOT_WITH_COMMA);
			}
			JavaClassElement.javaCodeBuffer.append(fetchWithParamsStr.toString()+JavaConstants.NEW_LINE);
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
