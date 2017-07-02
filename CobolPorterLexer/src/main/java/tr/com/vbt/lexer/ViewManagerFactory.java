package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewManagerFactory {

	final static Logger logger = LoggerFactory.getLogger(ViewManagerFactory.class);

	private static ViewManager instance;

	public static ViewManager getInstance() {
		if (instance == null) {
			if(ConversionLogModel.getInstance().getModuleOnly().equals(THYModules.TPS.toString())){
				instance = ViewManagerTPS.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals(THYModules.PERS.toString())){
				instance = ViewManagerPERS.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals(THYModules.MEIKRAM.toString())){
				instance = ViewManagerIKR.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals(THYModules.MEUCRETL.toString())){
				instance = ViewManagerUCR.getInstance();
			}else if(ConversionLogModel.getInstance().getModuleOnly().equals(THYModules.MEGIDER.toString())){
				instance = ViewManagerGDR.getInstance();
			}else{
				logger.debug("Module Bilgisi Hatali:"+ConversionLogModel.getInstance().getModuleOnly());
			}
		}
		
		return instance;
	}


	
}
