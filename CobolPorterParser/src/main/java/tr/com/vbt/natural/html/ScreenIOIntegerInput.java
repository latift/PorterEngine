package tr.com.vbt.natural.html;

import tr.com.vbt.util.ConverterConfiguration;

public class ScreenIOIntegerInput   implements ScreenIO{

	protected long XCoord;
	
	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;
	
	protected long YCoord;
	
	protected NaturalTagTypes tagType=NaturalTagTypes.INPUTFIELD;
	
	protected IOModeType modeType;
	
	protected String name;
	
	protected long value;
	
	protected String valueForEngine;
	
	protected long minLength;
	
	protected long maxLength;
	
	protected HtmlColor color;
	
	
	public ScreenIOIntegerInput(long xCoord, long yCoord, IOModeType modeType, String name, long value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , long minLen, long maxLen) {

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
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public ScreenIOIntegerInput(long xCoord, String yCoord, IOModeType modeType, String name, long value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			XCoord = XCoord + xCoord;
		}

		long YCoordCarpan;
		
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
		
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public ScreenIOIntegerInput(long xCoord, long yCoord, IOModeType modeType, String name, String valueForEngine,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , long minLen, long maxLen) {

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
		this.valueForEngine = valueForEngine;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	
	public ScreenIOIntegerInput(long xCoord, String yCoord, IOModeType modeType, String name, String valueForEngine,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType , long minLen, long maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			XCoord = this.XCoord + xCoord;
		}

		long YCoordCarpan;
		
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
		this.valueForEngine = valueForEngine;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
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
		return Long.toString(value);
	}

	public void setValue(long value) {
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
