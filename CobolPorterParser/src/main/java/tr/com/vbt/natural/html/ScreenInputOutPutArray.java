package tr.com.vbt.natural.html;

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

public class ScreenInputOutPutArray implements ScreenIO {

	private String[] valueArray;
	
	protected int XCoord;

	protected XCoordinationTypes xCoordinationType;

	protected int YCoord;

	protected NaturalTagTypes tagType;

	protected IOModeType modeType;

	protected String name;

	protected String value;
	
	protected HtmlColor color;
	
	private String caller;
	
	private String called;

	public ScreenInputOutPutArray(int xCoord, String yCoord, NaturalTagTypes tagType, IOModeType modeType,
			String[] sCRLINES, XCoordinationTypes xCoordinationType) {

		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		int YCoordCarpan = Integer.valueOf(yCoord.substring(0, yCoord.length() - 1));
		if (yCoord.contains("X")) {
			YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_X_LENGTH;
		} else if (yCoord.contains("T")) {
			YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
		} else { // Hata durumda en azından boyle göstersin
			YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
		}

		this.valueArray=sCRLINES;

	}

	public int getXCoord() {
		return XCoord;
	}

	public void setXCoord(int xCoord) {
		XCoord = xCoord;
	}

	public int getYCoord() {
		return YCoord;
	}

	public void setYCoord(int yCoord) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String[] getValueArray() {
		return valueArray;
	}

	public void setValueArray(String[] valueArray) {
		this.valueArray = valueArray;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(XCoord + " " + YCoord + " " + tagType + " " + modeType + " ");
		for(int i=0;i<valueArray.length;i++){
			sb.append(valueArray[i]+ " ");
		}
		sb.append(JavaConstants.NEW_LINE);
		return sb.toString();
	}

	@Override
	public XCoordinationTypes getxCoordinationType() {
		return xCoordinationType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValueForEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XCoordinationTypes getyCoordinationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isDoubleQouta() {
		return false;
	}

	@Override
	public String getDestinationJspPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHotKey() {
		return "";
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
