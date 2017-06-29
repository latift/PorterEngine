package tr.com.vbt.framework.general;


public class PFKey {

	//PF3:Yeni Hareket
	
	PFKeyEnum pfKey; //PF3
	
	String label="";  //Yeni Hareket
	
	boolean visible; 
	
	boolean isActive;
	
	String hotKey;
	//SET KEY PF3 PF4 

	//SET KEY PF11='%W>' 
	String shortCut;  //W,
	
	

	public PFKey(PFKeyEnum pfKey, String label, boolean visible, boolean isActive, String shortCut, String hotKey) {
		super();
		this.pfKey = pfKey;
		this.label = label;
		this.visible = visible;
		this.isActive = isActive;
		this.shortCut = shortCut;
		this.hotKey=hotKey;
	}
	
	public PFKey(String pfKey, String label, boolean visible, boolean isActive, String shortCut, String hotKey) {
		super();
		this.pfKey = PFKeyEnum.convertPfKeyStringToEnum(pfKey);
		this.label = label;
		this.visible = visible;
		this.isActive = isActive;
		this.shortCut = shortCut;
		this.hotKey=hotKey;
	}
	


	public PFKeyEnum getPfKey() {
		return pfKey;
	}

	public void setPfKey(PFKeyEnum pfKey) {
		this.pfKey = pfKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getShortCut() {
		return shortCut;
	}

	public void setShortCut(String shortCut) {
		this.shortCut = shortCut;
	}

	public String getHotKey() {
		return hotKey;
	}

	public void setHotKey(String hotKey) {
		this.hotKey = hotKey;
	}


	

}
