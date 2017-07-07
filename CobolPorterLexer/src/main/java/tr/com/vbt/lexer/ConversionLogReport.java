package tr.com.vbt.lexer;

import java.io.IOException;

import tr.com.vbt.util.WriteToFile;

public class ConversionLogReport {
	
	private static ConversionLogReport instance;
	
	private int programCount;
	
	private int convertedProgramCount;
	
	private int subProgramCount;
	
	private int convertedSubProgramCount;
	
	private int mapCount;
	
	private int convertedMapCount;
	
	
	private NaturalMode mode;
	
	private THYModules module;
	
	public static ConversionLogReport getInstance() {
		if (instance==null){
			instance=new ConversionLogReport();
		}
		return  instance;
	}
	
	public void programConversionStart() {
		programCount++;
	}

	public void programConversionSuccess() {
		convertedProgramCount++;
	}
	
	public void mapConversionStart() {
		mapCount++;
	}
	
	public void mapConversionSuccess() {
		convertedMapCount++;
	}

	public void subProgramConversionStart() {
		subProgramCount++;
	}
	
	public void subProgramConversionSuccess() {
		convertedSubProgramCount++;
	}

	public NaturalMode getMode() {
		return mode;
	}

	public void setMode(NaturalMode mode) {
		this.mode = mode;
	}


	public THYModules getModule() {
		return module;
	}

	public void setModule(THYModules module) {
		this.module = module;
	}

	
	@Override
	public String toString() {
		return this.module+" "+this.mode+
				"  Program Sayısı:"+this.programCount+"  Çevrilen:"+this.convertedProgramCount+
				"  SubProgram Sayısı:"+this.subProgramCount+"  Çevrilen:"+this.convertedSubProgramCount+
				"  Map Sayısı:"+this.mapCount+"  Çevrilen:"+this.convertedMapCount;
	}

	public void writeReport(){
		
		try {
			WriteToFile.appendToFile(this.toString(), ConversionLogModel.getInstance().getFullModuleReportFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
