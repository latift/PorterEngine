package tr.com.vbt.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tr.com.vbt.util.ConverterConfiguration;



public final class NaturalToJavaRuleTable implements LanToLanRule{
	
	
	public static List<Rule> ruleList=new ArrayList<Rule>();
	
	public NaturalToJavaRuleTable() {
		super();
		Rule r = null;
		
		//Get the workbook instance for XLS file 
		XSSFWorkbook workbook = null;
		try {
			//FileInputStream file = new FileInputStream(new File("D:\\CobolPorterWorkspace2\\CobolPorter\\NaturalFiles\\NaturalToJavaRule.xlsx"));
			FileInputStream file = new FileInputStream(new File(ConverterConfiguration.ruleTable()));
			
			workbook = new XSSFWorkbook (file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Double rownumDouble, priorityDouble;
		
		for (Row row : sheet) {
				r = new Rule();
				
				//Sheet Başlık Kısmını Atla
				if(row.getRowNum()==0){
					continue;
				}
				
				for (Cell cell : row)
				{
			         switch(cell.getColumnIndex()){
			         	case 0:  //RuleNum
			         		rownumDouble=cell.getNumericCellValue();
			         		r.setRuleNum(rownumDouble.intValue());
	                 	    break;
			         /*	case 1:  //Priority
			         		priorityDouble=cell.getNumericCellValue();
			         		r.setPriority(priorityDouble.intValue());
	                 	    break;*/
	                 	case 1: //Cobol_Full_Name //B
	                 		r.setCobolDetailedName(cell.getStringCellValue());
		                    break;
	                 	/*case 3: //OneOfCobolElementsParents 
	                 		r.setOneOfCobolElementParents(cell.getStringCellValue());
		                    break;*/
	                 	case 2: //Cobol Element Class  //C
	                 		r.setCobolElementClass(cell.getStringCellValue());
		                    break;
	                 	case 3: //CobolParameter
	                 		r.setCobolParameter(cell.getStringCellValue());
		                    break;
	                 	case 4: //Java Parent
	                 		r.setJavaDetailedParentName(cell.getStringCellValue());
		                    break;
	                 	case 5: //JavaElement
	                 		r.setJavaElement(cell.getStringCellValue());
		                    break;
	                 	case 6: //Process
	                 		if(cell.getStringCellValue().equals("Create")){
	                 			r.setProcess(Process.Create);
	                 		}else if(cell.getStringCellValue().equals("SetParameter")){
	                 			r.setProcess(Process.SetParameter);
	                 		}else if(cell.getStringCellValue().equals("CreateArrayItem")){
	                 			r.setProcess(Process.CreateArrayItem);
	                 		}
		                    break;
	                 	case 9: //Parameter
	                 		List<String> paramList= Arrays.asList(cell.getStringCellValue().split(","));
  	                 		r.setParameterList(paramList);
		                    break;
	                 	case 10: //Açıklama
	                 		r.setDescription(cell.getStringCellValue());
		                    break;
	                 }
	            
				}
				ruleList.add(r);
        	
		}
				
				
	}

	public List<Rule> getMatchedRule(String detailedCobolName) {
		List<Rule> matchedRuleList=new ArrayList<Rule>();
			
		for (Rule rule : ruleList) {
			if(rule.getCobolDetailedName().equals(detailedCobolName)){
				matchedRuleList.add(rule);
			}
		}
		return matchedRuleList;
	}

	
	

}

