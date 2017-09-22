package tr.com.vbt.natural.html;

public interface ScreenIO {

	XCoordinationTypes getxCoordinationType();
	
	XCoordinationTypes getyCoordinationType();
	 
	long getXCoord();

	long getYCoord();
	
	void setXCoord(long xCoord);
	
	void setYCoord(long yCoord);
	
	NaturalTagTypes getTagType();
	
	String getValue();
	
	String getValueForEngine();
	
	String getName();
	
	long getMaxLength();
	
	boolean isDoubleQouta();
	

	public String getDestinationJspPage();
	
	public String getHotKey();

	public HtmlColor getColor();
	
	public String getCaller();

	public String getCalled();
	
}
