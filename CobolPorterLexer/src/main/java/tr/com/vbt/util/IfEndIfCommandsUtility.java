package tr.com.vbt.util;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * 			Algoritma:
				1) If görürsen Buffer a Enf-İf koy.
				2) End-İf görürsen bufferdan bir tane sil. (End-if i programcı koydu ise)
				3) Nokta görürsen(Complex Inner If serisinin sonuna gelinmiştir.) buffer daki tüm end-ifleri ekle
 * @author 47159500
 *
 */
public class IfEndIfCommandsUtility {
	
	final static Logger logger = LoggerFactory.getLogger(IfEndIfCommandsUtility.class);
	
	private LinkedList<AbstractToken<String>> endIfBuffer = new LinkedList<AbstractToken<String>>();
	
	 public int bufferSize() {
		 return endIfBuffer.size();
	 }
	
	public boolean bufferHasElement() {
		if(endIfBuffer.isEmpty()){
			return false;
		}
		return true;
	}

	public AbstractToken getEndIfFromBuffer() {
		AbstractToken lastElement=endIfBuffer.removeLast();
		logger.info("GET AND REMOVE FROM BUFFER:"+lastElement.getDeger());
		return lastElement;
	}
	
	public void putEndIfTokenToBuffer() {
			AbstractToken endIf=new OzelKelimeToken<String>("END-IF", 0,0,0);
			logger.info("PUT END_IF TO BUFFER:");
			endIfBuffer.add(endIf);
	}
   
    
    
    

}
