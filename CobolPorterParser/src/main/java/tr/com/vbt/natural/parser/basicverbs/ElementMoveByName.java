package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**MOVE #NAME-START TO #NAME-END
 * 
 * 
 * MOVE  Uzunluk:0 Satir No:11 Tipi:OzelKelime
	#  Uzunluk:1 Satir No:11 Tipi:Karakter
	NAME-START  Uzunluk:0 Satir No:11 Tipi:Kelime
	TO  Uzunluk:0 Satir No:11 Tipi:OzelKelime
	#  Uzunluk:1 Satir No:11 Tipi:Karakter
	NAME-END  Uzunluk:0 Satir No:11 Tipi:Kelime
	
	
 * @author 47159500
 *
 */
public class ElementMoveByName extends AbstractCommand{
	
	private AbstractToken dataToMove;
	
	private List<AbstractToken> destVariable=new ArrayList<AbstractToken>();
	
	public ElementMoveByName(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Move","GENERAL.*.MOVE");
	}

	public ElementMoveByName(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		
		if(this.dataToMove != null){
			sb.append(" "+ReservedNaturalKeywords.MOVE_BY_NAME +"=\""+ this.dataToMove.getDeger()+" TO ");
			for (AbstractToken data : destVariable) {
				sb.append(" "+ data.getDeger());	
			}
			sb.append("\"\n");
		}else{
			sb.append(" "+ReservedNaturalKeywords.MOVE_BY_NAME +"=\""+ this.dataToMove+" TO ");
			for (AbstractToken data : destVariable) {
				sb.append(" "+ data.getDeger());	
			}
			sb.append("\"\n");
		}
	
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
		
		if(this.dataToMove != null){
			sb.append(" "+ReservedNaturalKeywords.MOVE +"=\""+ this.dataToMove.getDeger()+" TO ");
			for (AbstractToken data : destVariable) {
				sb.append(" "+ data.getDeger());	
			}
			sb.append("\"\n");
		}else{
			sb.append(" "+ReservedNaturalKeywords.MOVE +"=\""+ this.dataToMove+" TO ");
			for (AbstractToken data : destVariable) {
				sb.append(" "+ data.getDeger());	
			}
			sb.append("\"\n");
		}
		
		return sb.toString();
	}

	public AbstractToken getDataToMove() {
		return dataToMove;
	}

	public void setDataToMove(AbstractToken dataToMove) {
		this.dataToMove = dataToMove;
	}

	public List<AbstractToken> getDestVariable() {
		return destVariable;
	}

	public void setDestVariable(List<AbstractToken> destVariable) {
		this.destVariable = destVariable;
	}

	
	
	
	

	

	
}
