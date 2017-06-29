package tr.com.vbt.java.util;

import java.util.List;

public class Rule {

	private int ruleNum;
	
	private int priority;
	
	private String cobolDetailedName;  //Cobol_Full_Name
	 
	private String oneOfCobolElementParents; //OneOfCobolElementsParents
	
	private String cobolElementClass; //Cobol Element Class
	
	private String cobolParameter; //CobolParameter
	
	private String javaDetailedParentName; //Java Parent
	
	private String javaElement; //JavaElement
	
	private Process process; //Process
	
	private List<String> parameterList;
	
	private String description;
	
	
	
	
	
	public Rule() {
		super();
	}
	public Rule(String cobolDetailedName, String cobolParameter,
			String javaDetailedParentName, String javaElement, Process process,
			List<String> parameterList, String description, int priority, String oneOfCobolElementParents) {
		super();
		this.cobolDetailedName = cobolDetailedName;
		this.cobolParameter = cobolParameter;
		this.javaDetailedParentName = javaDetailedParentName;
		this.javaElement = javaElement;
		this.process = process;
		this.parameterList = parameterList;
		this.description = description;
		this.priority=priority;
		this.oneOfCobolElementParents=oneOfCobolElementParents;
		
	}
	public String getCobolDetailedName() {
		return cobolDetailedName;
	}
	public void setCobolDetailedName(String cobolDetailedName) {
		this.cobolDetailedName = cobolDetailedName;
	}
	public String getJavaDetailedParentName() {
		return javaDetailedParentName;
	}
	public void setJavaDetailedParentName(String javaDetailedParentName) {
		this.javaDetailedParentName = javaDetailedParentName;
	}
	public String getJavaElement() {
		return javaElement;
	}
	public void setJavaElement(String javaElement) {
		this.javaElement = javaElement;
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public List<String> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getCobolParameter() {
		return cobolParameter;
	}
	public void setCobolParameter(String cobolParameter) {
		this.cobolParameter = cobolParameter;
	}
	public int getRuleNum() {
		return ruleNum;
	}
	public void setRuleNum(int ruleNum) {
		this.ruleNum = ruleNum;
	}
	
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getOneOfCobolElementParents() {
		return oneOfCobolElementParents;
	}
	public void setOneOfCobolElementParents(String oneOfCobolElementParents) {
		this.oneOfCobolElementParents = oneOfCobolElementParents;
	}
	
	@Override
	public String toString() {
		return "RuleNum:"+this.ruleNum+" Priority:"+this.priority+" CobolDetailedName:"+this.cobolDetailedName;
	}
	public String getCobolElementClass() {
		return cobolElementClass;
	}
	public void setCobolElementClass(String cobolElementClass) {
		this.cobolElementClass = cobolElementClass;
	}
	
	
	

	
	
}
