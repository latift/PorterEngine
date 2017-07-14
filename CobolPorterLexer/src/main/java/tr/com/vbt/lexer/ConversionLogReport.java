package tr.com.vbt.lexer;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.exceptions.ConversionException;
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
	
	private Date allConversionStartTime;
	
	private Date allConversionEndTime;
	
	private Date modulConversionStartTime=new Date();
	
	private Date modulConversionEndTime;
	
	private Long toplamCevrimSuresi;
	
	
	
	private Map<String,ConversionException> exceptionMap=new HashedMap();
	
	
	public static ConversionLogReport getInstance() {
		if (instance==null){
			instance=new ConversionLogReport();
		}
		return  instance;
	}
	

	@Override
	public String toString() {

		StringBuffer sb=new StringBuffer();
			sb.append(JavaConstants.NEW_LINE);
			sb.append("---------------------------------------------------------------------"+JavaConstants.NEW_LINE);
			sb.append("  Module: "+ ConversionLogModel.getInstance().getModule()+"   Mode:"+ConversionLogModel.getInstance().getMode()+JavaConstants.NEW_LINE);
			if(allConversionStartTime!=null){
				sb.append("  Cevrim Genel Başlama Saati: "+ allConversionStartTime.toString()+JavaConstants.NEW_LINE);
			}
			if(allConversionEndTime!=null){
				try {
					sb.append("  Cevrim Genel Bitiş Saati: "+ allConversionEndTime.toString()+JavaConstants.NEW_LINE);
					toplamCevrimSuresi=allConversionEndTime.getTime()-allConversionStartTime.getTime();
					
					toplamCevrimSuresi=toplamCevrimSuresi / 1000 / 60; //dakika cinsinden.
					sb.append("  Toplam Çevrim Süresi: "+ toplamCevrimSuresi.toString()+" dakika."+JavaConstants.NEW_LINE);
				} catch (Exception e) {
				
				}
			}
			if(modulConversionStartTime!=null){
				sb.append("  Modül Çevrim Başlama Saati: "+ modulConversionStartTime.toString()+JavaConstants.NEW_LINE);
			}
			if(modulConversionEndTime!=null){
				sb.append("  Modül Çevrim Bitiş Saati: "+ modulConversionEndTime.toString()+JavaConstants.NEW_LINE);
			}
			sb.append("  Program Sayısı: "+this.programCount+"  Çevrilen:"+this.convertedProgramCount+"  Program Çevrim Oranı:"+getProgramCevrimOrani()+JavaConstants.NEW_LINE);
			sb.append("  SubProgram Sayısı: "+this.subProgramCount+"  Çevrilen:"+this.convertedSubProgramCount+"  SubProgram Çevrim Oranı:"+getSubProgramCevrimOrani()+JavaConstants.NEW_LINE);
			sb.append("  Map Sayısı: "+this.mapCount+"  Çevrilen:"+this.convertedMapCount+"  Map Çevrim Oranı:"+getMapCevrimOrani()+JavaConstants.NEW_LINE);
			sb.append("  Modül Toplam Çevrim Oranı: "+getToplamCevrimOrani()+JavaConstants.NEW_LINE);
			sb.append("---------------------------------------------------------------------"+JavaConstants.NEW_LINE);
			
			return sb.toString();
	}
	
	private String exceptionReport(){
	
		StringBuffer sb=new StringBuffer();
		ConversionException conExc;
		sb.append(JavaConstants.NEW_LINE);
		sb.append("---------------------------------------------------------------------"+JavaConstants.NEW_LINE);
		if(allConversionStartTime!=null){
			sb.append("  Cevrim Genel Başlama Saati: "+ allConversionStartTime.toString()+JavaConstants.NEW_LINE);
		}
		if(allConversionEndTime!=null){
			try {
				sb.append("  Cevrim Genel Bitiş Saati: "+ allConversionEndTime.toString()+JavaConstants.NEW_LINE);
			} catch (Exception e) {
			
			}
		}
		Iterator iterator = exceptionMap.entrySet().iterator();
		
		while (iterator.hasNext()) {
	            Map.Entry mapEntry = (Map.Entry) iterator.next();
	            conExc=exceptionMap.get(mapEntry.getKey().toString());
	            sb.append(conExc.toString()+" Occurance: "+conExc.getOccurance()+JavaConstants.NEW_LINE);
	        }

		sb.append("---------------------------------------------------------------------"+JavaConstants.NEW_LINE);
		return sb.toString();
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
		ConversionLogReport.getInstance().setModulConversionEndTime(new Date());
		try {
			WriteToFile.appendToFile(ConversionLogReport.getInstance().toString(), ConversionLogModel.getInstance().getFullModuleReportFile());
		} catch (IOException e) {
			
		}
	}



	public void writeExceptionReport() {
		ConversionLogReport.getInstance().setModulConversionEndTime(new Date());
		try {
			WriteToFile.appendToFile(ConversionLogReport.getInstance().exceptionReport(), ConversionLogModel.getInstance().getExceptionReportFileFullPath());
		} catch (Exception e) {
			
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
		
		logger.warn(ConversionLogReport.getInstance().toString());
		
	}





	public Date getAllConversionStartTime() {
		return allConversionStartTime;
	}


	public void setAllConversionStartTime(Date allConversionStartTime) {
		this.allConversionStartTime = allConversionStartTime;
	}


	public Date getModulConversionStartTime() {
		return modulConversionStartTime;
	}


	public void setModulConversionStartTime(Date modulConversionStartTime) {
		this.modulConversionStartTime = modulConversionStartTime;
	}


	public Date getModulConversionEndTime() {
		return modulConversionEndTime;
	}


	public void setModulConversionEndTime(Date modulConversionEndTime) {
		this.modulConversionEndTime = modulConversionEndTime;
	}


	public Date getAllConversionEndTime() {
		return allConversionEndTime;
	}


	public void setAllConversionEndTime(Date allConversionEndTime) {
		this.allConversionEndTime = allConversionEndTime;
	}
	
	public void addException(ConversionException e){
		
		ConversionException curException=exceptionMap.get(e.toString());
		if(curException!=null){
			curException.increaseOccurance();
		}else{
			e.increaseOccurance();
			exceptionMap.put(e.toString(), e);
		}
	}

	
	
	
	
}
