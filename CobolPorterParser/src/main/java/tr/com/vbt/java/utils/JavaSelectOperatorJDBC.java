package tr.com.vbt.java.utils;

import java.util.ArrayList;
import java.util.List;

public class JavaSelectOperatorJDBC implements JavaDBSelectOperator{

	@Override
	public List<Object> runSelectQuery(String query) {
		List<Object> resultList=new ArrayList<Object>();
		Object result1 = new Object();
		resultList.add(result1);
		return resultList;
	}



}
