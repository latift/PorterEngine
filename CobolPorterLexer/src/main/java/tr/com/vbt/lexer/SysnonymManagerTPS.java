package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

public class SysnonymManagerTPS extends AbstractSysnoymManager implements SynonymManager {

	private static Map<String, String> synonymToRealTableNameMap = new HashMap<>();
	
	final static Logger logger = LoggerFactory.getLogger(SysnonymManagerTPS.class);
	
	private static SysnonymManagerTPS instance;
	
	public static SysnonymManagerTPS getInstance() {
		if (instance==null){
			instance=new SysnonymManagerTPS();
		}
		return  instance;
	}
	
	private SysnonymManagerTPS() {
		super();
		loadSynoymMap();
	}

	final protected void loadSynoymMap() {
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UHM_LIMAN_TLF", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UHM_LIMAN_HATBKM", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_UHM_AKARYAKIT", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TKS_LIMAN", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_UPLIM", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_RUNWAY_SPECIFIC", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_RUNWAY_GENERAL", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_MVTADR", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_LIMAN", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_GEN_DATA2", ConverterConfiguration.DEFAULT_SCHEMA+"_EKP_LIMAN");


		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_ROE", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_MILES");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_DOMESTIC_TAX", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_DOMESTIC");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PERF39", ConverterConfiguration.DEFAULT_SCHEMA+"_ERP_HAR");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PERF70", ConverterConfiguration.DEFAULT_SCHEMA+"_PERF61");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PERF69", ConverterConfiguration.DEFAULT_SCHEMA+"_PERF61");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_PERF68", ConverterConfiguration.DEFAULT_SCHEMA+"_PERF61");

		
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_TABLES", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_COUNTRY", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_CITY", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_TABLES", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_COUNTRY", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_CITY", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE", ConverterConfiguration.DEFAULT_SCHEMA+"_KET_AIRLINE");
		
		
		
		synonymToRealTableNameMap.put("UHM_LIMAN_TLF", "EKP_LIMAN");
		synonymToRealTableNameMap.put("UHM_LIMAN_HATBKM", "EKP_LIMAN");
		synonymToRealTableNameMap.put("UHM_AKARYAKIT", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TKS_LIMAN", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_UPLIM", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_RUNWAY_SPECIFIC", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_RUNWAY_GENERAL", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_MVTADR", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_LIMAN", "EKP_LIMAN");
		synonymToRealTableNameMap.put("TAR_GEN_DATA2", "EKP_LIMAN");


		synonymToRealTableNameMap.put("KET_ROE", "KET_MILES");
		synonymToRealTableNameMap.put("KET_DOMESTIC_TAX", "KET_DOMESTIC");

		synonymToRealTableNameMap.put("PERF39", "ERP_HAR");
		synonymToRealTableNameMap.put("PERF70", "PERF61");
		synonymToRealTableNameMap.put("PERF69", "PERF61");
		synonymToRealTableNameMap.put("PERF68", "PERF61");

		
		synonymToRealTableNameMap.put("KET_TABLES", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_COUNTRY", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_CITY", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_AIRLINE", "KET_AIRLINE");
		
		synonymToRealTableNameMap.put("KET_TABLES", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_COUNTRY", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_CITY", "KET_AIRLINE");
		synonymToRealTableNameMap.put("KET_AIRLINE", "KET_AIRLINE");

	}


}
