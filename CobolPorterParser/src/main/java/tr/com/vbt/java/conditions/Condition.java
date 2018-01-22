package tr.com.vbt.java.conditions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

public class Condition implements ConditionInterface{
	
	final static Logger logger = Logger.getLogger(Condition.class);

	public List<ConditionInterface> childConditions=new ArrayList<ConditionInterface>();
	
	public Condition parentCondition;
	
	public ConditionJoiner conditionJoiner;
	
	public boolean notCondition;
	
	public void writeCondition() throws Exception{
		if(notCondition){
			JavaClassElement.javaCodeBuffer.append("!");
		}
		JavaClassElement.javaCodeBuffer.append("(");
		for(int i=0; i<childConditions.size();i++){
			childConditions.get(i).writeCondition();
		}
		JavaClassElement.javaCodeBuffer.append(")");
		
		if(conditionJoiner!=null){
			JavaClassElement.javaCodeBuffer.append(" ");
			conditionJoiner.writeCondition();
		}
	}
	

	public Condition(Condition parentCondition, ConditionJoiner conditionJoiner, boolean notCondition) {
		super();
		this.parentCondition = parentCondition;
		this.conditionJoiner = conditionJoiner;
		this.notCondition = notCondition;
	}

	public Condition(Condition parentCondition, boolean notCondition) {
		super();
		this.parentCondition = parentCondition;
		this.notCondition = notCondition;
	}

	public void addChildCondition(ConditionInterface child){
		this.childConditions.add(child);
	}
	
	public Condition getParentCondition() {
		return parentCondition;
	}

	public void setParentCondition(Condition parentCondition) {
		this.parentCondition = parentCondition;
	}

	//*S**  IF ERR-FOUND AND TAX NE 'STOPAJ' OR NOT(CMD(1:2) NE ' ' AND TAX NE 'STOPAJ' )
	public static Condition Condition1() {
		
		Condition rootCon=new Condition(null,false);
		
		//ERR-FOUND AND
		AbstractToken errFound=new KelimeToken("ERR_FOUND",0,0,0);
		OneItemSimpleCondition oneItemCon=new OneItemSimpleCondition(errFound);
		oneItemCon.setConditionJoiner(new ConditionJoiner(new OzelKelimeToken("AND",0,0,0)));
		rootCon.addChildCondition(oneItemCon);

		
		//TAX NE 'STOPAJ' OR
		AbstractToken tax=new KelimeToken("TAX",0,0,0);
		AbstractToken conOpNe= new OzelKelimeToken("NE",0,0,0);
		AbstractToken stopaj=new KelimeToken("STOPAJ",0,0,0);
		ConditionJoiner conOperatorOr=new ConditionJoiner(new OzelKelimeToken("OR",0,0,0));
		SimpleCondition taxNeStopaj=new SimpleCondition(tax,conOpNe, stopaj,conOperatorOr);
		rootCon.addChildCondition(taxNeStopaj);
		
		
		//NOT(....)
		Condition childCon1=new Condition(rootCon,true);
		rootCon.addChildCondition(childCon1);
		
		//CMD(1:2) NE ' ' AND
		SimpleCondition cmdCon=new SimpleCondition(new KelimeToken("CMD",0,0,0),new OzelKelimeToken("NE",0,0,0), new KelimeToken("STOPAJ",0,0,0),new ConditionJoiner(new OzelKelimeToken("OR",0,0,0)));
		childCon1.addChildCondition(cmdCon);
		
		//TAX NE 'STOPAJ'
		SimpleCondition taxStop2=new SimpleCondition(new KelimeToken("TAX",0,0,0),new OzelKelimeToken("NE",0,0,0), new KelimeToken("STOPAJ",0,0,0));
		childCon1.addChildCondition(taxStop2);
		
		return rootCon;
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
