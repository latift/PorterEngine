package tr.com.vbt.natural.html;

import java.math.BigDecimal;

import tr.com.vbt.util.ConverterConfiguration;

public class ScreenIOStringInput   implements ScreenIO{

	protected int XCoord;
	
	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;
	
	protected int YCoord;
	
	protected NaturalTagTypes tagType=NaturalTagTypes.INPUTFIELD;
	
	protected IOModeType modeType;
	
	protected String name;
	
	protected String value;
	
	protected String valueForEngine;
	
	protected int minLength;
	
	protected int maxLength;
	
	protected HtmlColor color;
	
	private String caller;
	
	private String called;
	

	public ScreenIOStringInput(int xCoord, int yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, int minLen, int maxLen) {
		
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	
	public ScreenIOStringInput(int xCoord, int yCoord, IOModeType modeType, String name, BigDecimal value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , int minLen, int maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = value.toString();
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public ScreenIOStringInput(int xCoord, int yCoord, IOModeType modeType, String name, int value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , int minLen, int maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = String.valueOf(value);
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public ScreenIOStringInput(int xCoord, int yCoord, IOModeType modeType, String name, float value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , int minLen, int maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.name=name;
		this.value = String.valueOf(value);
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public ScreenIOStringInput(int xCoord, String yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			XCoord = XCoord + xCoord;
		}

		int YCoordCarpan;
		
		if (yCoord != null && !yCoord.trim().isEmpty()) {
			YCoordCarpan = Integer.valueOf(yCoord.substring(0, yCoord.length() - 1));
			if (yCoord.contains("X")) {
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_X_LENGTH;
			} else if (yCoord.contains("T")) {
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			} else { // Hata durumda en azından boyle göstersin
				YCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			}
		}
		
		this.modeType = modeType;
		this.name=name;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
	}
	

	


	public int getXCoord() {
		return XCoord;
	}

	public void setXCoord(int xCoord) {
		XCoord = xCoord;
	}

	public XCoordinationTypes getxCoordinationType() {
		return xCoordinationType;
	}

	public void setxCoordinationType(XCoordinationTypes xCoordinationType) {
		this.xCoordinationType = xCoordinationType;
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

	public String getValueForEngine() {
		return valueForEngine;
	}

	public void setValueForEngine(String valueForEngine) {
		this.valueForEngine = valueForEngine;
	}

	public XCoordinationTypes getyCoordinationType() {
		return yCoordinationType;
	}

	public void setyCoordinationType(XCoordinationTypes yCoordinationType) {
		this.yCoordinationType = yCoordinationType;
	}

	@Override
	public int getMaxLength() {
		return maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(int maxLength) {
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
		// TODO Auto-generated method stub
		return null;
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
