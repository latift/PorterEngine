package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

//FIND IDGIDBS-TGECICI WITH REFERANS=REFNUM AND TAMAM_KOD=0 --> schema ismi eklenmeyecek.
//FIND LIMAN WITH L-LIMAN-KODU EQ #P-LIMAN-KODU --> schema ismi eklenecek
//FIND HAN-FATURA WITH FT-BITIS-DONEM-KLIM-FIRM-FATURA EQ  --> schema ismi eklenecek
public class SchemaNameManager {

	final static Logger logger = LoggerFactory.getLogger(SchemaNameManager.class);

	private static Map<String, String> schemaNameMap = new HashMap<>();
	
	private static Map<String, String> tableNames = new HashMap<>();

	public static void addSchemaName(List<AbstractToken> tokenListesi) {

		String realTableName, synonymName;
		loadSchemaMap();
		String fullName, schemaName = null;
		String[] seperatedNames;

		AbstractToken schemaToken = null;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {
			schemaToken = tokenListesi.get(i);
	
			if(!schemaToken.getTip().equals(TokenTipi.Kelime)|| schemaToken.getDeger().toString().length()<2){  
				continue;
			}
			
			logger.debug("Token:"+schemaToken.getDeger());
			fullName = schemaToken.getDeger().toString();
			
			if(!fullName.contains("_")){
				continue;
			}
		
			// fullName = IDGIDBS-TGECICI
			seperatedNames = fullName.split("_");

			try {
				//if (Configuration.isSchemaName(seperatedNames[0])) { // Schema Varsa

				//} else 
				if (isTable(fullName)) {//// Schema Yoksa ve TAble ise
					schemaName = schemaNameMap.get(fullName);
					if(schemaName==null || schemaName.length()==0){
						schemaName="TPS";
					}
					schemaToken.setDeger(schemaName + "_" + schemaToken.getDeger());
					logger.debug("SchemaManager:"+schemaName+"  is added");
					logger.debug("");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private static void loadSchemaMap() {
		
		schemaNameMap.put("KET_AIRLINE", "TPS");
		schemaNameMap.put("KET_DOMESTIC", "TPS");
		schemaNameMap.put("KET_FDB", "TPS");
		schemaNameMap.put("KET_FRT", "TPS");
		schemaNameMap.put("KET_MILES", "TPS");
		schemaNameMap.put("KET_MULTIFILE", "TPS");
		schemaNameMap.put("KET_NOTE", "TPS");
		schemaNameMap.put("KET_NOTE_ATPCO", "TPS");
		schemaNameMap.put("KET_NOTE_CODE", "TPS");
		schemaNameMap.put("KET_TAX", "TPS");
		schemaNameMap.put("KET_NOTE_TAX_EXC", "TPS");
		
		schemaNameMap.put("ANLASMA", "PER");
		schemaNameMap.put("DEVF01", "PER");
		schemaNameMap.put("DFRF01", "PER");
		schemaNameMap.put("ENFF01", "PER");
		schemaNameMap.put("ERP-HAR", "PER");
		schemaNameMap.put("ERP-ISGRB", "PER");
		schemaNameMap.put("ERP-OBJECT", "PER");
		schemaNameMap.put("ERP-UNITE", "PER");
		schemaNameMap.put("IZINFILE", "PER");
		schemaNameMap.put("KET-BSR", "PER");
		schemaNameMap.put("KREF03", "PER");
		schemaNameMap.put("KSSF01", "PER");
		schemaNameMap.put("LOGFILE", "PER");
		schemaNameMap.put("MUVF05S1", "PER");
		schemaNameMap.put("PC-CABINET", "PER");
		schemaNameMap.put("PERF15", "PER");
		schemaNameMap.put("PERF30", "PER");
		schemaNameMap.put("PERF31", "PER");
		schemaNameMap.put("PERF34", "PER");
		schemaNameMap.put("PERF43", "PER");
		schemaNameMap.put("PERF50", "PER");
		schemaNameMap.put("PERF61", "PER");
		schemaNameMap.put("PERF90", "PER");
		schemaNameMap.put("PILF01", "PER");
		schemaNameMap.put("STAF01", "PER");
		schemaNameMap.put("UCRF01", "PER");
		schemaNameMap.put("UCRF02", "PER");
		schemaNameMap.put("UCRF15", "PER");
		schemaNameMap.put("UCRF20", "PER");
		schemaNameMap.put("UCRSTJ03", "PER");
		schemaNameMap.put("UCUF01", "PER");
		
		schemaNameMap.put("CAT-SEVK","IKR");
		schemaNameMap.put("GDR-DEBITCREDIT","IKR");
		schemaNameMap.put("GDR-ROL-BILGISI","IKR");
		schemaNameMap.put("GZT-IRSALIYE","IKR");
		schemaNameMap.put("IKR-BOARDINGCARD","IKR");
		schemaNameMap.put("IKR-FRM-MALZEME","IKR");
		schemaNameMap.put("IKR-GAZETE-UCUS","IKR");
		schemaNameMap.put("IKR-MENU","IKR");
		schemaNameMap.put("IKR-YEMEK","IKR");
		schemaNameMap.put("IKR-YUKPLN","IKR");
		schemaNameMap.put("TAR-SEFOP","IKR");
		schemaNameMap.put("TAR-SEKTOR","IKR");
		schemaNameMap.put("THY-CAT","IKR");
		schemaNameMap.put("UHM-ACILIS2","IKR");
		
		schemaNameMap.put("CAT_SEVK","IKR");
		schemaNameMap.put("GDR_DEBITCREDIT","IKR");
		schemaNameMap.put("GDR_ROL_BILGISI","IKR");
		schemaNameMap.put("GZT_IRSALIYE","IKR");
		schemaNameMap.put("IKR_BOARDINGCARD","IKR");
		schemaNameMap.put("IKR_FRM_MALZEME","IKR");
		schemaNameMap.put("IKR_GAZETE_UCUS","IKR");
		schemaNameMap.put("IKR_MENU","IKR");
		schemaNameMap.put("IKR_YEMEK","IKR");
		schemaNameMap.put("IKR_YUKPLN","IKR");
		schemaNameMap.put("TAR_SEFOP","IKR");
		schemaNameMap.put("TAR_SEKTOR","IKR");
		schemaNameMap.put("THY_CAT","IKR");
		schemaNameMap.put("UHM_ACILIS2","IKR");
		
		
		schemaNameMap.put("UCRF42","UCL");
		schemaNameMap.put("UCRF50","UCL");
		schemaNameMap.put("UCRF53","UCL");
		schemaNameMap.put("UCR-ZOR93A","UCL");
		schemaNameMap.put("UCR_ZOR93A","UCL");
		schemaNameMap.put("ZORF01","UCL");
		schemaNameMap.put("HAN-MUHASEBE","UCL");
		schemaNameMap.put("HAN_MUHASEBE","UCL");
		schemaNameMap.put("HRCH01","UCL");
		schemaNameMap.put("KONF99","UCL");
		schemaNameMap.put("KREF06","UCL");
		schemaNameMap.put("MUVF01","UCL");
		schemaNameMap.put("MUVF11","UCL");
		schemaNameMap.put("MUVF20","UCL");
		schemaNameMap.put("PASF00","UCL");
		schemaNameMap.put("UCRF07","UCL");
		schemaNameMap.put("UCRF10","UCL");
		schemaNameMap.put("UCRF30","UCL");
		schemaNameMap.put("UCRF40","UCL");
		

	}
	

	private static boolean isTable(String isTable) {
		return schemaNameMap.containsKey(isTable);
	}

}
