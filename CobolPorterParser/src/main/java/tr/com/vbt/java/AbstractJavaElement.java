package tr.com.vbt.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.convert.TransferFromNaturalToJavaMain;
import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.database.IteratorNameManager;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.CustomAnnotations;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.patern.MapManager;
import tr.com.vbt.patern.MapManagerNaturalImpl;
import tr.com.vbt.util.ConverterConfiguration;

public abstract class AbstractJavaElement  implements Writable, HasChild {
	
	SourceCodeWriter sourceCodeWriter=SourceCodeWriterImpl.getInstance();
	
	final static Logger logger = LoggerFactory.getLogger(TransferFromNaturalToJavaMain.class);

	private String javaElementName;
	
	private AbstractCommand sourceCode;
	
	protected MapManager sourceToJavaStyleManager=MapManagerNaturalImpl.getInstance();
	
	protected List<AbstractJavaElement> children=new ArrayList<AbstractJavaElement>();
	
	protected HashMap<String, Object> parameters=new HashMap<String, Object>();

	private static int tabCount=0;
	
	public static StringBuilder javaCodeBuffer=new StringBuilder();
	
	protected static StringBuilder javaHibernateCodeBuffer=new StringBuilder();
	
	public static Map<String, String> javaHibernateCodeMap=new HashMap<>();
	
	protected static StringBuilder javaDAOInterfaceCodeBuffer=new StringBuilder();
	
	public static Map<String, String> javaDAOInterfaceCodeMap=new HashMap<>();
	
	protected static StringBuilder abstractJavaTreeBuffer=new StringBuilder();
	
	protected CustomAnnotations annotationStr;
	
	protected ConversionLogModel logModel = ConversionLogModel.getInstance();
	
	protected IteratorNameManager itNameManager=new IteratorNameManager();
	
	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void registerChild(AbstractJavaElement child){
		
		if(child==null){
			return;
		}
		this.children.add(child);
		
		return;
	}

	@Override
	public boolean writeChildrenJavaToStream() throws Exception {
		if(children!=null){
			for (AbstractJavaElement aje : children) {
				if(aje!=null){
					try {
						aje.writeJavaToStream();
					} catch (Exception e) {
						logger.warn(e.getLocalizedMessage(),e);
						JavaClassElement.javaCodeBuffer.append(e.getMessage());
						if(ConverterConfiguration.STOP_ENGINE_ON_CONVERSION_ERROR){
							throw e;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	@Override
	public boolean writeJavaToStream() throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		try {
			//JavaClassElement.javaCodeBuffer.append("//ORJINAL NATURAL:"+this.getSourceCode().getSourceCodeSentence());
			logger.debug("WriteOjinalOf:"+this.toString());
			sourceCodeWriter.writeSourceCode(this);
		} catch (Exception e1) {
			logger.error(e1.getLocalizedMessage(),e1);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		return true;
	}

	public AbstractJavaElement getChildWithName(String childName){
		int indexOfLastPoint;
		String classShortName;
		for (AbstractJavaElement aje : children) {
			indexOfLastPoint=aje.getClass().getName().lastIndexOf(".");
			classShortName=aje.getClass().getName().substring(indexOfLastPoint+1);
			if(classShortName.equals(childName)){
				return aje;
			}
		}
		return null;
		
	}
	
	public StringBuilder exportJavaTree() {
		StringBuilder sb=new StringBuilder();
		String className=this.getClass().toString().substring(6);
		sb.append("<"+className+" "+this.javaElementName);
		
		for (Map.Entry entry : this.parameters.entrySet()) {
			sb.append(entry.getKey() +": " + entry.getValue());
			sb.append("  ");
		}
				
		sb.append(">\n");
		/*tabCount++;
		for(int i=0;i<tabCount;i++){
			sb.append("\t");	
		}*/
		sb.append(exportChildrenJavaTree());
		/*tabCount--;
		for(int i=0;i<=tabCount;i++){
			sb.append("\t");	
		}*/
		sb.append("</"+className+">\n");
		return sb;
	}
	
	public StringBuilder exportChildrenJavaTree() {
		StringBuilder sb=new StringBuilder();
		for (AbstractJavaElement element : children) {
			sb.append(element.exportJavaTree());
		}
		return sb;
	}
	
	
	
	public AbstractJavaElement getChildArrayWithName(String childName){
		for (AbstractJavaElement aje : children) {
			if(aje.getJavaElementName().equals(childName)){
				return aje;
			}
		}
		return null;
		
	}


	public String getJavaElementName() {
		return javaElementName;
	}


	public void setJavaElementName(String javaElementName) {
		this.javaElementName = javaElementName;
	}

	public List<AbstractJavaElement> getChildren() {
		return children;
	}

	public List<AbstractJavaElement> getChildrenListByName(String childName) {
		List<AbstractJavaElement> resultList=new ArrayList<AbstractJavaElement>();
		for (AbstractJavaElement aje : children) {
			if(aje.getJavaElementName().equals(childName)){
				resultList.add(aje);
			}
		}
		return resultList;
	}
	
	
	protected void writeFieldAnnotation() {
		if(annotationStr!=null && !annotationStr.toString().trim().isEmpty()){

			JavaClassElement.javaCodeBuffer.append("@"+annotationStr);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			
		}
	}
	
	
	public void setChildren(List<AbstractJavaElement> children) {
		this.children = children;
	}

	public AbstractCommand getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(AbstractCommand sourceCode) {
		this.sourceCode = sourceCode;
	}

	public void resetSourceCode(){
		sourceCodeWriter.resetSourceCode();
	}

	public CustomAnnotations getAnnotationStr() {
		return annotationStr;
	}

	public void setAnnotationStr(CustomAnnotations annotationStr) {
		this.annotationStr = annotationStr;
	}


	
	
	
	
	
}
