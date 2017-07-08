package tr.com.vbt.lexer;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.util.WriteToFile;

public class ConversionLogReport {
	
	final static Logger logger = LoggerFactory.getLogger(ConversionLogReport.class);
	
	private static ConversionLogReport instance;
	
	private int programCount;
	
	private int convertedProgramCount;
	
	private int subProgramCount;
	
	private int convertedSubProgramCount;
	
	private int mapCount;
	
	private int convertedMapCount;
	
	public static ConversionLogReport getInstance() {
		if (instance==null){
			instance=new ConversionLogReport();
		}
		return  instance;
	}
	

	@Override
	public String toString() {
		return  "------------------------------------------------------------------------------"+JavaConstants.NEW_LINE+
				"  Cevrim: Tarihi "+ new Date().toString()+JavaConstants.NEW_LINE+
				"  Module: "+ ConversionLogModel.getInstance().getModule()+"   Mode:"+ConversionLogModel.getInstance().getMode()+JavaConstants.NEW_LINE+
				"  Program Sayısı: "+this.programCount+"  Çevrilen:"+this.convertedProgramCount+"  Program Çevrim Oranı:"+getProgramCevrimOrani()+JavaConstants.NEW_LINE+
				"  SubProgram Sayısı: "+this.subProgramCount+"  Çevrilen:"+this.convertedSubProgramCount+"  SubProgram Çevrim Oranı:"+getSubProgramCevrimOrani()+JavaConstants.NEW_LINE+
				"  Map Sayısı: "+this.mapCount+"  Çevrilen:"+this.convertedMapCount+"  Map Çevrim Oranı:"+getMapCevrimOrani()+JavaConstants.NEW_LINE+
				"  Toplam Çevrim Oranı: "+getToplamCevrimOrani()+JavaConstants.NEW_LINE+
				"------------------------------------------------------------------------------"+JavaConstants.NEW_LINE;
	}

	private float getMapCevrimOrani() {
		float mapCevrimOrani = 0;
		try {
			mapCevrimOrani=convertedMapCount*100/mapCount;
		} catch (Exception e) {
		}
		return mapCevrimOrani;
	}

	private float getSubProgramCevrimOrani() {
		float cevrimOrani = 0;
		try {
			cevrimOrani=convertedSubProgramCount*100/subProgramCount;
		} catch (Exception e) {
			
		}
		return cevrimOrani;
	}

	private float getProgramCevrimOrani() {
		float cevrimOrani = 0;
		try {
			cevrimOrani=convertedProgramCount*100/programCount;
		} catch (Exception e) {
			
		}
		return cevrimOrani;
	}

	private float getToplamCevrimOrani() {
		float cevrimOrani = 0;
		try {
			cevrimOrani=(convertedMapCount+ convertedSubProgramCount+ convertedProgramCount)*100/(mapCount+subProgramCount+programCount);
		} catch (Exception e) {
			
		}
		return cevrimOrani;
	}

	public void writeReport(){
		logger.toString();
		try {
			WriteToFile.appendToFile(ConversionLogReport.getInstance().toString(), ConversionLogModel.getInstance().getFullModuleReportFile());
		} catch (IOException e) {
			
		}
	}

	public void reset() {
		instance=null;
		
	}
	public void conversionStart() {
		if(ConversionLogModel.getInstance().isSubProgram()){
			subProgramCount++;
		}else if(ConversionLogModel.getInstance().isProgram()){
			programCount++;
		}else if(ConversionLogModel.getInstance().isMap()){
			mapCount++;
		}
		
	}
	
	public void conversionSuccess() {
		if(ConversionLogModel.getInstance().isSubProgram()){
			convertedSubProgramCount++;
		}else if(ConversionLogModel.getInstance().isProgram()){
			convertedProgramCount++;
		}else if(ConversionLogModel.getInstance().isMap()){
			convertedMapCount++;
		}
		
		logger.debug(ConversionLogReport.getInstance().toString());
		logger.debug("..");
		
	}
	
}
