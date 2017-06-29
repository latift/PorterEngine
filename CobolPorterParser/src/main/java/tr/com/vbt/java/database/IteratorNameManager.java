package tr.com.vbt.java.database;

import java.util.HashSet;

public class IteratorNameManager {

	private int  iteraterCounter=0;
	
	private String iteratorName;
	
	private static HashSet<String> itNameSet=new HashSet<String>();
	
	////itName="it"+pojoName; 
	public String createIteratorName(String pojoName) {
	
		do{
			if(iteraterCounter==0){
				iteratorName="it"+pojoName; 
			}else{
				iteratorName="it"+pojoName+iteraterCounter; 
			}
			iteraterCounter++;	
		}while(itNameSet.contains(iteratorName));
		
		itNameSet.add(iteratorName);
		
		return iteratorName;
	}
	
	public static void resetIteratorNameManager(){
		itNameSet=new HashSet<>();
	}

}
