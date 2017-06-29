package tr.com.vbt.natural.html;

/**
 * 
 * name:		text:						Specifies the name of an <input> element
 * max:		number date:					Specifies the maximum value for an <input> element
 * type:  text,button,checkbox, submit vs.  Specifies the type <input> element to display
 * value:	text							Specifies the value of an <input> element
 */

/**
 *  <label for="male">Male</label>
  <input type="radio" name="gender" id="male" value="male"><br>
  <input type="submit" value="SORGULA">
 * 
 *<input type="text" name=Ahmet value=Ahmet>

 */
public class HtmlInputTagPopupable extends HtmlObject{
	
	private HtmlInputTypes type; //submit, text, hidden vs.
	
	private int min;
	
	private int max=100;
	
	private String calledProgram;
	
	private String caller;
	
	
	

	public HtmlInputTagPopupable(String name, String value, int top, int left,
			 String calledProgram, String caller, int min, int max) {
		super(NaturalTagTypes.POPUPABLE_INPUTFIELD, name, value, top, left,"");
		this.type = HtmlInputTypes.SUBMIT;
		this.calledProgram = calledProgram;
		this.caller = caller;
		this.min=min;
		this.max=max;
	}


	
	public HtmlInputTypes getType() {
		return type;
	}

	public void setType(HtmlInputTypes type) {
		this.type = type;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getCalledProgram() {
		return calledProgram;
	}

	public void setCalledProgram(String calledProgram) {
		this.calledProgram = calledProgram;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}


	

	
	
	

}
