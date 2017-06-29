package tr.com.vbt.natural.html;

/**
 * 
 * <input type="submit" name=<%=htmlObject.getValue()%>
 * value=<%=htmlObject.getValue()%> class="submitButton">
 * 
 * @author 47159500
 *
 */
public class HtmlSubmitButtonTag extends HtmlObject {

	private HtmlInputTypes type; // submit, text, hidden vs.

	public HtmlSubmitButtonTag(String name, String value, int top, int right, String hotKey) {
		super(NaturalTagTypes.SUBMIT_BUTTON, name, value, top, right, hotKey);
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(String name, String value, int top, int right) { // TPS
																				// Icin
																				// Eklendi
		super(NaturalTagTypes.SUBMIT_BUTTON, name, value, top, right, "");
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(String name, String value, int top, int right, String hotKey, boolean visible) {
		super(NaturalTagTypes.SUBMIT_BUTTON, name, value, top, right, hotKey);
		this.type = HtmlInputTypes.TEXT;
		this.visible = visible;
	}

	public HtmlSubmitButtonTag(String value, int top, int right, String hotKey) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, hotKey);
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(int value, int top, int right, String hotKey) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, hotKey);
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(float value, int top, int right, String hotKey) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, hotKey);
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(String name, String value, int top, int right, boolean visible) {
		super(NaturalTagTypes.SUBMIT_BUTTON, name, value, top, right, "");
		this.type = HtmlInputTypes.TEXT;
		this.visible = visible;
	}

	public HtmlSubmitButtonTag(String value, int top, int right) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, "");
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(int value, int top, int right) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, "");
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlSubmitButtonTag(float value, int top, int right) {
		super(NaturalTagTypes.SUBMIT_BUTTON, "Gonder", value, top, right, "");
		this.type = HtmlInputTypes.TEXT;
	}

	public HtmlInputTypes getType() {
		return type;
	}

	public void setType(HtmlInputTypes type) {
		this.type = type;
	}

}
