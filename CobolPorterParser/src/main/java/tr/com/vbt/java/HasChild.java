package tr.com.vbt.java;

import java.util.List;

public interface HasChild {
	
	public void registerChild(AbstractJavaElement child);


	public List<AbstractJavaElement> getChildren();


	public void setChildren(List<AbstractJavaElement> children);

	
	public AbstractJavaElement getChildWithName(String childName);
	
	
	
}
