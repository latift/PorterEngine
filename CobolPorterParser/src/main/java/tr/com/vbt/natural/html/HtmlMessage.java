package tr.com.vbt.natural.html;



public class HtmlMessage extends HtmlObject{
	
	private String message;
	
	//Koordinatlar 0 verildi çünkü message popup olarak verilecek.
	public HtmlMessage(String message) {
		super(NaturalTagTypes.MESSAGE, "", "", 0,0,"");
		this.message=message;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
