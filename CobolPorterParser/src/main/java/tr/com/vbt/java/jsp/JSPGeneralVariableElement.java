package tr.com.vbt.java.jsp;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.jsp.general.JSPConstants;


//	#NAME-START (A20)   <%! String NAME_START; %> 

/**
 * levelNumber,
 * dataName,-->   name
 * pictureClausevalue(9)--> type
 * @author 47159500
 *
 */
public class JSPGeneralVariableElement extends  AbstractJavaElement{

	private String type;
	
	private String name;
	
	private String visibility;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		name = (String) this.parameters.get("dataName");
		//String pictureClausevalue = (String) this.parameters.get("pictureClausevalue");
		type = (String) this.parameters.get("type");
		
		//9 --> int
		
		//TODO: Implement Below Code
		if(type ==null){
			type="String";
		}
		else if(type.equals("number")){
			type="int";
		}else if(type.equals("String")){
			type="String";
		}else {
			type="String";
		}
		JavaClassElement.javaCodeBuffer.append(JSPConstants.DECLARATION_OPEN);
		JavaClassElement.javaCodeBuffer.append(type+" ");
		if(name !=null){
			JavaClassElement.javaCodeBuffer.append(name.replace('-','_'));
		}else {
			JavaClassElement.javaCodeBuffer.append("Untransmitted_Constant_Name");
		}
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
		JavaClassElement.javaCodeBuffer.append(JSPConstants.DECLARATION_CLOSE);
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}
	
	
	

}
