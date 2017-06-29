package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class OneItemSimpleCondition implements ConditionInterface{

		private AbstractToken condition;
		
		public ConditionJoiner conditionJoiner;
		
		public boolean notCondition;

		public OneItemSimpleCondition(AbstractToken condition) {
			super();
			this.condition = condition;
		}

		public OneItemSimpleCondition(AbstractToken condition, ConditionJoiner conditionJoiner) {
			super();
			this.condition = condition;
			this.conditionJoiner = conditionJoiner;
		}

		public AbstractToken getCondition() {
			return condition;
		}

		public void setCondition(AbstractToken condition) {
			this.condition = condition;
		}


		@Override
		public void writeCondition() throws Exception {
			if(notCondition){
				JavaClassElement.javaCodeBuffer.append("!");
			}
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(condition));
			JavaClassElement.javaCodeBuffer.append(" ");
			if(conditionJoiner!=null){
				conditionJoiner.writeCondition();
				JavaClassElement.javaCodeBuffer.append(" ");
			}
			
		}

		public boolean isNotCondition() {
			return notCondition;
		}


		public void setNotCondition(boolean notCondition) {
			this.notCondition = notCondition;
		}

		public ConditionJoiner getConditionJoiner() {
			return conditionJoiner;
		}

		public void setConditionJoiner(ConditionJoiner conditionJoiner) {
			this.conditionJoiner = conditionJoiner;
		}




		
	
}
