package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//* This is a comment *    --> TokenSize: 6
//***This is also comment ***** TokenSize:5
//*** This is also comment ***** TokenSize:6
public class ElementComment extends AbstractCommand{
	
	private List<String> commentList=new ArrayList<String>();
	
	public ElementComment(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementComment(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementComment(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}
	
	public ElementComment(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementComment","GENERAL.*.COMMENT");
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.COMMENT_ENTRY +"=\"");
		sb.append("Comment: ");
		for (String src : commentList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.COMMENT_ENTRY +"=\"");
		sb.append("Comment: ");
		for (String src : commentList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public List<String> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<String> commentList) {
		this.commentList = commentList;
	}
	
	

	
}
