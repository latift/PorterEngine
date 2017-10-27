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

public class EngineIOUndefined extends AbstractEngineIO implements EngineIO {

	protected long XCoord;

	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;

	protected long YCoord;

	protected NaturalTagTypes tagType=NaturalTagTypes.LABEL;

	protected IOModeType modeType;

	protected String name;

	protected String value;
	
	protected long minLength;
	
	protected long maxLength;
	
	protected HtmlColor color;
	

	public EngineIOUndefined(long xCoord, long yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}

	public EngineIOUndefined(long xCoord, String yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType) {

		long YCoordCarpan;

		if (yCoord != null && !yCoord.trim().isEmpty()) {
			
			//13.0T --> 13
			YCoordCarpan = Integer.valueOf(yCoord.substring(0, yCoord.length()- 3));  //13.0T --> 13
			if (yCoord.contains("X")) {
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_X_LENGTH;
			} else if (yCoord.contains("T")) {
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			} else { // Hata durumda en azından boyle göstersin
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			}
		}

		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;

	}

	public long getXCoord() {
		return XCoord;
	}

	public void setXCoord(long xCoord) {
		XCoord = xCoord;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return XCoord + " " + YCoord + " " + tagType + " " + modeType + " " + value + JavaConstants.NEW_LINE;
	}

	public XCoordinationTypes getxCoordinationType() {
		return xCoordinationType;
	}

	public void setxCoordinationType(XCoordinationTypes xCoordinationType) {
		this.xCoordinationType = xCoordinationType;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValueForEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	public XCoordinationTypes getyCoordinationType() {
		return yCoordinationType;
	}

	public void setyCoordinationType(XCoordinationTypes yCoordinationType) {
		this.yCoordinationType = yCoordinationType;
	}

	public long getMinLength() {
		return minLength;
	}

	public void setMinLength(long minLength) {
		this.minLength = minLength;
	}

	public long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(long maxLength) {
		this.maxLength = maxLength;
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
