package tr.com.vbt.exceptions;

public class ConversionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Exception rootCause;
	
	private String exceptionClass;
	
	private String methodName;
	
	private int exceptionLineNumber;
	
	private int occurance;

	public ConversionException(Exception rootCause) {
		super();
		this.rootCause = rootCause;
		//exceptionClass = Thread.currentThread().getStackTrace()[3].getClassName();
		//methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		//exceptionLineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		
		exceptionClass = rootCause.getStackTrace()[0].getClassName();
		methodName = rootCause.getStackTrace()[0].getMethodName();
		exceptionLineNumber = rootCause.getStackTrace()[0].getLineNumber();
				
		/*
		 * String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		 */
	}
	
	

	@Override
	public String toString() {
		return "ConversionException [exceptionClass=" + exceptionClass + ", methodName=" + methodName
				+ ", exceptionLineNumber=" + exceptionLineNumber + "]";
	}



	public Exception getRootCause() {
		return rootCause;
	}

	public void setRootCause(Exception rootCause) {
		this.rootCause = rootCause;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public int getExceptionLineNumber() {
		return exceptionLineNumber;
	}

	public void setExceptionLineNumber(int exceptionLineNumber) {
		this.exceptionLineNumber = exceptionLineNumber;
	}

	@Override
	public boolean equals(Object obj) {
		
		ConversionException paramExc;
		if(!(obj instanceof ConversionException)){
			return false;
		}else{
			paramExc=(ConversionException) obj;
		}
		if(this.exceptionClass.equals(paramExc.getExceptionClass())&&this.methodName.equals(paramExc.getMethodName()) && this.exceptionLineNumber==paramExc.getExceptionLineNumber()){
			return true;
		}
		return false;
	}



	public void increaseOccurance() {
		occurance++;
		
	}



	public int getOccurance() {
		return occurance;
	}



	public void setOccurance(int occurance) {
		this.occurance = occurance;
	}



	public String getMethodName() {
		return methodName;
	}



	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	

}
