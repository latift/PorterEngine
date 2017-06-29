package tr.com.vbt.java.utils;

public class JavaDBOperatorFactory {

	public static JavaDBSelectOperator createDBOperator(){
		JavaDBSelectOperator operator=new JavaSelectOperatorJDBC();
		return operator;
	}
}
