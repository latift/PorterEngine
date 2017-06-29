package tr.com.vbt.natural.parser.database;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;

/**
 * *S**  GET KET-CITY ISNS(ITEM)
*S**  GET KET-CITY ISN
 */
public class ElementGet extends AbstractCommand{
	
	private AbstractToken viewName; //Liman
	
	private AbstractToken isnToken;
	
	private  ArrayToken isnArrayToken;
	
	
	public ElementGet(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Get","DATABASE.*.GET");
	}

	public ElementGet(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.GET +" \"");
	    sb.append(" "+ viewName.getDeger().toString());	
	    if(isnToken!=null){
	    	sb.append(isnToken.getDeger().toString());
	    }
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.GET +" \"");
	    sb.append(" "+ viewName.getDeger().toString());	
	    if(isnToken!=null){
	    	sb.append(isnToken.getDeger().toString());
	    }
	    sb.append("\"\n");
		return sb.toString();
	}

	public AbstractToken getViewName() {
		return viewName;
	}

	public void setViewName(AbstractToken viewName) {
		this.viewName = viewName;
	}

	public AbstractToken getIsnToken() {
		return isnToken;
	}

	public void setIsnToken(AbstractToken isnToken) {
		this.isnToken = isnToken;
	}

	public ArrayToken getIsnArrayToken() {
		return isnArrayToken;
	}

	public void setIsnArrayToken(ArrayToken isnArrayToken) {
		this.isnArrayToken = isnArrayToken;
	}
	
	

	


	
}
