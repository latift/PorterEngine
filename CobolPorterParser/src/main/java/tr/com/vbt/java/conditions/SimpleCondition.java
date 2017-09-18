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
			
			boolean isBigDecimalType=false;
			
			VariableTypes conditionLeftType=ConvertUtilities.getVariableType(conditionLeft);
			VariableTypes conditionRightType=ConvertUtilities.getVariableType(conditionRight);
	
			if(conditionLeftType!=null && conditionRightType!=null){
				primitiveType=(conditionLeftType.equals(VariableTypes.INT_TYPE) || conditionLeftType.equals(VariableTypes.LONG_TYPE)) &&
							(conditionRightType.equals(VariableTypes.INT_TYPE) ||  conditionRightType.equals(VariableTypes.LONG_TYPE));
				}
			if(conditionLeftType.equals(VariableTypes.BIG_DECIMAL_TYPE)  ||conditionRightType.equals(VariableTypes.BIG_DECIMAL_TYPE) ){
				isBigDecimalType=true;
			}
			
			
		
			SimpleConditionWriter conWriter;
			//IF TPS-DOF NE MASK(YYMMDD) OR TPS-DOF < TPS-DOS
			if ((conOperator.isKarakter('=')||conOperator.isKarakter("==")||conOperator.isOzelKelime("EQ")) && conditionRight.isMasked()){
				conWriter=new SimpleMaskEqualsConditionWriter();
				
			}else if((conOperator.isKarakter("!=")||conOperator.isOzelKelime("NE")) && conditionRight.isMasked()){
				conWriter=new SimpleMaskNotEqualsConditionWriter();
				
			}else if(conOperator.isKarakter('=')||conOperator.isKarakter("==")||conOperator.isOzelKelime("EQ")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeEqualsConditionWriter();
				}
				else if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter("!=")||conOperator.isOzelKelime("NE")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeNotEqualsConditionWriter();
				}
				else if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeNotEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter('>')||conOperator.isKarakter(">")||conOperator.isOzelKelime("GT")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeGreaterThenConditionWriter();
				}
				else if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeGreaterThenConditionWriter();
				}
			
			}else if(conOperator.isKarakter('>')||conOperator.isKarakter(">=")||conOperator.isOzelKelime("GE")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeGreaterThenOrEqualsConditionWriter();
				}
				else if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeGreaterThenOrEqualsConditionWriter();
				}
			
			}else if(conOperator.isKarakter('<')||conOperator.isKarakter("<")||conOperator.isOzelKelime("LT")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeLessThenConditionWriter();
				}
				else if(primitiveType){
					conWriter=new SimplePrimitiveTypeWriter();
				}else{
					conWriter=new SimpleObjectTypeLessThenConditionWriter();
				}
					
			}else if(conOperator.isKarakter('<')||conOperator.isKarakter("<=")||conOperator.isOzelKelime("LE")){
				if(isBigDecimalType){
					conWriter=new SimpleBigDecimalTypeLessThenOrEqualsConditionWriter();
				}
				else if(primitiveType){
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
