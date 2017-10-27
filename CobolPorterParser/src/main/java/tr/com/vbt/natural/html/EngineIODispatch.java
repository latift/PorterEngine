package tr.com.vbt.natural.html;

public class EngineIODispatch extends AbstractEngineIO implements EngineIO {

	protected long XCoord;

	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;

	protected long YCoord;

	protected NaturalTagTypes tagType=NaturalTagTypes.DISPATCH;

	protected IOModeType modeType;

	protected String destinationJspPage;
	
	protected String name="";

	protected String value="";
	
	protected HtmlColor color;
	
	public EngineIODispatch(String destinationJspPage) {

		super();
		this.destinationJspPage=destinationJspPage;
	}

	@Override
	public XCoordinationTypes getxCoordinationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XCoordinationTypes getyCoordinationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getXCoord() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getYCoord() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setXCoord(long xCoord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYCoord(long yCoord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NaturalTagTypes getTagType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getValueForEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMaxLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDoubleQouta() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDestinationJspPage() {
		return destinationJspPage;
	}

	public void setDestinationJspPage(String destinationJspPage) {
		this.destinationJspPage = destinationJspPage;
	}

	@Override
	public String getHotKey() {
		// TODO Auto-generated method stub
		return "";
	}
	

	public HtmlColor getColor() {
		return color;
	}

	public void setColor(HtmlColor color) {
		this.color = color;
	}

	@Override
	public String getCaller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCalled() {
		// TODO Auto-generated method stub
		return null;
	}
	



	

}
