package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;

/**
 *    0216 1 MAP2           --> ElementProgramGrupNatural                                                                                                               
   0218   2 MUSNO1               (N8) 
   0220   2 ADSOY1               (A41)
   
   -->  Map2{
   			int MusNo1;
   			String AdSoy1;
   		}
   		
   		--  class MAP_DIZISI{
   		
   		};
   		MAP_DIZISI MAP_DIZISI=new MAP_DIZISI();
   */
public class JavaProgramGrup extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaProgramGrup.class);
	
	
	protected long levelNumber; // mandatory
	
	protected String grupName; // mandatory
	
	private int arrayLength; // 500 Parantez içindeki ifade
	

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
	
		levelNumber = (int)((long) this.parameters.get("levelNumber"));
			
		grupName = (String) this.parameters.get("grupName");
		
		grupName=grupName.replaceAll("-", "_");
		
		if(this.parameters.get("arrayLength")!=null){
			arrayLength =  (int)((long) this.parameters.get("arrayLength"));
		}
		
		
		try{
			JavaClassElement.javaCodeBuffer.append("public class "+grupName);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			writeChildrenJavaToStream();
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			
			//MAP_DIZISI MAP_DIZISI=new MAP_DIZISI();
			//MAP_DIZISI MAP_DIZISI[]=new MAP_DIZISI[100];
			JavaClassElement.javaCodeBuffer.append("public "+grupName+" "+ grupName);
			if(arrayLength==0){
				JavaClassElement.javaCodeBuffer.append("=new "+ grupName+"();");
			}
			else{
				JavaClassElement.javaCodeBuffer.append("[]");
				JavaClassElement.javaCodeBuffer.append("=new "+ grupName);
				JavaClassElement.javaCodeBuffer.append("["+arrayLength+"];");
			}
			
			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}


	public long getLevelNumber() {
		return levelNumber;
	}


	public void setLevelNumber(long levelNumber) {
		this.levelNumber = levelNumber;
	}


	public String getGrupName() {
		return grupName;
	}


	public void setGrupName(String grupName) {
		this.grupName = grupName;
	}


	public int getArrayLength() {
		return arrayLength;
	}


	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}

	

}
