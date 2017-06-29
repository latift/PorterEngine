package tr.com.vbt.cobol.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.java.util.RuleUtility;
import tr.com.vbt.token.AbstractToken;

public abstract class AbstractCommand implements Parsebla, JavaGenerator, Levelable{
	
	final static Logger logger = LoggerFactory.getLogger(AbstractCommand.class);
	
	protected int levelNumber; // mandatory
	
	protected String dataName; // mandatory
	
	protected HashMap<String, Object> parameters=new HashMap<String, Object>();
	
	protected int elementTokenSize;

	protected List<AbstractCommand> commandListesi;
	
	protected String commandName;
	
	protected String detailedCobolName;
	
	protected AbstractCommand parent;
	
	protected AbstractJavaElement parentJavaElement ;
	
	protected AbstractJavaElement javaElement ;
	
	protected List<AbstractCommand> childElementList=new ArrayList<AbstractCommand>();
	
	protected AbstractCommand currentCommand;
	
	protected int levelOfCode;  //Cocuk ve Torun ayrımı yapmak için.
	
	protected int commandMatchPoint;
	
	protected int satirNumarasi;
	
	protected String sourceCodeSentence="//";	
	
	protected String dataTypeGetterMapString="";
	
	protected String dataTypeSetterMapString="";
	
	protected String sourceCodeFullSatir="";	
	
	private boolean visualCommand;

	private String mode;
	
	
	
	public AbstractCommand(AbstractCommand parent, String commandName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super();
		this.parent = parent;
		this.commandName=commandName;
		this.detailedCobolName=detailedCobolName;
		this.commandListesi=commandListesi;
		
	}
	

	
	public AbstractCommand(String commandName, String detailedCobolName2,
			List<AbstractCommand> commandListesi2) {
		super();
		this.commandName=commandName;
		this.detailedCobolName=detailedCobolName2;
		this.commandListesi=commandListesi2;
	}



	public AbstractCommand(String commandName,
			String detailedCobolName2,int satirNumarasi) {
		super();
		this.commandName=commandName;
		this.detailedCobolName=detailedCobolName2;
		this.satirNumarasi=satirNumarasi;
	}
	
	public AbstractCommand(String commandName,
			String detailedCobolName2) {
		super();
		this.commandName=commandName;
		this.detailedCobolName=detailedCobolName2;
	}



	public AbstractCommand(String commandName2, String detailedCobolName2, boolean isVisualCommand) {
		super();
		this.commandName=commandName2;
		this.detailedCobolName=detailedCobolName2;
		this.visualCommand=isVisualCommand;
	}



	public abstract boolean generateDetailedCobolName();

	
	public void addChild(AbstractCommand e){
			if(this.levelOfCode==0){
				childElementList.add(e);
				e.setParent(this);
			}
			
	}
	
	public void parseChild(){
		for (AbstractCommand child : childElementList) {
			child.parse();
		}
	}

	
	public void printElementAndChildren(){
		System.out.println(commandName);
		for (AbstractCommand child : childElementList) {
			child.printElementAndChildren();
		}
	}
	
	
	private void exportChildren(StringBuilder sb) {
		//StringBuilder sb=new StringBuilder();
		for (AbstractCommand element : childElementList) {
			element.export(sb);
			
		}
	}
	
	public void export(StringBuilder sb) {
		//StringBuilder sb=new StringBuilder();
		sb.append("<");
		sb.append(this.commandName);
		sb.append(exportContents());
		sb.append(">\n");
		exportChildren(sb);
		sb.append("</"+this.commandName+">\n");
		/*for (AbstractCommand child : this.childElementList) {
		 sb.append(child.export());	
		}*/
	}
	
	@Override
	public boolean generateJava() throws RuleNotFoundException {
		boolean result;
		RuleUtility ruleUtility = RuleUtility.getInstance();
		result=ruleUtility.operateRule(this);
		if(result){
			return this.generateJavaForChildren();
		}
		return result;
	}
	
	@Override
	public boolean generateJavaForChildren() throws RuleNotFoundException{
		boolean result=true;
		for (AbstractCommand ae : childElementList) {
			if(!ae.generateJava()){
				logger.info("Dikkat. Hata oluştu: "+ae.toString());
				result=false;
			}
		}
		return result;
	}
	
	public String getDetailedCobolName() {
		return detailedCobolName;
	}


	public void setDetailedCobolName(String detailedCobolName) {
		this.detailedCobolName = detailedCobolName;
	}

	public AbstractCommand getParent() {
		return parent;
	}

	public void setParent(AbstractCommand parent) {
		this.parent = parent;
	}

	public AbstractJavaElement getParentJavaElement() {
		return parentJavaElement;
	}

	public void setParentJavaElement(AbstractJavaElement parentJavaElement) {
		this.parentJavaElement = parentJavaElement;
	}

