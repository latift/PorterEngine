package tr.com.vbt.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tr.com.vbt.java.basic.JavaCallFunctionElement;
import tr.com.vbt.java.database.FinderJavaElement;
import tr.com.vbt.java.database.JavaFindNumberWithElement;
import tr.com.vbt.java.database.JavaFindWithElement;
import tr.com.vbt.java.database.JavaFindWithElementV2;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.general.JavaFunctionElement;
import tr.com.vbt.java.general.JavaFunctionMainElement;
import tr.com.vbt.java.general.JavaNaturalClassElement;
import tr.com.vbt.java.general.JavaValidationElement;
import tr.com.vbt.java.loops.JavaFor;
import tr.com.vbt.java.loops.JavaForTimesElement;
import tr.com.vbt.java.loops.JavaRepeatElement;
import tr.com.vbt.java.loops.JavaRepeatUntilElement;
import tr.com.vbt.java.loops.JavaRepeatWhileElement;
import tr.com.vbt.java.loops.JavaWhileElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;

public class AbstractJavaElement extends AbstractJava{
	
	public static  AbstractJava parentLoopElement;

	@Override
	public boolean hasInputChild(){
		AbstractJavaElement je = null;
		
		if(hasSubroutineInputChild(this)){
			return true;
		}
		for(int i=0;i<children.size();i++){
			
			if(children.get(i) instanceof AbstractJavaElement){
				je=(AbstractJavaElement) children.get(i);
			}else{
				return false;
			}
			
			if(hasSubroutineInputChild(je)){
				return true;
			}else if(je.getJavaElementName().equalsIgnoreCase("JavaInputElement")|| je.getJavaElementName().equalsIgnoreCase("JavaInputUsingMapElement")){
				return true;
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean isInputChild(){
		
		if(this.getJavaElementName().equalsIgnoreCase("JavaInputElement")|| this.getJavaElementName().equalsIgnoreCase("JavaInputUsingMapElement")){
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isOrHasInputChild(){
		
		return isInputChild()&&hasInputChild();
	}
	private boolean hasSubroutineInputChild(AbstractJavaElement je) {
		String pargraphname;
		
		AbstractJava subroutineToken;
		if(je.getJavaElementName().equalsIgnoreCase("JavaCallFunctionElement")){
			
			pargraphname=(String) je.parameters.get("paragraghName");
			
			subroutineToken =getSubroutineJavaElement(pargraphname);
			
			if(subroutineToken==null){
				return false;
			}
			return subroutineToken.hasInputChild();
			
		}
		return false;
	}

	private AbstractJava getSubroutineJavaElement(String pargraphname) {
		
		JavaClassGeneral javaTreeElement = (JavaClassGeneral) JavaClassGeneral.getInstance();
		
		JavaNaturalClassElement javaNaturalClassElement =(JavaNaturalClassElement) javaTreeElement.getChildWithName("JavaNaturalClassElement");
		
		AbstractJava javaFunctionElement = null;
		
		for (AbstractJava aje : javaNaturalClassElement.children) {
			
			if(aje instanceof JavaFunctionElement && pargraphname.equals(aje.parameters.get("subroutineName"))){
					javaFunctionElement= aje;
					break;
				
			}
		}
		
		return javaFunctionElement;
		
	}
	
	private AbstractJavaElement getSubroutineCallerJavaElement(String subroutineName) {
		
		JavaClassGeneral javaTreeElement = (JavaClassGeneral) JavaClassGeneral.getInstance();
		
		JavaNaturalClassElement javaNaturalClassElement =(JavaNaturalClassElement) javaTreeElement.getChildWithName("JavaNaturalClassElement");
		
		AbstractJavaElement aje=null, result;
		
		for (AbstractJava aj : javaNaturalClassElement.children) {
			
			aje=(AbstractJavaElement) aj;
			
			if(aje instanceof JavaCallFunctionElement &&  ((JavaCallFunctionElement)aje).paragraphName.equals(subroutineName)){
					return aje;
			}
		}
	
		
		for (AbstractJava aj : javaNaturalClassElement.children) {
			
			aje=(AbstractJavaElement) aj;
			
			result= aje.getSubroutineCallerJavaElement(subroutineName, aje);
			
			if(result==null){
				continue;
			}else{
				return result;
			}
			
		}
		
		return null;
		
	}
	
	private AbstractJavaElement getSubroutineCallerJavaElement(String subroutineName, AbstractJavaElement ajeParam) {
		
		AbstractJavaElement aje, result;
		
		for (AbstractJava aj : ajeParam.children) {
			
			logger.debug(aj.getJavaElementName());
			
			aje=(AbstractJavaElement) aj;
			
			if(aje instanceof JavaCallFunctionElement &&  ((JavaCallFunctionElement)aje).paragraphName.equals(subroutineName)){
					return aje;
			}
		}

		
		for (AbstractJava aj : ajeParam.children) {
			
			logger.debug(aj.getJavaElementName());
			
			aje=(AbstractJavaElement) aj;
			
			result= aje.getSubroutineCallerJavaElement(subroutineName, aje);
				
			if(result==null){
				continue;
			}else{
				return result;
			}
		}
		return null;
		
	}
	
	public AbstractJavaElement getSubroutineWithName(String subroutineName){
		
		String pargraphname;
		
		AbstractJavaElement aje;
	
		for (AbstractJava aj : children) {
			
			aje=(AbstractJavaElement) aj;
			
			if(aje instanceof JavaFunctionElement){

				pargraphname = (String) aje.parameters.get("paragraghName");
				
				if(pargraphname.equals(subroutineName)){
					return aje;
				}
			}
		}
		return null;
		
	}

	public void moveAllLevelElementsIntoValidationBranch(){
		
		AbstractJavaElement je;
		
		logger.debug("Bütün Childlarını yap"+this.getJavaElementName());
		logger.debug("");
		for(int i=0;i<children.size();i++){
			
			
			if(children.get(i) instanceof AbstractJavaElement){
				je=(AbstractJavaElement) children.get(i);
			}else{
				continue;
			}
			
			if(je.getJavaElementName().equalsIgnoreCase("JavaValidationElement")||
				je.getJavaElementName().equalsIgnoreCase("JavaValidationEnderElement")){
				continue;
			}
			if(je instanceof JavaFunctionMainElement){
				logger.debug("");
			}
			je.moveOneLevelChildsIntoValidationBranch();
		}
		
		for(int i=0;i<children.size();i++){
			
			if(children.get(i) instanceof AbstractJavaElement){
				je=(AbstractJavaElement) children.get(i);
			}else{
				continue;
			}
			
			if(je.getJavaElementName().equalsIgnoreCase("JavaValidationElement")||
					je.getJavaElementName().equalsIgnoreCase("JavaValidationEnderElement")){
					continue;
			}
			
			je.moveAllLevelElementsIntoValidationBranch();
		}
		
	}
	private void moveOneLevelChildsIntoValidationBranch(){
		
		AbstractJava je, jeValidationElement = null;
		
		logger.debug("JavaElementName: "+this.getJavaElementName());
		
		List<AbstractJava> childrenTemp=new ArrayList<AbstractJava>();
		
		for(int i=0;i<children.size();i++){
			
			je=children.get(i);
			
			/*if(je.getJavaElementName()==null ||
					je.getJavaElementName().equalsIgnoreCase("JavaClassGeneral") || 
					je.getJavaElementName().equalsIgnoreCase("JavaNaturalClassElement")||
					je.getJavaElementName().equalsIgnoreCase("JavaFunctionMainElement")||
					je.getJavaElementName().equalsIgnoreCase("JavaFunctionElement")){
				continue;
			}*/
			
			if(je instanceof JavaCallFunctionElement && jeValidationElement==null){ //İlk defa geldi ise ve callnatla karşılaştı ise bir şey yapma( call edilen metod input içeriyormu önemli değil)
				childrenTemp.add(je);
			}else if(je instanceof JavaCallFunctionElement && jeValidationElement!=null && je.hasInputChild()){ //Validation açıldı ise ve input içeren callnatla karşılaştı ise validationu resetle.
																						 //Callnat validation dışında kalsın. //MEthod un icinde zaten validasyon ayrıca yapılır
				jeValidationElement=null;
				childrenTemp.add(je);
				
			}else if(!(je instanceof JavaCallFunctionElement)&&je.isOrHasInputChild()  &&  jeValidationElement!=null){ //Validasyon açılmışsa ve child input içeriyorsa validasyonu resetle
				
					jeValidationElement=new JavaValidationElement();
					jeValidationElement.registerChild(je);
					childrenTemp.add(jeValidationElement);
					logger.debug("Register Validation Start Before:"+je.getJavaElementName());
			}else if(!(je instanceof JavaCallFunctionElement)&& !je.isOrHasInputChild() &&  jeValidationElement!=null){ //Validasyon açılmışsa ve child input içermiyorsa ve input değilse childı validasyonun altına al
				
				jeValidationElement.registerChild(je);
				logger.debug("Register Validation Start Before:"+je.getJavaElementName());
			}else if(!(je instanceof JavaCallFunctionElement)&&je.hasInputChild() &&  jeValidationElement!=null){ //Validasyon açılmamışsa ve child input içeriyorsa validasyonla ilgili bir şey yapma
				
					childrenTemp.add(je);
			}else if(!(je instanceof JavaCallFunctionElement)&&je.isInputChild() &&  jeValidationElement==null){ //Validasyon açılmamışsa ve child input ise validasyon başlat
				
				jeValidationElement=new JavaValidationElement();
				jeValidationElement.registerChild(je);
				childrenTemp.add(jeValidationElement);
			}else{
				
				if(jeValidationElement==null){ //Program içinde definition kısımları için bu eklendi.
					childrenTemp.add(je);
				}else{
					jeValidationElement.registerChild(je);
				}
				
			}
			
		}
		
		children=new ArrayList<>();
		for(int i=0; i< childrenTemp.size();i++){
			children.add(i,childrenTemp.get(i));
		}
		
		if(this.getJavaElementName()==null ||
				this.getJavaElementName().equalsIgnoreCase("JavaClassGeneral") || 
				this.getJavaElementName().equalsIgnoreCase("JavaNaturalClassElement")||
				this.getJavaElementName().equalsIgnoreCase("JavaFunctionMainElement")||
				this.getJavaElementName().equalsIgnoreCase("JavaFunctionElement")){
			
		}else{
			
		}
		
		
		return ;
	}
	

	public void addCatchBlock(){
		JavaClassElement.javaCodeBuffer.append("} catch (VBTBreak e) {"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("} catch (VBTContinue e) {"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("continue"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("} "+JavaConstants.NEW_LINE);
	
	}
	
	public void addTryBlock(){
		JavaClassElement.javaCodeBuffer.append("		try {"+JavaConstants.NEW_LINE); //validation try block
	}
	

	@Override
	protected void createTableNameTokenForColumnsWithoutTable() {
		
		AbstractToken curToken;
		
		List curTokenList;
		List newTokenList=new ArrayList<AbstractToken>();
		
		if(this instanceof FinderJavaElement){ //Find gibilerde bu işlem yapılmaz.
			return ;
		}
		
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value instanceof AbstractToken){
		    	curToken=(AbstractToken) value;
		    	curToken=createTableNameTokenForColumnsWithoutTable(curToken);
		    	parameters.put(key, curToken);
		    }else if(value instanceof List){
		    	
		    	curTokenList=(List) value;
		    	
		    	for(int i=0; i<curTokenList.size();i++){
		    		if(curTokenList.get(i) instanceof AbstractToken){
			    		curToken=(AbstractToken) curTokenList.get(i);
				    	curToken=createTableNameTokenForColumnsWithoutTable(curToken);
				    	newTokenList.add(curToken);
					}else{
		    			newTokenList.add(curTokenList.get(i));
		    		}
		    	}
		    	parameters.put(key, newTokenList);
		    }
		 }
	}
	
	protected AbstractToken createTableNameTokenForColumnsWithoutTable(AbstractToken abstractToken) {
		
		if(!abstractToken.isTableNotDefined()){
			return abstractToken;
		}
		
		
		AbstractToken newToken = null;
		FinderJavaElement finderElement=findParentFind(abstractToken);
		
		if(finderElement==null){
			return abstractToken;
		}
		
		newToken=new KelimeToken(finderElement.getPojoToken().getDeger()+"", abstractToken.getSatirNumarasi(), 0, 0);  //Tablo ismi.
		newToken.setColumnNameToken(abstractToken);
		return newToken;
	}

	public FinderJavaElement findParentFind(AbstractToken abstractToken) {
		FinderJavaElement finderElement;
		
		AbstractJavaElement aje;

		if(this instanceof JavaFunctionElement){  //Parent Function ise o functionın perform edildigi yerden devam et.
			 
			aje=getSubroutineCallerJavaElement(((JavaFunctionElement)this).subroutineName);
			
			if(aje==null){
				return null;
			}
	
			return aje.findParentFind(abstractToken);
		}
		
		if(parent==null){
			return null;
		}
		
		if(!(parent instanceof AbstractJavaElement)){
			return null;
		}
		
		AbstractJavaElement parentJava=(AbstractJavaElement) parent;
		
		
		if(parentJava instanceof FinderJavaElement && controlIfFinderElementHasColumn((FinderJavaElement) parentJava, abstractToken)){  //Parent Find ise ve columu iceriyorsa
			
				return (FinderJavaElement) this.parent;
		}else{
			return parentJava.findParentFind(abstractToken);
		}

	}

	private boolean controlIfFinderElementHasColumn(FinderJavaElement finderElement, AbstractToken abstractToken) {
		
		boolean pojoHasFieldType;
		
		AbstractToken tableNameToken;
		
		tableNameToken=finderElement.getPojoToken(); // TGECICI
		
		pojoHasFieldType=ConvertUtilities.pojoHasField(tableNameToken,  abstractToken);
		
		if(pojoHasFieldType){
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean controlIfInFindStatement() {
		logger.warn("Control:"+this.getJavaElementName());
		if(this.getParent()!=null &&( this.getParent() instanceof JavaFindWithElementV2
				|| this.getParent() instanceof JavaFindNumberWithElement
				|| this.getParent() instanceof JavaFindWithElement
				)){
			return true;
		}else if(this.getParent()!=null && this.getParent() instanceof JavaClassGeneral ){
			return false;
		}else if(this.getParent()==null){
			return false;
		}else if(this.getParent() instanceof JavaWhileElement ||
				this.getParent() instanceof JavaForTimesElement ||
				this.getParent() instanceof JavaFor ||
				this.getParent() instanceof JavaRepeatElement ||
				this.getParent() instanceof JavaRepeatWhileElement ||
				this.getParent() instanceof JavaRepeatUntilElement ){
			parentLoopElement=this.getParent();
			return false;
		}else{
			return this.getParent().controlIfInFindStatement();
		}
		
	}

	
}
