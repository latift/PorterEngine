package tr.com.vbt.natural.html;

import java.math.BigDecimal;

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

public class EngineIOLabel extends AbstractEngineIO implements EngineIO {

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

	private boolean doubleQouta;
	
	private String caller;
	
	private String called;
	
	protected HtmlColor color=HtmlColor.BLUE;  //Default Blue

	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		if(value==null || value.isEmpty()){
			value="";
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
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, HtmlColor color) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		if(value==null || value.isEmpty()){
			value="";
		}
		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
		this.color=color;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, boolean doubleQouta) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		if(value==null || value.isEmpty()){
			value="";
		}
		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
		this.doubleQouta=doubleQouta;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, boolean doubleQouta, HtmlColor color) {

		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}

		if(value==null || value.isEmpty()){
			value="";
		}
		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
		this.doubleQouta=doubleQouta;
		this.color=color;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, long value,
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
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, long value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, HtmlColor color) {
		
		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}
		
		
		
		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
		this.color=color;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, float value,
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
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, float value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, HtmlColor color) {
		
		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
		if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
			XCoord = xCoord;
		} else {
			this.XCoord = this.XCoord + xCoord;
		}
		
		XCoord = xCoord;
		YCoord = yCoord;
		this.modeType = modeType;
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.minLength=minLen;
		this.maxLength=maxLen;
		this.color=color;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, BigDecimal value,
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
				if(value.intValue()==0 ){
					this.value = "";
				}else{
					this.value = String.valueOf(value);
				}
				this.xCoordinationType = xCoordinationType;
				this.yCoordinationType = yCoordinationType;
				this.minLength=minLen;
				this.maxLength=maxLen;
	}
	
	public EngineIOLabel(long xCoord, long yCoord, IOModeType modeType, BigDecimal value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, long minLen, long maxLen, HtmlColor color) {
		
		// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
				if (xCoordinationType.equals(XCoordinationTypes.EXACT)) {
					XCoord = xCoord;
				} else {
					this.XCoord = this.XCoord + xCoord;
				}
				
				XCoord = xCoord;
				YCoord = yCoord;
				this.modeType = modeType;
				if(value.intValue()==0 ){
					this.value = "";
				}else{
					this.value = String.valueOf(value);
				}
				this.xCoordinationType = xCoordinationType;
				this.yCoordinationType = yCoordinationType;
				this.minLength=minLen;
				this.maxLength=maxLen;
				this.color=color;
	}

	public EngineIOLabel(long xCoord, String yCoord, IOModeType modeType, String value,
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

		if(value==null || value.isEmpty()){
			value="";
		}
		
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;

	}
	
	public EngineIOLabel(long xCoord, String yCoord, IOModeType modeType, String value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, HtmlColor color) {

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

		if(value==null || value.isEmpty()){
			value="";
		}
		
		this.modeType = modeType;
		this.value = value;
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.color=color;

	}
	
	public EngineIOLabel(long xCoord, String yCoord, IOModeType modeType, long value,
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
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
	}
	
	public EngineIOLabel(long xCoord, String yCoord, IOModeType modeType, long value,
			XCoordinationTypes xCoordinationType,XCoordinationTypes yCoordinationType, HtmlColor color) {
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
		if(value==0 ){
			this.value = "";
		}else{
			this.value = String.valueOf(value);
		}
		this.xCoordinationType = xCoordinationType;
		this.yCoordinationType = yCoordinationType;
		this.color=color;
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
		return doubleQouta;
	}

	public void setDoubleQouta(boolean doubleQouta) {
		this.doubleQouta = doubleQouta;
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