	public AbstractJavaElement getJavaElement() {
		return javaElement;
	}

	public void setJavaElement(AbstractJavaElement javaElement) {
		this.javaElement = javaElement;
	}



	public HashMap<String, Object> getParameters() {
		return parameters;
	}



	public void setParameters(HashMap<String, Object> parameters) {
		this.parameters = parameters;
	}



	//public abstract int getElementTokenSize();

	public void setElementTokenSize(int elementTokenSize) {
		this.elementTokenSize = elementTokenSize;
	}



	public String getCommandName() {
		return commandName;
	}



	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}



	public List<AbstractCommand> getCommandListesi() {
		return commandListesi;
	}



	public void setCommandListesi(List<AbstractCommand> commandListesi) {
		this.commandListesi = commandListesi;
	}



	public List<AbstractCommand> getChildElementList() {
		return childElementList;
	}



	public void setChildElementList(List<AbstractCommand> childElementList) {
		this.childElementList = childElementList;
	}



	public AbstractCommand getCurrentCommand() {
		return currentCommand;
	}



	public void setCurrentCommand(AbstractCommand currentCommand) {
		this.currentCommand = currentCommand;
	}



	public boolean matches(Object obj) {
		AbstractCommand dest=(AbstractCommand) obj;
		if((this.detailedCobolName==null||dest.detailedCobolName==this.detailedCobolName)&&
				(dest.commandName==this.commandName))
			return true;
		return false;
	}

	public int getLevelOfCode() {
		return levelOfCode;
	}


	public void setLevelOfCode(int levelOfCode) {
		this.levelOfCode = levelOfCode;
	}


	public void increaseCommandsMatchPoint() {
		this.commandMatchPoint++;
	};

	public int getCommandMatchPoint() {
		return commandMatchPoint;
	}



	public void setCommandMatchPoint(int commandMatchPoint) {
		this.commandMatchPoint = commandMatchPoint;
	}



	public void decreaseCommandsMatchPoint() {
		this.commandMatchPoint--;
		
	}

	
	@Override
	public void parse() {
		
	}



	public int getSatirNumarasi() {
		return satirNumarasi;
	}



	public void setSatirNumarasi(int satirNumarasi) {
		this.satirNumarasi = satirNumarasi;
	}



	public String getSourceCodeSentence() {
		return sourceCodeSentence;
	}



	public void setSourceCodeSentence(String sourceCodeSentence) {
		this.sourceCodeSentence = sourceCodeSentence;
	}
	
	


	public void setSourceCodeSentence(String sourceCodeSentence2,
			AbstractToken currentTokenForMatch) {
		try {
			setSourceCodeSentence("Satir:"+this.getSatirNumarasi()+" "+this.getCommandName()+" "+sourceCodeSentence2+" "+currentTokenForMatch.getDeger().toString());
		} catch (Exception e) {
			this.setSourceCodeSentence("//"+e.getMessage());
		}
		
	}



	public String getDataTypeGetterMapString() {
		return dataTypeGetterMapString;
	}



	public void setDataTypeGetterMapString(String dataTypeGetterMapString) {
		this.dataTypeGetterMapString = dataTypeGetterMapString;
	}



	public String getDataTypeSetterMapString() {
		return dataTypeSetterMapString;
	}



	public void setDataTypeSetterMapString(String dataTypeSetterMapString) {
		this.dataTypeSetterMapString = dataTypeSetterMapString;
	}



	public String getSourceCodeFullSatir() {
		return sourceCodeFullSatir;
	}



	public void setSourceCodeFullSatir(String sourceCodeFullSatir) {
		this.sourceCodeFullSatir = sourceCodeFullSatir;
	}



	public boolean isVisualCommand() {
		return visualCommand;
	}



	public void setVisualCommand(boolean visualCommand) {
		this.visualCommand = visualCommand;
	}



	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	public void calculateLevel(Levelable previousLevelable){
		this.levelNumber=previousLevelable.getLevelNumber();
	}



	public boolean isOneOfCommands(String... command) {
		for(int i=0; i<command.length;i++){
			if(this.commandNameEquals(command[i])){
				return true;
			}
		}
		return false;
	}


	
	public boolean commandNameEquals(String commandName) {
		
		if(this.getCommandName().equals(commandName)){
				return true;
		}

		return false;
	}


	public void setMode(String mode) {
		this.mode=mode;
		
	}



	public String getMode() {
		return mode;
	}



	public boolean isStarter() {
		if(this instanceof AbstractMultipleLinesCommand){
			return true;
		}
		return false;
	}
	
	public boolean isEnder() {
		if(this instanceof AbstractEndingCommand){
			return true;
		}
		return false;
	}
	
	public boolean isSimple() {
		if(this instanceof AbstractEndingCommand || this instanceof AbstractMultipleLinesCommand){
			return false;
		}
		return true;
	}
	
	
	
}
