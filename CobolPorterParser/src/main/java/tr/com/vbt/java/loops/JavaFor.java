package tr.com.vbt.java.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class JavaFor extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaFor.class);

	String indexName;
	
	AbstractToken loopStartIndex;
	
	AbstractToken loopEndPoint;
	
	AbstractToken stepToken; 
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try{
			indexName = (String) this.parameters.get("indexName");
			indexName=indexName.replaceAll("-", "_");
			
			loopStartIndex =  (AbstractToken) this.parameters.get("loopStartIndex");
			if(loopStartIndex.getDeger() instanceof String){
				loopStartIndex.setDeger(loopStartIndex.getDeger().toString().replaceAll("-", "_"));
			}
			
			loopEndPoint = (AbstractToken) this.parameters.get("loopEndPoint");
			
			if(loopEndPoint.getDeger() instanceof String){
				loopEndPoint.setDeger(loopEndPoint.getDeger().toString().replaceAll("-", "_"));
			}
			
			stepToken = (AbstractToken) this.parameters.get("step");
			
			boolean stepDirectionAzalanMi = false;
			
			int step=1;
					
			if(stepToken!=null){
				step=(int) ((long)stepToken.getDeger());
				if(step<0){
					stepDirectionAzalanMi = true;
				}
			}
			//for(indexName=loopStartIndex; indexName<loopEndPoint ; indexName++){
			//for(k2=begin; k2>k1; k2=k2-1){
			
			JavaClassElement.javaCodeBuffer.append("for("+indexName+"=");
			if(loopStartIndex!=null){
				if(loopStartIndex.getTip().equals(TokenTipi.Sayi)){
						JavaClassElement.javaCodeBuffer.append((int)((long)loopStartIndex.getDeger())); //+"; "+indexName+"<"+loopEndPoint.getDeger()+" ; "+indexName+"++){");
				}else{
					JavaClassElement.javaCodeBuffer.append(loopStartIndex.getDeger());
				}
			}
			JavaClassElement.javaCodeBuffer.append(";");
			
			if(stepDirectionAzalanMi){
				JavaClassElement.javaCodeBuffer.append(indexName+">");
			}else{
				JavaClassElement.javaCodeBuffer.append(indexName+"<=");
			}
			if(loopEndPoint!=null){
				if(loopEndPoint.getTip().equals(TokenTipi.Sayi)){
						JavaClassElement.javaCodeBuffer.append((int)((long)loopEndPoint.getDeger())); //+"; "+indexName+"<"+loopEndPoint.getDeger()+" ; "+indexName+"++){");
				}else{
					JavaClassElement.javaCodeBuffer.append(loopEndPoint.getDeger());
				}
			}
			JavaClassElement.javaCodeBuffer.append(";");
			
			
			JavaClassElement.javaCodeBuffer.append(indexName+"="+indexName);
			if(stepDirectionAzalanMi){
				JavaClassElement.javaCodeBuffer.append(step+"){");
			}else{
				JavaClassElement.javaCodeBuffer.append("+"+step+"){");
				
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			
			addTryBlock();
			writeChildrenJavaToStream();
			addCatchBlock();
			
			JavaClassElement.javaCodeBuffer.append("}"+"// for");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}

	

}
