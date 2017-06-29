package tr.com.vbt.java.jsp;

import java.util.Iterator;
import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.jsp.general.JSPConstants;
import tr.com.vbt.token.AbstractToken;


/**
 *  WRITE #NAME-START #NAME-END		-> 		 <form name="write1">
 
  <!-- #NAME-START -->
  <%  NAME_START = request.getParameter("NAME-START");%>
  <%= NAME_START %>
  
  <!-- #NAME-END -->
  <%  NAME_END = request.getParameter("NAME-END");%>
  <%= NAME_END %>
  </form>
 * 
 *
 *	
	Kural: #A Görünce  <%  A = request.getParameter("A");%> <%=A %>
	
 */


public class JSPDisplayFormElement extends  AbstractJavaElement{

	private String formName="form1";
	
	private List<AbstractToken> writeParameters;
	
	
	
	
	public String getFormName() {
		return formName;
	}



	public void setFormName(String formName) {
		this.formName = formName;
	}



	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		writeParameters = (List<AbstractToken> ) this.parameters.get("writeParameters");
			
		
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.FORM_OPEN+" "+JSPConstants.METHOD+"=\"get\" "+JSPConstants.NAME+"=\"" +formName+"\">" + JavaConstants.NEW_LINE );
		AbstractToken currentToken, nextToken;
		Iterator<AbstractToken> iter1= writeParameters.iterator();
		while(iter1.hasNext()){
			currentToken=iter1.next();
			
			if(currentToken.getDeger().equals('#')){
				nextToken=iter1.next();
				AbstractJavaElement.javaCodeBuffer.append("<%" +nextToken.getDeger().toString().replace("-", "_")+" = request.getParameter(\""+ nextToken.getDeger().toString().replace("-", "_")+"\");%>");
				AbstractJavaElement.javaCodeBuffer.append("<%=" +nextToken.getDeger().toString().replace("-", "_")+"%>");
				AbstractJavaElement.javaCodeBuffer.append("<br>");
				
			}else{
				AbstractJavaElement.javaCodeBuffer.append(currentToken.getDeger());
			}
		}
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.SUBMIT_INPUT_TYPE);// <input type="submit" value="Ok">
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.FORM_CLOSE_TAG+ JavaConstants.NEW_LINE );
		return true;
	}



	
	
	

}
