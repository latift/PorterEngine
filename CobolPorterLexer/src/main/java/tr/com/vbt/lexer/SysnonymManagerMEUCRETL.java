package tr.com.vbt.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.util.ConverterConfiguration;

public class SysnonymManagerMEUCRETL extends AbstractSysnoymManager implements SynonymManager {

	final static Logger logger = LoggerFactory.getLogger(SysnonymManagerMEUCRETL.class);
	
	private static SysnonymManagerMEUCRETL instance;
	
	public static SysnonymManagerMEUCRETL getInstance() {
		if (instance==null){
			instance=new SysnonymManagerMEUCRETL();
		}
		return  instance;
	}
	
	private SysnonymManagerMEUCRETL() {
		super();
		loadSynoymMap();
	}



	final protected void loadSynoymMap() {
	
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_HRCH02",ConverterConfiguration.DEFAULT_SCHEMA+"_HRCH01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_HRCH03",ConverterConfiguration.DEFAULT_SCHEMA+"_HRCH01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF02",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF03",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF04",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF05",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF06",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF06L",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF07",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF07A",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF07L",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF08",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF09",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF12",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF13",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF14",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF15",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF16",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF17",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF18",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF19",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF25",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF26",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF27",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF30",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF36",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF11L",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF11");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF202",ConverterConfiguration.DEFAULT_SCHEMA+"_MUVF20");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PASF01",ConverterConfiguration.DEFAULT_SCHEMA+"_PASF00");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PASF02",ConverterConfiguration.DEFAULT_SCHEMA+"_PASF00");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PASF03",ConverterConfiguration.DEFAULT_SCHEMA+"_PASF00");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PERF80",ConverterConfiguration.DEFAULT_SCHEMA+"_PASF00");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10_1",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10Y",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10Y_1",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF10");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30_1",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30Y",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30Y_1",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF30");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF41",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF40");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF43",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF42");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF43Y",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF42");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF44",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF42");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF50Y",ConverterConfiguration.DEFAULT_SCHEMA+"_UCRF50");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UCRZORF",ConverterConfiguration.DEFAULT_SCHEMA+"_UCR_ZOR93A");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF02",ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF03",ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF01");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF04",ConverterConfiguration.DEFAULT_SCHEMA+"_ZORF01");
		
		
		synonymToRealTableNameMap.put("HRCH02","HRCH01");
		synonymToRealTableNameMap.put("HRCH03","HRCH01");
		synonymToRealTableNameMap.put("MUVF02","MUVF01");
		synonymToRealTableNameMap.put("MUVF03","MUVF01");
		synonymToRealTableNameMap.put("MUVF04","MUVF01");
		synonymToRealTableNameMap.put("MUVF05","MUVF01");
		synonymToRealTableNameMap.put("MUVF06","MUVF01");
		synonymToRealTableNameMap.put("MUVF06L","MUVF01");
		synonymToRealTableNameMap.put("MUVF07","MUVF01");
		synonymToRealTableNameMap.put("MUVF07A","MUVF01");
		synonymToRealTableNameMap.put("MUVF07L","MUVF01");
		synonymToRealTableNameMap.put("MUVF08","MUVF01");
		synonymToRealTableNameMap.put("MUVF09","MUVF01");
		synonymToRealTableNameMap.put("MUVF12","MUVF01");
		synonymToRealTableNameMap.put("MUVF13","MUVF01");
		synonymToRealTableNameMap.put("MUVF14","MUVF01");
		synonymToRealTableNameMap.put("MUVF15","MUVF01");
		synonymToRealTableNameMap.put("MUVF16","MUVF01");
		synonymToRealTableNameMap.put("MUVF17","MUVF01");
		synonymToRealTableNameMap.put("MUVF18","MUVF01");
		synonymToRealTableNameMap.put("MUVF19","MUVF01");
		synonymToRealTableNameMap.put("MUVF25","MUVF01");
		synonymToRealTableNameMap.put("MUVF26","MUVF01");
		synonymToRealTableNameMap.put("MUVF27","MUVF01");
		synonymToRealTableNameMap.put("MUVF30","MUVF01");
		synonymToRealTableNameMap.put("MUVF36","MUVF01");
		synonymToRealTableNameMap.put("MUVF11L","MUVF11");
		synonymToRealTableNameMap.put("MUVF202","MUVF20");
		synonymToRealTableNameMap.put("PASF01","PASF00");
		synonymToRealTableNameMap.put("PASF02","PASF00");
		synonymToRealTableNameMap.put("PASF03","PASF00");
		synonymToRealTableNameMap.put("PERF80","PASF00");
		synonymToRealTableNameMap.put("UCRF10_1","UCRF10");
		synonymToRealTableNameMap.put("UCRF10Y","UCRF10");
		synonymToRealTableNameMap.put("UCRF10Y_1","UCRF10");
		synonymToRealTableNameMap.put("UCRF30_1","UCRF30");
		synonymToRealTableNameMap.put("UCRF30Y","UCRF30");
		synonymToRealTableNameMap.put("UCRF30Y_1","UCRF30");
		synonymToRealTableNameMap.put("UCRF41","UCRF40");
		synonymToRealTableNameMap.put("UCRF43","UCRF42");
		synonymToRealTableNameMap.put("UCRF43Y","UCRF42");
		synonymToRealTableNameMap.put("UCRF44","UCRF42");
		synonymToRealTableNameMap.put("UCRF50Y","UCRF50");
		synonymToRealTableNameMap.put("UCRZORF","UCR_ZOR93A");
		synonymToRealTableNameMap.put("ZORF02","ZORF01");
		synonymToRealTableNameMap.put("ZORF03","ZORF01");
		synonymToRealTableNameMap.put("ZORF04","ZORF01");
		synonymToRealTableNameMap.put("UCRF01Y","UCRF01");

		

	}







}
