package tr.com.vbt.cobol.convert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.database.IteratorNameManager;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalLexing;
import tr.com.vbt.lexer.Phase;
import tr.com.vbt.natural.parser.general.ElementNaturalProgram;
import tr.com.vbt.patern.CommandList;
import tr.com.vbt.patern.NaturalCommandList;
import tr.com.vbt.patern.PaternManager;
import tr.com.vbt.patern.PaternManagerDataTypesNaturalImpl;
import tr.com.vbt.patern.PaternManagerMapNaturalImpl;
import tr.com.vbt.patern.PaternManagerNaturalImpl;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.ConversionMode;
import tr.com.vbt.util.WriteToFile;

public class TransferFromNaturalToJavaMainAllElements {

	

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	// Type1: java Latif File HanStart
	// Type2: java Latif Folder

	// Latif WINDOWS MB Map Files IDGM0004
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		String[] argsForProgram = new String[8];
		String[] argsForSubprogram = new String[8];
		String[] argsForMap = new String[8];
		argsForMap[0]=args[0];//USer 
		argsForMap[1]=args[1];//OS
		argsForMap[2]=args[2];//Customer
		argsForMap[3]=args[3];//Module
		argsForMap[4]=args[4];//schema
		argsForMap[5]="MAP";
		argsForMap[6]=args[5];//folder
		if(argsForMap[6].equals("Folder")) {
			
			argsForMap[7]=args[6];//ALL_SOURCE_CODES
		}
		
		
		argsForSubprogram[0]=args[0];
		argsForSubprogram[1]=args[1];
		argsForSubprogram[2]=args[2];
		argsForSubprogram[3]=args[3];
		argsForSubprogram[4]=args[4];
		argsForSubprogram[5]="SUBPROGRAM";
		argsForSubprogram[6]=args[5]; 
		if(argsForSubprogram[6].equals("Folder")) {
			
			argsForSubprogram[7]=args[6];
		}
		
		argsForProgram[0]=args[0];
		argsForProgram[1]=args[1];
		argsForProgram[2]=args[2];
		argsForProgram[3]=args[3];
		argsForProgram[4]=args[4];
		argsForProgram[5]="Program";
		argsForProgram[6]=args[5];
		if(argsForProgram[6].equals("Folder")) {
			
			argsForProgram[7]=args[6];
		}
		
		
		
		TransferFromNaturalToJavaMain fromNaturalToJavaMain=new  TransferFromNaturalToJavaMain();
		fromNaturalToJavaMain.operateConversion(argsForProgram);
		
		TransferFromNaturalSubProgramsToJava fromNaturalSubProgramToJavaMain=new  TransferFromNaturalSubProgramsToJava();
		fromNaturalSubProgramToJavaMain.operateConversionSubPrograms(argsForSubprogram);
		
		TransferFromNaturalToJavaMap fromNaturalMapToJavaMain=new  TransferFromNaturalToJavaMap();
		fromNaturalMapToJavaMain.operateConversionMAP(argsForMap);
		
	}
	
	
	

}
