package tr.com.vbt.ddm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.rpc.processor.config.Configuration;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;

public class DDMList {
	
	final static Logger logger = LoggerFactory.getLogger(DDMList.class);

	Map<String, DDM> dDMMap = new HashedMap();
	
	private static DDMList instance;
	
	private DDM firstLevelDDM;

	private DDMList() {
		super();
		
		//final File folder = new File("C:\\CobolPorterWorkspace2\\CobolPorter\\CobolPorter\\THY\\TPS\\SeperatedPrograms\\DDM");
		final File folder = new File(ConversionLogModel.getInstance().getFullDDMFolder());
		if(ConverterConfiguration.pojosAreDefinedInCode){
			listFilesForFolder(folder);
			writeDDMList();
			
		}
		
	}	
		
	private void writeDDMList() {
			try {
				StringBuilder sb=new StringBuilder();

				for (Map.Entry<String, DDM> entry : dDMMap.entrySet())
				{
					//			DDM d1 = new DDM(TableName,T,L,DB,Name);
					if(entry.getKey().length()>40){
						sb.append(entry.getKey() + "\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>35){
						sb.append(entry.getKey() + "\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>30){
						sb.append(entry.getKey() + "\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>25){
						sb.append(entry.getKey() + "\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>20){
						sb.append(entry.getKey() + "\t\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>15){
						sb.append(entry.getKey() + "\t\t\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>10){
						sb.append(entry.getKey() + "\t\t\t\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>5){
						sb.append(entry.getKey() + "\t\t\t\t\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}else if(entry.getKey().length()>0){
						sb.append(entry.getKey() + "\t\t\t\t\t\t\t\t\t:" + entry.getValue().getTableName()+"\t\t"+ entry.getValue().getT()+"\t" +entry.getValue().getL()+"\t" + entry.getValue().getDB()+"\t" + entry.getValue().getName()+"\n");
					}
				}

				WriteToFile.writeToFileByCreatingFileIfNotExist(sb,ConversionLogModel.getInstance().getFullLoadedDDMListFile());
			} catch (IOException e) {
				logger.debug(e.getMessage(),e);
			}
		
	}

		public static DDMList getInstance() {
			if (instance==null){
				instance=new DDMList();
			}
			return  instance;
		}

	
	public void listFilesForFolder(final File folder) {
			
			for (final File fileEntry : folder.listFiles()) {
			    if (fileEntry.isDirectory()) {
			       //listFilesForFolder(fileEntry);
			    } else {
			        System.out.println(fileEntry.getName());
			        loadDDMFromDDMFiles(folder.getAbsolutePath(), fileEntry.getName());
			    }
			}
	}

	private void loadDDMFromDDMFiles(String fullDDMFolderPath, String fileName) {

		if(!ConverterConfiguration.pojosAreDefinedInCode){//MB için 
			
			return;
		}
		BufferedReader br = null;
		FileReader fr = null;
		
		int lineNumber=0;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fullDDMFolderPath+"\\"+fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				if(lineNumber>1){
					logger.debug("fileName:"+fileName+"  sCurrentLine:"+sCurrentLine);
					addLineToDDM(fileName,sCurrentLine);
				}
				lineNumber++;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * 0  T L  DB  Name                             F Leng  S D  Remark
	 *      1  A4  TAX-AMOUNT                       P  9.2  N
		    1  A5  TAX-INOUT                        A    1  N D
		    1  A6  TAX-TKT-CODE                     A    2  N
		    1  A8  TAX-DOM-INT                      A    1  N
		  M 1  A9  TAX-DETAIL                       A   40  N
		    1  AA  TAX-EFF-DATE                     N  6.0  N
		    1  AB  TAX-EXP-DATE                     N  6.0  N
		  P 1  AC  TAX-EXEMPT-PER
		    2  AD  TAX-EXEMPT-CODE                  A    6  N
		  M 2  AE  TAX-EXEMPT-DEFINITION            A   60  N
	 * @param sCurrentLine
	 */
	private void addLineToDDM(String fileName, String sCurrentLine) {
		
		try {
			String[] lineItems=sCurrentLine.trim().split(" ");
			
			List<String> lineItemsList=new ArrayList<>();
			
			String TableName=fileName;
			String T = ""; 
			String L;
			String DB;
			String Name;
			String F;
			String Leng;
			String S;
			String D;
			
			for(int i=0; i< lineItems.length;i++){
				if(!lineItems[i].trim().isEmpty()){
					lineItemsList.add(lineItems[i]);
				}
			}
			
			logger.debug("File:"+fileName+" Line:"+sCurrentLine);
			
			if(lineItemsList.get(0).startsWith("A")|| lineItemsList.get(0).startsWith("M")||lineItemsList.get(0).startsWith("P")||lineItemsList.get(0).startsWith("T")||lineItemsList.get(0).startsWith("G")){
				
				T=lineItemsList.get(0);
				L=lineItemsList.get(1);
				DB=lineItemsList.get(2);
				Name=lineItemsList.get(3);
				
			}else{
				L=lineItemsList.get(0);
				DB=lineItemsList.get(1);
				Name=lineItemsList.get(2);
				
			}
			
			DDM d1 = new DDM(TableName,T,L,DB,Name);
			if(L.equals("1")){
				firstLevelDDM=d1;
			}else{
				d1.setFirstLevelDDM(firstLevelDDM);
			}
			
			String key=d1.getTableName().substring(0,d1.getTableName().length()-3).replaceAll("-", "_") + d1.getName().replaceAll("-", "_");
			logger.debug("Put To DDM List:"+key);
			dDMMap.put(key, d1);
		} catch (Exception e) {
			//DDM dosyalarında hatalı satirlar olabilir. Yada yukardaki formata uymayan satirlar. Bunlari ihmal edebilirsin.
		}
		
	}



	public Map<String, DDM> getdDMMap() {
		return dDMMap;
	}

	public void setdDMMap(Map<String, DDM> dDMMap) {
		this.dDMMap = dDMMap;
	}

	public DDM getDDM(AbstractToken copyTo) {
		
		DDM ddm;
		
		String key="";
		if(copyTo.getTypeNameOfView()!=null&& !copyTo.getTypeNameOfView().trim().isEmpty()){
			key=copyTo.getTypeNameOfView().toString()+"."+copyTo.getColumnNameToken().getDeger().toString();
			
		}else if(copyTo.getSynoynmsRealTableName()!=null&& !copyTo.getSynoynmsRealTableName().trim().isEmpty()){
			key=copyTo.getSynoynmsRealTableName().toString()+"."+copyTo.getColumnNameToken().getDeger().toString();
		
		}else{
			key=copyTo.getDeger().toString()+"."+copyTo.getColumnNameToken().getDeger().toString();
		}
		ddm=dDMMap.get(key);
		if(ddm==null){
			ddm=registerUndefinedDDM(key, copyTo);
			
			return ddm;
		}
		if(ddm.getFirstLevelDDM()!=null &&ddm.getFirstLevelDDM().getT().equals("G")){ //Grupsa
			ddm.setL(ddm.getFirstLevelDDM().getL());
		}
		return ddm;
		
		
	}
	
	public DDM getDDMByKey(String key,AbstractCommand command ) {
		
		DDM ddm;
		
		ddm=dDMMap.get(key);
		
		if(ddm==null){
			
			ddm=registerUndefinedDDM(key, command.getSatirNumarasi());
			
			return ddm;
		}
		
		return ddm;
		
		
	}
	
public DDM getDDMByKey(String key,AbstractToken curToken ) {
		
		DDM ddm;
		
		ddm=dDMMap.get(key);
		
		if(ddm==null){
			
			ddm=registerUndefinedDDM(key, curToken.getSatirNumarasi());
			
			return ddm;
		}
		
		return ddm;
		
		
	}

	public DDM getFirstLevelDDM(AbstractToken copyTo) {
		
		DDM ddm;
		
		String key="";
		if(copyTo.getSynoynmsRealTableName()!=null&& !copyTo.getSynoynmsRealTableName().trim().isEmpty()){
			key=copyTo.getSynoynmsRealTableName().toString()+"."+copyTo.getColumnNameToken().getDeger().toString();
		}else{
			key=copyTo.getDeger().toString()+"."+copyTo.getColumnNameToken().getDeger().toString();
		}
		ddm=dDMMap.get(key);
		if(ddm==null){
			
			ddm=registerUndefinedDDM(key,copyTo);
			
			return ddm;
		}
		if(ddm.getL().equals("1")){
			return ddm;
		}
		return ddm.getFirstLevelDDM();
	}
	
	
	private static DDM registerUndefinedDDM(String key, AbstractToken token) {
		
		return registerUndefinedDDM(key,token.getSatirNumarasi());

	}
	
	
	private static DDM registerUndefinedDDM(String key, int satirNumarasi) {
		
		String module=ConversionLogModel.getInstance().getModule();
		
		String programName=ConversionLogModel.getInstance().getFileName();
		
		String value=module+"\t\t"+programName+"\t\t"+satirNumarasi;
		
		ConversionLogModel.getInstance().getUndefinedDDMList().put(key, value);
		
		DDM ddm=new DDM("XXXXX", "", "1", "XXXXX", "XXXXX");
		
		return ddm;
	}
	public static void writeUndefinedDDMList(){
		
		 	Map<String, String> undefinedDDMList=ConversionLogModel.getInstance().getUndefinedDDMList();
			try {
				StringBuilder sb=new StringBuilder();

				for (Map.Entry<String, String> entry : undefinedDDMList.entrySet())
				{
					//			DDM d1 = new DDM(TableName,T,L,DB,Name);
					if(entry.getKey().length()>40){
						sb.append(entry.getKey() + "\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>35){
						sb.append(entry.getKey() + "\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>30){
						sb.append(entry.getKey() + "\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>25){
						sb.append(entry.getKey() + "\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>20){
						sb.append(entry.getKey() + "\t\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>15){
						sb.append(entry.getKey() + "\t\t\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>10){
						sb.append(entry.getKey() + "\t\t\t\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>5){
						sb.append(entry.getKey() + "\t\t\t\t\t\t\t:" + entry.getValue()+"\n");
					}else if(entry.getKey().length()>0){
						sb.append(entry.getKey() + "\t\t\t\t\t\t\t\t:" + entry.getValue()+"\n");
					}
				}
				sb.append(JavaConstants.NEW_LINE);
				
				WriteToFile.writeHeaderToFile(createHeader(), ConversionLogModel.getInstance().getUndefinedDDMListFile());
			
				WriteToFile.appendToFile(sb.toString(),ConversionLogModel.getInstance().getUndefinedDDMListFile());
				
			} catch (IOException e) {
				logger.debug(e.getMessage(),e);
			}
			
			ConversionLogModel.getInstance().resetUndefinedList();
	}

	private static StringBuffer createHeader() {
		StringBuffer header=new StringBuffer();
		header.append("DDM								Modül			Programı		SatırNumarası");
		header.append(JavaConstants.NEW_LINE);
		header.append("--------------------------------------------------------------------------");
		header.append(JavaConstants.NEW_LINE);
		return header;
	}

	

}
