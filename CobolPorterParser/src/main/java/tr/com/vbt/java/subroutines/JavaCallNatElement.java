package tr.com.vbt.java.subroutines;

import java.util.ArrayList;
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


//CALLNAT 'HANMSGN2' #ERR-NUM #PROGRAM #PREFIX #SUFFIX #STRING -->  programCall();
// HANMSGN2 hanmsgn2=new HANMSGN2();
//hanmsgn2.call(ERR-NUM , PROGRAM , PREFIX , SUFFIX ,STRING);   --> 	callNat("IDGNONYL", new Object[]{MUSNO1, UYARI});

//				// NATURAL CODE:467 :.0 CALLNAT IDGNONYL + MUSNO1 UYARI
//callNat_v2("IDGNONYL", new Parameter[] { 		new Parameter("GLOBAL_MUSNO1", GLOBAL_MUSNO1),  new Parameter("MAPP.UYARI",MAPP.UYARI) })
public class JavaCallNatElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaCallNatElement.class);

	private String paragraghName;
	
	private List<AbstractToken> paragraghParameters=new ArrayList<AbstractToken>();
	
	private AbstractToken firstDimension;
	
	private int firstDimensionSize;
	
	private AbstractToken secDimension;
	
	private int secDimensionSize;
	
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		AbstractToken parameter, linkedToken;
		ArrayToken arrayParameter;
		String parameterName;
		try {
			paragraghName=(String) this.parameters.get("paragraghName");
			
			paragraghParameters=(List<AbstractToken>) this.parameters.get("paragraghParameters");
			/* 1624 CALLNAT 'IDGNGECI' SECILEN_DIZISI SECILENSAY REFNUM
			 1626 WDOVIZ WMUHKOD MUHVALOR D_SMUSNO1(1)
			 1628 D_SMUSNO2(1) D_SHESNIT(1)*/
			//JavaClassElement.javaCodeBuffer.append("//");  //TODO: Compile hatası olmaması icin gecici kondu.
			JavaClassElement.javaCodeBuffer.append("callNatV2"+JavaConstants.OPEN_NORMAL_BRACKET+"\""+paragraghName+"\"" +","+" new Parameter[]"+JavaConstants.OPEN_BRACKET);
			
			if(paragraghParameters !=null){

				for (int i=0; i<paragraghParameters.size();i++) {
						
						parameter=paragraghParameters.get(i);
						
						if(parameter.isConstantVariableWithQuota()){
						
							JavaClassElement.javaCodeBuffer.append("new Parameter("+JavaWriteUtilities.toCustomString(parameter)+","+JavaWriteUtilities.toCustomString(parameter)+")");
						
							//CALLNAT 'TOPCLCN1' TAX-SECURITY(*)
						}else if(parameter.isPojoVariable() && parameter.getColumnNameToken().isAllArrayItems() ){
							
							String param= parameter.getDeger().toString()+"."+Utility.viewNameToPojoGetterName(parameter.getColumnNameToken().getDeger().toString())+"()";
							
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+param+"\","+param+")");
							
						}else if(parameter.isPojoVariable() && parameter.getColumnNameToken()!=null){
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameter.getColumnNameToken().getDeger().toString()+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
									
						}else {
							JavaClassElement.javaCodeBuffer.append("new Parameter(\""+parameter.getDeger().toString()+"\","+JavaWriteUtilities.toCustomString(parameter)+")");
									
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
