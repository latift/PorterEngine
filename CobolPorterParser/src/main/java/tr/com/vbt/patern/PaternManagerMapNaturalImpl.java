package tr.com.vbt.patern;

import tr.com.vbt.natural.parser.enders.patern.PaternEndDefineData;
import tr.com.vbt.natural.parser.enders.patern.PaternEndMainStart;
import tr.com.vbt.natural.parser.general.patern.PaternDefineData;
import tr.com.vbt.natural.parser.general.patern.PaternMainStart;
import tr.com.vbt.natural.parser.screen.patern.PaternInput;
import tr.com.vbt.natural.parser.screen.patern.PaternInputNoErase;
import tr.com.vbt.natural.parser.screen.patern.PaternWrite;
import tr.com.vbt.natural.parser.screen.patern.PaternWriteNoTitle;

public class PaternManagerMapNaturalImpl  extends AbstractPaternManagerNatural{

	public PaternManagerMapNaturalImpl() {
		super();
		
		
		commmandPatternList.add(new PaternDefineData());
		
		commmandPatternList.add(new PaternEndDefineData());
		commmandPatternList.add(new PaternMainStart());
		commmandPatternList.add(new PaternEndMainStart());
		
		commmandPatternList.add(new PaternInput());
		commmandPatternList.add(new PaternInputNoErase());
		commmandPatternList.add(new PaternWriteNoTitle());
		commmandPatternList.add(new PaternWrite());
		}		

}

