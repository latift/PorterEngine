package tr.com.vbt.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.util.ConverterConfiguration;

public class SysnonymManagerMEIKRAM extends AbstractSysnoymManager implements SynonymManager {

	final static Logger logger = LoggerFactory.getLogger(SysnonymManagerMEIKRAM.class);
	
	private static SysnonymManagerMEIKRAM instance;
	
	public static SysnonymManagerMEIKRAM getInstance() {
		if (instance==null){
			instance=new SysnonymManagerMEIKRAM();
		}
		return  instance;
	}
	
	private SysnonymManagerMEIKRAM() {
		super();
		loadSynoymMap();
	}



	final protected void loadSynoymMap() {
		
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_FATURA",ConverterConfiguration.DEFAULT_SCHEMA+"_GZT_IRSALIYE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_FRM_ICMAL",ConverterConfiguration.DEFAULT_SCHEMA+"_GZT_IRSALIYE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_CAT_YUK",ConverterConfiguration.DEFAULT_SCHEMA+"_CAT_SEVK");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_ICMAL",ConverterConfiguration.DEFAULT_SCHEMA+"_GZT_IRSALIYE");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_FRMMLZ_FIYAT",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_FRM_MALZEME");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_UCAK",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_FRM_MALZEME");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_YUKPLAN",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_GAZETE_UCUS");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_YUKPLAN_EXTRA",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_GAZETE_UCUS");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_SEKTOR",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_MENU");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_TARIFE",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_MENU");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_TARIFE_UYG",ConverterConfiguration.DEFAULT_SCHEMA+"_IKR_MENU");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_ULKE",ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_SEFOP");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_UCAK",ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_SEFOP");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_UPSEKTOR",ConverterConfiguration.DEFAULT_SCHEMA+"_TAR_SEKTOR");
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA+"_THY_CAT_1",ConverterConfiguration.DEFAULT_SCHEMA+"_THY_CAT");
		
		synonymToRealTableNameMap.put("IKR_FATURA","GZT_IRSALIYE");
		synonymToRealTableNameMap.put("IKR_FRM_ICMAL","GZT_IRSALIYE");
		synonymToRealTableNameMap.put("CAT_YUK","CAT_SEVK");
		synonymToRealTableNameMap.put("IKR_ICMAL","GZT_IRSALIYE");
		synonymToRealTableNameMap.put("IKR_FRMMLZ_FIYAT","IKR_FRM_MALZEME");
		synonymToRealTableNameMap.put("IKR_UCAK","IKR_FRM_MALZEME");
		synonymToRealTableNameMap.put("IKR_YUKPLAN","IKR_GAZETE_UCUS");
		synonymToRealTableNameMap.put("IKR_YUKPLAN_EXTRA","IKR_GAZETE_UCUS");
		synonymToRealTableNameMap.put("IKR_SEKTOR","IKR_MENU");
		synonymToRealTableNameMap.put("IKR_TARIFE","IKR_MENU");
		synonymToRealTableNameMap.put("IKR_TARIFE_UYG","IKR_MENU");
		synonymToRealTableNameMap.put("TAR_ULKE","TAR_SEFOP");
		synonymToRealTableNameMap.put("TAR_UCAK","TAR_SEFOP");
		synonymToRealTableNameMap.put("TAR_UPSEKTOR","TAR_SEKTOR");
		synonymToRealTableNameMap.put("THY_CAT_1","THY_CAT");
		

	}







}
