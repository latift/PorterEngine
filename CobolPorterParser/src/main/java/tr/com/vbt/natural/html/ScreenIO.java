package tr.com.vbt.natural.html;

public interface ScreenIO {

	XCoordinationTypes getxCoordinationType();
	
	XCoordinationTypes getyCoordinationType();
	 
	int getXCoord();

	int getYCoord();
	
	void setXCoord(int xCoord);
	
	void setYCoord(int yCoord);
	
	NaturalTagTypes getTagType();
	
	String getValue();
	
	String getValueForEngine();
	
	String getName();
	
	int getMaxLength();
	
	boolean isDoubleQouta();
	

	public String getDestinationJspPage();
	
	public String getHotKey();

	public HtmlColor getColor();
	
	public String getCaller();

	public String getCalled();
	
}
