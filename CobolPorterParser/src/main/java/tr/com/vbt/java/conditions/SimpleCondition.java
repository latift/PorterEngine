package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.java.utils.VariableTypes;
import tr.com.vbt.token.AbstractToken;

public class SimpleCondition implements ConditionInterface {

	 	private AbstractToken conditionLeft;

	 	public AbstractToken conOperator;

		private AbstractToken conditionRight;
		
		public ConditionJoiner conditionJoiner;
		
		public boolean notCondition;

		public SimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator, AbstractToken conditionRight) {
			super();
			this.conditionLeft = conditionLeft;
			this.conOperator = conOperator;
			this.conditionRight = conditionRight;
		}
		
		

		public SimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator, AbstractToken conditionRight,
				ConditionJoiner conditionJoiner) {
			super();
			this.conditionLeft = conditionLeft;
			this.conOperator = conOperator;
			this.conditionRight = conditionRight;
			this.conditionJoiner = conditionJoiner;
		}



		public AbstractToken getConditionLeft() {
			return conditionLeft;
		}

		public void setConditionLeft(AbstractToken conditionLeft) {
			this.conditionLeft = conditionLeft;
		}

		public AbstractToken getConditionRight() {
			return conditionRight;
		}

		public void setConditionRight(AbstractToken conditionRight) {
			this.conditionRight = conditionRight;
		}



		public AbstractToken getConOperator() {
			return conOperator;
		}



		public void setConOperator(AbstractToken conOperator) {
			this.conOperator = conOperator;
		}



		public ConditionJoiner getConditionJoiner() {
			return conditionJoiner;
		}

		public void setConditionJoiner(ConditionJoiner conditionJoiner) {
			this.conditionJoiner = conditionJoiner;
		}



		@Override
		public void writeCondition() throws Exception {

			if(notCondition){
				JavaClassElement.javaCodeBuffer.append("!");
			}
			boolean primitiveType=false;
			if(ConvertUtilities.getVariableType(conditionLeft)!=null){
				primitiveType=ConvertUtilities.getVariableType(conditionLeft).equals(VariableTypes.BIG_DECIMAL_TYPE) ||
					 ConvertUtilities.getVariableType(conditionLeft).equals(VariableTypes.DATE_TYPE) ||
					 ConvertUtilities.getVariableType(conditionLeft).equals(VariableTypes.INT_TYPE) ||
					 ConvertUtilities.getVariableType(conditionLeft).equals(VariableTypes.LONG_TYPE);
				
				if(!primitiveType){
					primitiveType=ConvertUtilities.getVariableType(conditionRight).equals(VariableTypes.BIG_DECIMAL_TYPE) ||
							 ConvertUtilities.getVariableType(conditionRight).equals(VariableTypes.DATE_TYPE) ||
							 ConvertUtilities.getVariableType(conditionRight).equals(VariableTypes.INT_TYPE) ||
							 ConvertUtilities.getVariableType(conditionRight).equals(VariableTypes.LONG_TYPE);
				}
			}
			
			SimpleConditionWriter conWriter;
			//IF TPS-DOF NE MASK(YYMMDD) OR TPS-DOF < TPS-DOS
			if ((conOperator.isKarakter('=')||conOperator.isKarakter("==")||conOperator.isOzelKelime("EQ")) && conditionRight.isMasked()){
				conWriter=new SimpleMaskEqualsConditionWriter();
				
			}else if((conOperator.isKarakter("!=")||conOperator.isOzelKelime("NE")) && conditionRight.isMasked()){
				conWriter=new SimpleMaskNotEqualsConditionWriter();
				
			}else if(conOperator.isKarakter('=')||conOperator.isKarakter("==")||conOperator.isOzelKelime("EQ")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter("!=")||conOperator.isOzelKelime("NE")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeNotEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter('>')||conOperator.isKarakter(">")||conOperator.isOzelKelime("GT")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeGreaterThenConditionWriter();
				}
			
			}else if(conOperator.isKarakter('>')||conOperator.isKarakter(">=")||conOperator.isOzelKelime("GE")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeGreaterThenOrEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter('<')||conOperator.isKarakter("<")||conOperator.isOzelKelime("LT")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeLessThenConditionWriter();
				}
					
			}else if(conOperator.isKarakter('<')||conOperator.isKarakter("<=")||conOperator.isOzelKelime("LE")){
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeLessThenOrEqualsConditionWriter();
				}
			
			}else{
				if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleUndefinedConditionWriter();
				}
	
			}
			
			conWriter.writeSimpleCondition(conditionLeft,conOperator,conditionRight,conditionJoiner);
				
			if(conditionJoiner!=null){
				JavaClassElement.javaCodeBuffer.append(" ");
				conditionJoiner.writeCondition();
			}
			
		}

		public boolean isNotCondition() {
			return notCondition;
		}


		public void setNotCondition(boolean notCondition) {
			this.notCondition = notCondition;
		}

	
}
