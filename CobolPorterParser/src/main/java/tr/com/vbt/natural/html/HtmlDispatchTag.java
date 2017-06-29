package tr.com.vbt.natural.html;

import java.util.List;



public class HtmlDispatchTag extends HtmlObject{
	
	String destinationJspPage;
	
	List<HtmlParameter> parameters;
	
	public HtmlDispatchTag(String destinationJspPage ) {
		super(NaturalTagTypes.DISPATCH);
		this.destinationJspPage=destinationJspPage;
	}
	
	public HtmlDispatchTag(String destinationJspPage, List<HtmlParameter> parameters) {
		super(NaturalTagTypes.DISPATCH);
		this.destinationJspPage=destinationJspPage;
		this.parameters=parameters;
	}

	public String getDestinationJspPage() {
		return destinationJspPage;
	}

	public void setDestinationJspPage(String destinationJspPage) {
		this.destinationJspPage = destinationJspPage;
	}

	public List<HtmlParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<HtmlParameter> parameters) {
		this.parameters = parameters;
	}
	
	
	

}
