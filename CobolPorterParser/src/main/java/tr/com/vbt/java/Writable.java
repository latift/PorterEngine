package tr.com.vbt.java;

public interface Writable {
	public boolean writeJavaToStream() throws Exception;
	
	public abstract boolean writeChildrenJavaToStream() throws Exception;
	
	public abstract StringBuilder exportJavaTree();
	
	public abstract StringBuilder exportChildrenJavaTree();
}
