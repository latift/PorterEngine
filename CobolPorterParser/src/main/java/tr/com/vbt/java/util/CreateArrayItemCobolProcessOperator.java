package tr.com.vbt.java.util;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaCobolClassElement;
import tr.com.vbt.java.general.JavaFunctionElement;
import tr.com.vbt.java.general.JavaFunctionMainElement;
import tr.com.vbt.java.general.JavaGeneralVariableElement;
import tr.com.vbt.java.general.JavaInnerClassElement;
import tr.com.vbt.java.general.JavaNaturalClassElement;
import tr.com.vbt.java.jsp.JSPDisplayFormElement;
import tr.com.vbt.java.jsp.JSPFormElement;
import tr.com.vbt.java.jsp.JSPGeneralVariableElement;
import tr.com.vbt.java.jsp.general.JSPGeneral;
import tr.com.vbt.util.ConverterConfiguration;

public class CreateArrayItemCobolProcessOperator extends ProcessOperator {

	final static Logger logger = Logger.getLogger(CreateArrayItemCobolProcessOperator.class);
	
	public CreateArrayItemCobolProcessOperator(Rule rule, AbstractCommand sourceElement) {
		super(rule);
		//findParent();
	}

	

	private void findParent() {
		
		String cobolParameter;
		
		if(rule.getJavaDetailedParentName()==null){
			return;
		}
		
		String[] detailNameArray=rule.getJavaDetailedParentName().split(".");
		
		List<String> detailNameList=Arrays.asList(detailNameArray);
		
		AbstractJava abj=RuleUtility.getRootDestJavaElement();
		
		for (String detailName : detailNameList) {
		
			if(detailName.contains("[]")){
			
				if(detailName.equals("children[]")){
				
					cobolParameter=this.rule.getCobolParameter();
					
					List<AbstractJava> children=((JavaClassElement)abj).getChildren();
					
					abj=abj.getChildWithName(cobolParameter);
					
					this.parentJava=abj;
					
				}
		
			}else{
			
				abj=abj.getChildWithName(detailName);
			}
		}
		
		this.parentJava=abj;
		
		logger.info("PARENT:"+this.parentJava);
	}
	

	




	@Override
	public AbstractJava operateRule(Rule rule, AbstractCommand sourceElement) {
	
		String javaElement=rule.getJavaElement();
		
		if(javaElement==null){
			throw new RuntimeException("CreateArrayItemProcessOperator Rule Java Sınıfı tanımlanmanmamış: Rule No:"+rule.getRuleNum());
		}
		
		if(javaElement.equals("JavaFunctionElement")){
			elementForCreate=new JavaFunctionElement();
		}else if(javaElement.equals("JavaFunctionMainElement")){
			elementForCreate=new JavaFunctionMainElement();
		}else if(javaElement.equals("JavaGeneralVariableElement")){
			elementForCreate=new JavaGeneralVariableElement();
		}else if(javaElement.equals("JavaInnerClassElement")){
			elementForCreate=new JavaInnerClassElement();
		}else if(javaElement.equals("JavaClassElement")){
			elementForCreate=new JavaClassElement();
		}else if(javaElement.equals("JavaNaturalClassElement")){
			elementForCreate=new JavaNaturalClassElement();
		}else if(javaElement.equals("JavaCobolClassElement")){
			elementForCreate=new JavaCobolClassElement();
		}else if(javaElement.equals("JSPGeneralVariableElement")){
			elementForCreate=new JSPGeneralVariableElement();
		}else if(javaElement.equals("JSPFormElement")){
			elementForCreate=new JSPFormElement();
		}else if(javaElement.equals("JSPDisplayFormElement")){
			elementForCreate=new JSPDisplayFormElement();
		}
		
		if(elementForCreate==null){
			throw new RuntimeException("CreateArrayItemProcessOperator Sınıfına Element tanımı eklenmeli.. Element:"+javaElement);
		}
		logger.info("test3");
		
		setParentJava(rule,sourceElement);
		
		if (sourceElement.getParentJavaElement()== null) {
			throw new RuntimeException("CreateArrayItemProcessOperator Source un ParentJavası null. SourceElement:"+sourceElement+" Rule:"+rule.getRuleNum());
		}
	
		sourceElement.getParentJavaElement().getChildren().add(elementForCreate);
		
		sourceElement.setJavaElement(elementForCreate);
		
		try {
			elementForCreate.setParameters(sourceElement.getParameters());
			elementForCreate.setSourceCode(sourceElement);
		} catch (Exception e) {
			logger.info(javaElement +" should be defined at CreateProcessOperator");
			e.printStackTrace();
			throw e;
		}
		if(sourceElement.getDetailedCobolName().equals("")){
			logger.debug("UNDEFINED_COBOL_KEYWORD");
		}
		logger.info("OPERATE RULE:"+rule.getRuleNum()+"  "+rule.getCobolDetailedName()+"  SourceElement:"+sourceElement.getDetailedCobolName()+"  JavaParentElement:"+  sourceElement.getParentJavaElement()+ " Added:"+sourceElement.getJavaElement());
		
		return elementForCreate;
		
	}



	private void setParentJava(Rule rule, AbstractCommand sourceElement) {
		AbstractJava aje;
		String[] detailedName;
		if(rule.getJavaDetailedParentName()!=null&&rule.getJavaDetailedParentName().length()>0){
			if(ConverterConfiguration.destLan.equals("JSP")){
				aje=JSPGeneral.getInstance();
			}else{
				aje=JavaClassGeneral.getInstance();
			}
			detailedName=rule.getJavaDetailedParentName().split("\\.");
			for (String childName : detailedName) {
				if(childName.equals("JavaClassGeneral")||childName.equals("JSPGeneral")){
					continue;
				}
				aje=aje.getChildWithName(childName);
			}
			sourceElement.setParentJavaElement(aje);
		}else{
			sourceElement.setParentJavaElement(sourceElement.getParent().getJavaElement());
		}
	}



		
}
