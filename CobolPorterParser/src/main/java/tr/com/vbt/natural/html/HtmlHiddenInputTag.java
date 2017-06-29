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
public class HtmlHiddenInputTag extends HtmlObject{
	
	private HtmlInputTypes type; //submit, text, hidden vs.
	

	public HtmlHiddenInputTag(String name,
			String value, int top, int right) {
		super(NaturalTagTypes.HIDDEN_INPUTFIELD, name, value, top, right,"" );
		this.type=HtmlInputTypes.HIDDEN;
	}
	
	public HtmlHiddenInputTag( String name,
			int value, int top, int right) {
		super(NaturalTagTypes.HIDDEN_INPUTFIELD, name, value , top, right,"" );
		this.type=HtmlInputTypes.HIDDEN;
	}
	
	public HtmlHiddenInputTag(String name,
			float value, int top, int right) {
		super(NaturalTagTypes.HIDDEN_INPUTFIELD, name, value , top, right,"" );
		this.type=HtmlInputTypes.HIDDEN;
	}

	public HtmlInputTypes getType() {
		return type;
	}

	public void setType(HtmlInputTypes type) {
		this.type = type;
	}


	
	
	

}
