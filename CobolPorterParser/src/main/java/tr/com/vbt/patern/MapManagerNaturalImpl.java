package tr.com.vbt.patern;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;

public class MapManagerNaturalImpl  implements MapManager{
	
	final static Logger logger = LoggerFactory.getLogger(MapManagerNaturalImpl.class);
	
	private static MapManager instance = null;

	protected HashMap<String, String> localVariablesGetterMap=new HashMap<String, String>();
	
	protected HashMap<String, String> localVariablesSetterMap=new HashMap<String, String>();
	
	protected HashMap<String, String> pojosGetterMap=new HashMap<String, String>();
	
	protected HashMap<String, String> pojosSetterMap=new HashMap<String, String>();

	
	/**
	 * *S**01 TESL VIEW OF AYK-TESL                                                                    
*S**  02 T-TESNO                                                                                
*S**  02 T-TARIH2                                                                               
*S**  02 REDEFINE T-TARIH2                                                                      
*S**    03 T-TARIH2-N (N8)                                                                      
*S**  02 T-SEFCAR                                                                               
*S**  02 C-T-LOG-USER                                                                           
*S**  02 T-LOG-USER (1:50)                                                                      
*S**    03 T-USER-NAME                                                                          
*S**    03 T-USER-TERM                                                                          
*S**    03 T-USER-DATE-TIME                                                                     
*S**    03 REDEFINE T-USER-DATE-TIME                                                            
*S**      04 T-USER-DATE (N8)                                                                   
*S**      04 T-USER-TIME (N4)                                                                   
*S**    03 T-USER-REMARKS   
	 */
	

	

	private MapManagerNaturalImpl() {
		super();
		
		/**  POJO
		 * *S**1 LIMAN VIEW OF TAR-LIMAN                                                                   	
		 	*S**  2 L-ULKE-KODU                                                                             
		 	*S**  2 L-LIMAN-KODU  
		 	*
			public TarLiman LIMAN;
			public List<TarLiman> LIMAN_RESULT_LIST;
			@Autowired
			public TarLimanDAO LIMAN_DAO;
			
		 * ULKE_KODU=LIMAN.getLUlkeKodu();
		 */
		//pojosMap.put("T_TESNO", "TESL.getTTesno()");
		//pojosMap.put("T-TARIH2", "TESL.getTTarih2()");
		//pojosMap.put("T-TARIH2-N", "((int)TESL.getTTarih2())");
		//Pojo Kural: LEvel 1 ise bir şey yapma
		//Level 2 ise atasının Stringini al(TESL) ve kendisine get ekle
		//      Harfleri küçüklt ve nokta ile birleştir.
		//Level  Tipi redefine ise onu ve chield larını es geciyoruz.
		
		
		
		
		/**Local Değişken:
		*
		 * 
		 *  *S**1 #T-KLMTARSEF2 (A15)                                                                       
			*S**1 REDEFINE #T-KLMTARSEF2                                                                    
			*S**  2 #T-KLM-LIMAN (A3)                                                                       
			*S**  2 #T-KLM-TARIH (N8)                                                                       
			*S**  2 REDEFINE #T-KLM-TARIH                                                                   
			*S**    3  #T-KLM-TARIH-A (A8)                                                                  
			*S**  2 #T-KLM-SEFNO (N4)                                                                       
			*S**1 #T-KLMTARSEF3 (A15)  
		 */
		
		//localVariablesMap.put("", value);
		//Local Değişken Kural:
		// Level1 ise bir şey yapma: Tanim ekleme. Client Cod Map de tanım bulamazsa Sorduğu Key i geri dönecek.
		// Level2 ise:  Atasıyla aynı olan childları gezerek offsetini hesapla.	 
		
	}
	
	
	public static MapManager getInstance(){
		if(instance == null) {
	         instance = new MapManagerNaturalImpl();
	      }
	      return instance;
	}
	
	public void resetInstance(){
		instance=null;
	}


	@Override
	public void registerCommandToPojoMap(String commandName, AbstractCommand command){
		this.pojosGetterMap.put(commandName, command.getDataTypeGetterMapString());
		this.pojosSetterMap.put(commandName, command.getDataTypeSetterMapString());
	}


	@Override
	public void registerCommandToLocalVariablesMap(String commandName, AbstractCommand command) {
		this.localVariablesGetterMap.put(commandName, command.getDataTypeGetterMapString());
		this.localVariablesSetterMap.put(commandName, command.getDataTypeSetterMapString());
		
	}


	@Override
	public void printMaps() {
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("*********************************POJO GETTER******************************************");
		for (String key: pojosGetterMap.keySet()) {
		    logger.info("key : " + key);
		    logger.info("value : " + pojosGetterMap.get(key));
		}
		logger.info("*********************************POJO SETTER******************************************");
		for (String key: pojosSetterMap.keySet()) {
		    logger.info("key : " + key);
		    logger.info("value : " + pojosSetterMap.get(key));
		}
		
		logger.info("********************************LOCAL VARIABLE GETTER*********************************");
		for (String key: localVariablesGetterMap.keySet()) {
		    logger.info("key : " + key);
		    logger.info("value : " + localVariablesGetterMap.get(key));
		}
		
		logger.info("********************************LOCAL VARIABLE SETTER**********************************");
		for (String key: localVariablesSetterMap.keySet()) {
		    logger.info("key : " + key);
		    logger.info("value : " + localVariablesSetterMap.get(key));
		}
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		logger.info("**********************************DATA TYPE MAP***************************************");
		
		
	}


	@Override
	public String getJavaStyleGetterCodeOfNatDataTypeForPojo(String key) {
		String result= pojosGetterMap.get(key);
		return result;
		
	}


	@Override
	public String getJavaStyleSetterCodeOfNatDataTypeForPojo(String key) {
		String result= pojosSetterMap.get(key);
		return result;
	}


	@Override
	public String getJavaStyleGetterCodeOfNatDataTypeForLocalVar(String key) {
		String result= localVariablesGetterMap.get(key);
		return result;
	}


	@Override
	public String getJavaStyleSetterCodeOfNatDataTypeForLocalVar(String key) {
		String result= localVariablesSetterMap.get(key);
		return result;
	}
	
	

	

}

