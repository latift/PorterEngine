package tr.com.vbt.natural.html;

import java.math.BigDecimal;

import tr.com.vbt.util.ConverterConfiguration;

public class EngineIOStringInput  extends AbstractEngineIO implements EngineIO{

	protected long XCoord;
	
	protected XCoordinationTypes xCoordinationType;
	
	protected XCoordinationTypes yCoordinationType;
	
	protected long YCoord;
	
	protected NaturalTagTypes tagType=NaturalTagTypes.INPUTFIELD;
	
	protected IOModeType modeType;
	
	protected String name;
	
	protected String value;
	
	protected String valueForEngine;
	
	protected long minLength;
	
	protected long maxLength;
	
	protected HtmlColor color;
	
	private String caller;
	
	private String called;
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, long minLen, long maxLen, String controlVariableName) {
		this(xCoord, yCoord, modeType, name, value, xCoordinationType, yCoordinationType,minLen,maxLen);
		this.controlVariableName=controlVariableName;
	}

	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, long minLen, long maxLen) {
		
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
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, BigDecimal value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, long minLen, long maxLen, String controlVariableName) {
		this(xCoord, yCoord, modeType, name, value, xCoordinationType, yCoordinationType,minLen,maxLen);
		this.controlVariableName=controlVariableName;
	}
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, BigDecimal value,
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
		this.value = value.toString();
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, long value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, long minLen, long maxLen, String controlVariableName) {
		this(xCoord, yCoord, modeType, name, value, xCoordinationType, yCoordinationType,minLen,maxLen);
		this.controlVariableName=controlVariableName;
	}
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, long value,
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
		this.value = String.valueOf(value);
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, float value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType, long minLen, long maxLen, String controlVariableName) {
		this(xCoord, yCoord, modeType, name, value, xCoordinationType, yCoordinationType,minLen,maxLen);
		this.controlVariableName=controlVariableName;
	}
	
	public EngineIOStringInput(long xCoord, long yCoord, IOModeType modeType, String name, float value,
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
		this.value = String.valueOf(value);
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public EngineIOStringInput(long xCoord, String yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType, XCoordinationTypes yCoordinationType,String controlVariableName) {
		this(xCoord, yCoord, modeType, name, value, xCoordinationType, yCoordinationType);
		this.controlVariableName=controlVariableName;
	}
	
	public EngineIOStringInput(long xCoord, String yCoord, IOModeType modeType, String name, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType) {

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
	public long getMaxLength() {
		return maxLength;
	}

	public long getMinLength() {
		return minLength;
	}

	public void setMinLength(long minLength) {
		this.minLength = minLength;
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
