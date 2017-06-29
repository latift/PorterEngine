package tr.com.generated;

import tr.com.vbt.java.utils.JavaDBOperatorFactory;
import tr.com.vbt.java.utils.JavaDBSelectOperator;

public class DatabaseOperations {

	JavaDBSelectOperator dbSelectInterFace=JavaDBOperatorFactory.createDBOperator();
	
	Query query=new Query();
	
	class Query{
		String select;
		String into;
		String from;
		String where;
		public String getSelect() {
			return select;
		}
		public void setSelect(String select) {
			this.select = select;
		}
		public String getInto() {
			return into;
		}
		public void setInto(String into) {
			this.into = into;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getWhere() {
			return where;
		}
		public void setWhere(String where) {
			this.where = where;
		}
		
		
	}
}
