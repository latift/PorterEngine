package tr.com.vbt.cobol.parser;

public interface Levelable {
	
	public int getLevelNumber();
	
	public void setLevelNumber(int levelNumber);
	
	public String getDataName();

	public void setDataName(String dataName);
	
	public void calculateLevel(Levelable previousLevelable);
	
}
