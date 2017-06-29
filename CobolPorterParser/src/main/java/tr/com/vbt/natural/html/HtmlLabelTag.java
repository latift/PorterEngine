package tr.com.vbt.natural.html;



public class HtmlLabelTag extends HtmlObject{
	
	public HtmlLabelTag(String value, int top, int right) {
		super(NaturalTagTypes.LABEL, "", value, top, right,"",HtmlColor.BLUE );
	}
	
	public HtmlLabelTag( int value, int top, int right) {
		super(NaturalTagTypes.LABEL,  "", value , top, right,"",HtmlColor.BLUE );
	}
	
	public HtmlLabelTag(float value, int top, int right) {
		super(NaturalTagTypes.LABEL, "", value , top, right,"" ,HtmlColor.BLUE);
	}
	
	public HtmlLabelTag(double value, int top, int right) {
		super(NaturalTagTypes.LABEL, "", value , top, right ,"",HtmlColor.BLUE);
	}
	


	public HtmlLabelTag(String value, int top, int right, HtmlColor color) {
		super(NaturalTagTypes.LABEL, "", value, top, right,"",color );
	}
	
	public HtmlLabelTag( int value, int top, int right, HtmlColor color) {
		super(NaturalTagTypes.LABEL,  "", value , top, right,"" ,color);
	}
	
	public HtmlLabelTag(float value, int top, int right, HtmlColor color) {
		super(NaturalTagTypes.LABEL, "", value , top, right,"",color );
	}
	
	public HtmlLabelTag(double value, int top, int right, HtmlColor color) {
		super(NaturalTagTypes.LABEL, "", value , top, right ,"",color);
	}

	

}
