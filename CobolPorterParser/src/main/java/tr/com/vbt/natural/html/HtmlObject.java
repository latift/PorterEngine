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
public class HtmlObject {
	
	private NaturalTagTypes tagType; //input, label, SubmitButton vs.
	
	private String name; //ad, soyad, adres, telefonno vs. (label yani)
	
	private String value; //ahmet, ali vs.
	
	private int left;
	
	private int top;
	
	private String hotKey;
	
	protected boolean visible=true;
	
	private HtmlColor color;
	
	public NaturalTagTypes getTagType() {
		return tagType;
	}

	public void setTagType(NaturalTagTypes tagType) {
		this.tagType = tagType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public HtmlObject(NaturalTagTypes tagType, String name,
			String value, int top, int left, String hotKey) {
		this.tagType = tagType;
		this.name = name;
		if(value==null){
			value="";
		}
		this.value = value;
	/*	try {
			this.value = replaceSpacesWithUnbreakable(value);
		} catch (Exception e) {
			// logger.debug("",e);
		}*/
		this.left=left;
		this.top=top;
		this.hotKey=hotKey;
	}
	
	public HtmlObject(NaturalTagTypes tagType, String name,
			String value, int top, int left, String hotKey, HtmlColor color) {
		this.tagType = tagType;
		this.name = name;
		if(value==null){
			value="";
		}
		this.value = value;
	/*	try {
			this.value = replaceSpacesWithUnbreakable(value);
		} catch (Exception e) {
			// logger.debug("",e);
		}*/
		this.left=left;
		this.top=top;
		this.hotKey=hotKey;
		this.color=color;
	}
	
	
	private String replaceSpacesWithUnbreakable(String value) {
		return value.replaceAll(" ","&nbsp");
	}

	public HtmlObject(NaturalTagTypes tagType, String name,
			int value, int top,int left , String hotKey) {
		this.tagType = tagType;
		this.name = name;
		if(value==0){
			this.value="";
		}else{
			this.value = Integer.toString(value);
		}
		this.left=left;
		this.top=top;
		this.hotKey=hotKey;
	}
	
	public HtmlObject(NaturalTagTypes tagType, String name,
			int value, int top,int left , String hotKey, HtmlColor color) {
		this.tagType = tagType;
		this.name = name;
		if(value==0){
			this.value="";
		}else{
			this.value = Integer.toString(value);
		}
		this.left=left;
		this.top=top;
		this.hotKey=hotKey;
		this.color=color;
	}

	public HtmlObject(NaturalTagTypes tagType,String name,
			float value, int top, int left, String hotKey) {
		this.tagType = tagType;
		this.name = name;
		if(value==0){
			this.value="";
		}else{
			this.value = Float.toString(value);
		}
		this.top=top;
		this.left=left;
		this.hotKey=hotKey;
		
	}
	
	public HtmlObject(NaturalTagTypes tagType,String name,
			double value, int top, int left, String hotKey, HtmlColor color) {
		this.tagType = tagType;
		this.name = name;
		if(value==0){
			this.value="";
		}else{
			this.value = Double.toString(value);
		}
		this.top=top;
		this.left=left;
		this.hotKey=hotKey;
		this.color=color;
		
	}
	
	public HtmlObject(NaturalTagTypes tagType,String name,
			double value, int top, int left, String hotKey) {
		this.tagType = tagType;
		this.name = name;
		if(value==0){
			this.value="";
		}else{
			this.value = Double.toString(value);
		}
		this.top=top;
		this.left=left;
		this.hotKey=hotKey;
		
	}
	

	public HtmlObject(NaturalTagTypes tagType) {
		this.tagType=tagType;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public String getHotKey() {
		return hotKey;
	}

	public void setHotKey(String hotKey) {
		this.hotKey = hotKey;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public HtmlColor getColor() {
		return color;
	}

	
	public void setColor(HtmlColor color) {
		this.color = color;
	}



	

	
	
	

}
