package tr.com.vbt.java.general;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.ConverterConfiguration;

public class JavaNaturalClassElement extends  AbstractJavaElement{
	
	
	String classSecurity="public";
	String programId, interfaceName, implementsClassName;
	ConversionLogModel logModel;
	
	private List<JavaFunctionElement> jFunctions;
	
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		
		copyAtTopOfPageToClassLevel();
		
		logModel=ConversionLogModel.getInstance();
		
		programId=(String) this.parameters.get("PROGRAM_ID");
		
		if(programId==null){
			interfaceName=ConverterConfiguration.className;
		}
		
		
	
		implementsClassName=interfaceName+"Impl";
		

		if(ConversionLogModel.getInstance().isMapTester()){
			interfaceName=interfaceName+"Tester";
		}else if(ConversionLogModel.getInstance().isMap()){
			
		}
		
		
		
		String module=ConversionLogModel.getInstance().getModule().replaceAll("/SeperatedPrograms", "").toLowerCase();
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append("import java.util.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import javax.annotation.*;"+JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append("import java.math.BigDecimal;");
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append("import org.springframework.context.annotation.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import org.springframework.stereotype.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import org.springframework.beans.factory.annotation.*;"+JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append("import org.slf4j.Logger;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import org.slf4j.LoggerFactory;"+JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append("import tr.com.vbt.natural.core.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import tr.com.vbt.natural.html.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import tr.com.vbt.java.utils.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import tr.com.vbt.lexer.*;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("import tr.com.vbt.util.FrameworkConfiguration;"+JavaConstants.NEW_LINE);
		
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		if(logModel.getCustomer().equals("MB") && logModel.isMapTester()){
			AbstractJavaElement.javaCodeBuffer.append("import tr.com.mb.menu1.web.TESTNATPROGImpl;"+JavaConstants.NEW_LINE);
		}
		
		if(logModel.getCustomer().equals("MB")){
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.generated.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.hibernate.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.hibernate.generated.*;"+JavaConstants.NEW_LINE);
			String schemaName;
		/*	for(int i=0; i<ConverterConfiguration.getSchemaList().size();i++){
				schemaName=ConverterConfiguration.getSchemaList().get(i);
				AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo."+schemaName.toLowerCase()+".*;"+JavaConstants.NEW_LINE);
			}*/
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.idgidbs.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.common.*;"+JavaConstants.NEW_LINE);
				
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.global.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.local.*;"+JavaConstants.NEW_LINE);
			
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".web.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".web.interfaces.*;"+JavaConstants.NEW_LINE);
			
			if(logModel.isMapOrMapTester()){
				AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"."+module+".web.*;"+JavaConstants.NEW_LINE);
			}

		
		}else{
		
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.global.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.local.*;"+JavaConstants.NEW_LINE);
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"."+module+".web.interfaces.*;"+JavaConstants.NEW_LINE);
	
			if(logModel.isSubProgram()){
				AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"."+module+".web.*;"+JavaConstants.NEW_LINE);
			}
		}
	
		
		
		
		

		if(ConversionLogModel.getInstance().isProgram()){
//			@Component
			AbstractJavaElement.javaCodeBuffer.append("@Component"+JavaConstants.NEW_LINE);
//			@Service(value = "TPSFTEP1")
			AbstractJavaElement.javaCodeBuffer.append("@Service(value = \""+interfaceName+"\")"+JavaConstants.NEW_LINE);
//			@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
			AbstractJavaElement.javaCodeBuffer.append("@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = \"session\")"+JavaConstants.NEW_LINE);
		}else{
			//@Component(value = "IDGNGECI")
			//@Scope("prototype")
			AbstractJavaElement.javaCodeBuffer.append("@Component(value = \""+interfaceName+"\")"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("@Scope(\"prototype\")"+JavaConstants.NEW_LINE);
		}
		
		if(logModel.isMapOrMapTester()){
			AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " " +JavaConstants.CLASS+ " "+ interfaceName+  " extends AbstractNaturalMap "+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
		}else{
			if(logModel.getCustomer().equals("MB")){
				AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " " +JavaConstants.CLASS+ " "+ implementsClassName+  " extends AbstractNaturalIsciDovizleriProgram implements "+ interfaceName+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
			}else{
				AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " " +JavaConstants.CLASS+ " "+ implementsClassName+  " extends AbstractNatural"+module.toUpperCase()+"Program implements "+ interfaceName+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
			}
		}
		
		addLogger();
		
		addConstructor();
		
		this.writeChildrenJavaToStream();
		
		//addSetParametersMethod();
		//addInitParamListMethod(); Gerek kalmadı. Reflection ve annotation ile çözüldü
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET+"// End Of Program "+implementsClassName);
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	private void addLogger() {

		//final static Logger logger = LoggerFactory.getLogger(IDGP0011Impl.class);
		if(ConversionLogModel.getInstance().isMap()){
			AbstractJavaElement.javaCodeBuffer.append("final static Logger logger = LoggerFactory.getLogger("+interfaceName+".class);"+JavaConstants.NEW_LINE);
		}else if(ConversionLogModel.getInstance().isMapTester()){
			AbstractJavaElement.javaCodeBuffer.append("final static Logger logger = LoggerFactory.getLogger("+interfaceName+".class);"+JavaConstants.NEW_LINE);
		}else{
			AbstractJavaElement.javaCodeBuffer.append("final static Logger logger = LoggerFactory.getLogger("+implementsClassName+".class);"+JavaConstants.NEW_LINE);
		}
		
	}

	private void addSetParametersMethod() {
		/*
		 * @Override
			protected void setParametersBindedProperty(String paramName) {
				if (paramName.equals("SECKODMAINMENU")) {
						if (request.getParameter("SECKODMAINMENU") != null&& !request.getParameter("SECKODMAINMENU").isEmpty()) {
						try {
							this.MAPP.SECKODMAINMENU = Integer.valueOf(request.getParameter("SECKODMAINMENU"));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							this.MAPP.SECKODMAINMENU = 0;
						}
					} 
				}else if (paramName.equals("WMUS1SECIM")) {
						this.WMUS1SECIM = request.getParameter("WMUS1SECIM").trim();
				}
			}
		*/
		
		AbstractJavaElement.javaCodeBuffer.append("@Override"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("protected void setParametersBindedProperty(String paramName) {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//	if (paramName.equals(\"SECKODMAINMENU\")) {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//			if (request.getParameter(\"SECKODMAINMENU\") != null&& !request.getParameter(\"SECKODMAINMENU\").isEmpty()) {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//			try {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//			this.MAPP.SECKODMAINMENU = Integer.valueOf(request.getParameter(\"SECKODMAINMENU\"));"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//} catch (NumberFormatException e) {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//logger.debug(e.getMessage(), e);"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//this.MAPP.SECKODMAINMENU = 0;"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//}"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//} "+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//}else if (paramName.equals(\"WMUS1SECIM\")) {"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//this.WMUS1SECIM = request.getParameter(\"WMUS1SECIM\").trim();"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("//}"+JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append("}"+JavaConstants.NEW_LINE);
		
		
	}

	private void addInitParamListMethod() {
		 	AbstractJavaElement.javaCodeBuffer.append("@PostConstruct"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("protected void initParamList() {"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//		try {\"+JavaConstants.NEW_LINE);"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//			parameterFieldsList.add(this.getClass().getDeclaredField(\"PHESNO\"));"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//		} catch (NoSuchFieldException e) {"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//			logger.debug(e.getMessage(), e);"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//		} catch (SecurityException e) {"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//			logger.debug(e.getMessage(), e);"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("//		}"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("}"+JavaConstants.NEW_LINE);
	}
	private void addConstructor() {
		
		if(logModel.isMapOrMapTester()){
			return;
		}

//		public GZTBAS21Impl() {
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " "+ implementsClassName+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
//			super();0
		AbstractJavaElement.javaCodeBuffer.append("super();"+JavaConstants.NEW_LINE);
//			this.isClientInteracting=true;
		AbstractJavaElement.javaCodeBuffer.append("this.isClientInteracting="+ConversionLogModel.getInstance().isClientInteracting()+";"+JavaConstants.NEW_LINE);
//		}
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
		

		
	}

	public List<JavaGeneralVariableElement> getGeneralVariables() {
		List<JavaGeneralVariableElement> resultList=new ArrayList<JavaGeneralVariableElement>();
		for (AbstractJava aje : children) {
			if(aje instanceof JavaGeneralVariableElement){
				resultList.add((JavaGeneralVariableElement) aje);
			}
		}
		return resultList;
	}


	private void copyAtTopOfPageToClassLevel() {
		
		AbstractJavaElement curChild, javaFunctionMainElement,javaAtTopOfPageElement,javaAtEndOfPageElement;
		
		javaFunctionMainElement=(AbstractJavaElement) this.getChildWithName("JavaFunctionMainElement");
		
		if(javaFunctionMainElement!=null){
		
			javaAtTopOfPageElement= (AbstractJavaElement) javaFunctionMainElement.getChildWithName("JavaAtTopOfPageElement");
			
			javaAtEndOfPageElement= (AbstractJavaElement) javaFunctionMainElement.getChildWithName("JavaAtEndOfPageElement");
			
			if(javaAtTopOfPageElement!=null){
				this.registerChild(javaAtTopOfPageElement);
			}
			
			if(javaAtEndOfPageElement!=null){
				this.registerChild(javaAtEndOfPageElement);
			}
			
		}
		
	}
	
}

