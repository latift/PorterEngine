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
public class HtmlInputTag extends HtmlObject{
	
	private HtmlInputTypes type; //submit, text, hidden vs.
	
	private int min;
	
	private int max=100;
	
	

	public HtmlInputTag(String name,
			String value, int top, int left) {
		super(NaturalTagTypes.INPUTFIELD, name, value, top, left,"" );
		this.type=HtmlInputTypes.TEXT;
	}
	
	public HtmlInputTag( String name,
			int value, int top, int left) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
	}
	
	public HtmlInputTag( String name,
			double value, int top, int left) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
	}
	
	public HtmlInputTag(String name,
			float value, int top, int left) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
	}
	
	
	public HtmlInputTag(String name,
			String value, int top, int left, int min, int max) {
		super(NaturalTagTypes.INPUTFIELD, name, value, top, left,"" );
		this.type=HtmlInputTypes.TEXT;
		this.max=max;
		this.min=min;
	}
	
	public HtmlInputTag( String name,
			int value, int top, int left, int min, int max) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
		this.min=min;
		this.max=max;
	}
	
	public HtmlInputTag(String name,
			float value, int top, int left, int min, int max) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
		this.min=min;
		this.max=max;
	}
	
	public HtmlInputTag(String name,
			double value, int top, int left, int min, int max) {
		super(NaturalTagTypes.INPUTFIELD, name, value , top, left,"" );
		this.type=HtmlInputTypes.TEXT;
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


	
	
	

}
