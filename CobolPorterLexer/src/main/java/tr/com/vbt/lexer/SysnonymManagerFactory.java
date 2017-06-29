package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysnonymManagerFactory {

	private static Map<String, String> synonymToRealTableNameMap = new HashMap<>();

	final static Logger logger = LoggerFactory.getLogger(SysnonymManagerFactory.class);

	public static SynonymManager getInstance() {
		
		return SysnonymManagerAll.getInstance();
		
	/*	if (instance == null) {
			if(ConversionLogModel.getInstance().getModuleOnly().equals("TPS")){
				instance = SysnonymManagerTPS.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals("PERS")){
				instance = SysnonymManagerPERS.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals("MEIKRAM")){
				instance = SysnonymManagerMEIKRAM.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals("MEUCRETL")){
				instance = SysnonymManagerMEUCRETL.getInstance();
			}
		}
		return instance;*/
	}


	
}
