package tr.com.vbt.java.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.JavaElementsFactory;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.util.ConverterConfiguration;

public class RuleUtility {
	
	final static Logger logger = LoggerFactory.getLogger(RuleUtility.class);

	
	JavaElementsFactory factory=new JavaElementsFactory();
	
	LanToLanRule ltolmap = null;
	
	private static RuleUtility instance;
	
	public static RuleUtility getInstance() {
		if (instance==null){
			instance=new RuleUtility();
		}
		return  instance;
	}
	
	private RuleUtility() {
		super();
		if (ConverterConfiguration.sourceLan.equals("COBOL")){
			ltolmap=new CobolToJavaRuleTable();
		}else if (ConverterConfiguration.sourceLan.equals("NATURAL")&&ConverterConfiguration.destLan.equals("JSP")){
			ltolmap=new NaturalToJSPRuleTable();
		}else if (ConverterConfiguration.sourceLan.equals("NATURAL")){
			ltolmap=new NaturalToJavaRuleTable();
		}
	}



	public boolean  operateRule(AbstractCommand sourceElement){
		Rule rule = null;
		try {
			rule = getRule(sourceElement);
		} catch (RuleNotFoundException e) {
			logger.info("Rule Not Found: "+sourceElement+" "+e.getCobolDetailedName());
			sourceElement.setDetailedCobolName("UNDEFINED_COBOL_KEYWORD");
			try {
				rule = getRule(sourceElement);
			} catch (RuleNotFoundException e1) {
				e1.printStackTrace();
				return false;
			}
			
		}
		//logger.info("OPERATE RULE:"+rule.getRuleNum()+"  "+rule.getCobolDetailedName()+"  SourceElement:"+sourceElement.getDetailedCobolName()+"  JavaParentElement:"+  sourceElement.getParentJavaElement());
		Process  process=rule.getProcess();
		ProcessOperator operator;
		if(process==null){
			logger.info("NO PROCESS TO DO:"+sourceElement.getDetailedCobolName());
			return true;
		}
		if(process.equals(Process.Create)){
			operator=new CreateProcessOperator(rule,sourceElement);
			operator.operateRule(rule,sourceElement);
		}else if(process.equals(Process.CreateArrayItem)){
			operator=new CreateArrayItemProcessOperator(rule,sourceElement);
			operator.operateRule(rule,sourceElement);
		}else if(process.equals(Process.SetParameter)){
			operator=new SetParameterProcessOperator(rule,sourceElement);
			operator.operateRule(rule,sourceElement);
		}
		return true;
	}
	
	

	public static AbstractJavaElement getRootDestJavaElement(){
		return JavaClassGeneral.getInstance();
	}

	private Rule getRule(AbstractCommand sourceElement) throws RuleNotFoundException {
		
		List<Rule> matchedRuleList=ltolmap.getMatchedRule(sourceElement.getDetailedCobolName());
		if(matchedRuleList.isEmpty()){
			throw new RuleNotFoundException(sourceElement.getDetailedCobolName());
		}
		for (Rule rule2 : matchedRuleList) {
			return rule2;
		}
		return null;
	}
	
	

	

}
