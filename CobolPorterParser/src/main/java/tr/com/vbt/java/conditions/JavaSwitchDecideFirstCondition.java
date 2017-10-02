package tr.com.vbt.java.conditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


/*
0420    DECIDE FOR FIRST CONDITION
0430    WHEN *PF-KEY='PF3'
0440       ESCAPE ROUTINE
0450    WHEN *PF-KEY='PF2'
0460       FETCH 'IDGPANAM'
0470    WHEN *PF-KEY='PF7'
0480       IF D_BAS > 15 THEN
0490           D_BAS:=D_BAS - 15
0500       ELSE
0510       D_BAS:=1
0520       END-IF
0530    WHEN *PF-KEY='PF8'
0540       Z:=KAYIT_SAY - 14
0550       IF D_BAS < Z THEN
0560          D_BAS :=D_BAS + 15
0570       END-IF
0580    WHEN NONE
0590          IGNORE
0600        END-DECIDE
}
*/


public class JavaSwitchDecideFirstCondition extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaSwitchDecideFirstCondition.class);
	
	private AbstractToken condition;
	
	protected boolean firstChildIfOperated=false;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
		
			//JavaClassElement.javaCodeBuffer.append("switch(true) ");
			//JavaClassElement.javaCodeBuffer.append( JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			this.writeChildrenJavaToStream();
			//JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+"// switch"+JavaConstants.NEW_LINE);
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
