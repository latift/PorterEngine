package tr.com.vbt.java.util;

public class RuleNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	private String cobolDetailedName;
	
	public RuleNotFoundException(String cobolDetailedName) {
		this.cobolDetailedName=cobolDetailedName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCobolDetailedName() {
		return cobolDetailedName;
	}

	public void setCobolDetailedName(String cobolDetailedName) {
		this.cobolDetailedName = cobolDetailedName;
	}

	
	
}
