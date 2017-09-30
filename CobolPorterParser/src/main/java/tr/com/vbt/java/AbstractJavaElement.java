package tr.com.vbt.java;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.java.basic.JavaCallFunctionElement;
import tr.com.vbt.java.basic.JavaSubtractElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaFunctionElement;
import tr.com.vbt.java.general.JavaFunctionMainElement;
import tr.com.vbt.java.general.JavaNaturalClassElement;
import tr.com.vbt.java.general.JavaValidationElement;

public class AbstractJavaElement extends AbstractJava{

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
	
	public AbstractJava getSubroutineWithName(String subroutineName){
		
		String pargraphname;
	
		for (AbstractJava aje : children) {
		
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
}
