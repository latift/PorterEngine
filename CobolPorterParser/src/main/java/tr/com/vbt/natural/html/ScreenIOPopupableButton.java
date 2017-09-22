package tr.com.vbt.natural.html;

import tr.com.vbt.framework.general.PFKey;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.util.ConverterConfiguration;

/**
 * 
 * Usage Genel: Usage1: *S** INPUT 'YOU CANNOT USE THE SYSTEM. ACCESS DENIED' /
 * S** 'REASON : ' / S** +MESSAGE (AD=O)
 * 
 * Usage2:
 * 
 * *S** INPUT (AD=O'_' SG=OFF ZP=OFF) WITH TEXT #MESSAGE S** 'User:' #USER 52X
 * 'Page:' '-'PAGE-PTR(AD=O SG=OFF ZP=ON) S** / 10X 'From' 7X 'Subject' 34X
 * 'Date' 10X 'S' S** / 10X '-'G-HEADERS(1)(AD=M'_') 10X S**
 * '-'G-HEADERS(2)(AD=M'_') 40X S** '-'G-HEADERS(3)(AD=M'_') S** / 10X '-'(10)
 * '-'(40) '-'(13) '-' S** /
 *
 *
 * Usage 3:
 *
 ** S**INPUT ( IP=OFF S** ) S** 027T '*** FARE TABLE ***' S** 060T *DATI (AD=OD )
 * S** 070T *TIMX (AD=OD ) S** S** 002T 'CHAPTER :' S** 012T SCR-CHAPTER
 * (AD=MYLT'_' CV=CV1 ) S** S** 002T 'ORIGIN' S** 013T ':'
 *
 */

public class ScreenIOPopupableButton implements ScreenIO {

	protected long XCoord;

	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;

	protected long YCoord;

	protected NaturalTagTypes tagType=NaturalTagTypes.POPUPABLE_INPUTFIELD;

	protected IOModeType modeType;

	protected String name="Gonder";

	protected String value;
	
	protected long minLength;
	
	protected long maxLength;
	
	private  static long  buttonYcoord;
	
	private String hotKey;
	
	protected HtmlColor color;
	
	private String caller;
	
	private String called;
	

	public ScreenIOPopupableButton(int xCoord, int yCoord, IOModeType modeType, String name, String value, XCoordinationTypes xCoordinationType,
			XCoordinationTypes yCoordinationType, int min, int max, String caller, String called) {
	
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}
		this.YCoord=yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=min;
		this.maxLength=max;
		this.caller=caller;
		this.called=called;
	}
	
	public ScreenIOPopupableButton(int xCoord, int yCoord, IOModeType modeType, String name, String value, XCoordinationTypes xCoordinationType,
			XCoordinationTypes yCoordinationType, int min, int max, String caller, String called,String hotKey) {
	
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}
		this.YCoord=yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=min;
		this.maxLength=max;
		this.caller=caller;
		this.called=called;
		this.hotKey=hotKey;
	}

	public long getXCoord() {
		return XCoord;
	}

	public void setXCoord(long xCoord) {
		XCoord = xCoord;
	}

	public XCoordinationTypes getxCoordinationType() {
		return xCoordinationType;
	}

	public void setxCoordinationType(XCoordinationTypes xCoordinationType) {
		this.xCoordinationType = xCoordinationType;
	}

	public XCoordinationTypes getyCoordinationType() {
		return yCoordinationType;
	}

	public void setyCoordinationType(XCoordinationTypes yCoordinationType) {
		this.yCoordinationType = yCoordinationType;
	}

	public long getYCoord() {
		return YCoord;
	}

	public void setYCoord(long yCoord) {
		YCoord = yCoord;
	}

	public NaturalTagTypes getTagType() {
		return tagType;
	}

	public void setTagType(NaturalTagTypes tagType) {
		this.tagType = tagType;
	}

	public IOModeType getModeType() {
		return modeType;
	}

	public void setModeType(IOModeType modeType) {
		this.modeType = modeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValueForEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMaxLength() {
		return maxLength;
	}

	@Override
	public boolean isDoubleQouta() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDestinationJspPage() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getHotKey() {
		return hotKey;
	}

	public void setHotKey(String hotKey) {
		this.hotKey = hotKey;
	}

	public HtmlColor getColor() {
		return color;
	}

	public void setColor(HtmlColor color) {
		this.color = color;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}
	




	

}
