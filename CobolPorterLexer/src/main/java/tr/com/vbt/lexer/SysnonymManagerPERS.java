package tr.com.vbt.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.util.ConverterConfiguration;

public class SysnonymManagerPERS extends AbstractSysnoymManager implements SynonymManager{

	final static Logger logger = LoggerFactory.getLogger(SysnonymManagerPERS.class);

	private static SysnonymManagerPERS instance;

	public static SysnonymManagerPERS getInstance() {
		if (instance == null) {
			instance = new SysnonymManagerPERS();
		}
		return instance;
	}

	private SysnonymManagerPERS() {
		super();
		loadSynoymMap();
	}

	

	final protected void loadSynoymMap() {
		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UHM_LIMAN_TLF", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ERRF01", ConverterConfiguration.DEFAULT_SCHEMA + "_ANLASMA");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF02", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF03", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF04", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF05", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF06", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF07", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF08", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF09", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF10", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF11", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF12", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF13", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF14", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF15", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF16", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF17", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF18", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF19", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF20", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF21", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF22", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF23", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF24", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF25", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF90", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF91", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF92", ConverterConfiguration.DEFAULT_SCHEMA + "_DEVF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF02", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF03", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF04", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TEKF01", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TEKF02", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TEKF03", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TEKF04", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TEKF05", ConverterConfiguration.DEFAULT_SCHEMA + "_DFRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_GEN_DATA2", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_LIMAN", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_MVTADR", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_RUNWAY_GENERAL",
				ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_RUNWAY_SPECIFIC",
				ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TAR_UPLIM", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TKS_LIMAN", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UHM_AKARYAKIT", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UHM_LIMAN_HATBKM",
				ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UHM_LIMAN_TLF", ConverterConfiguration.DEFAULT_SCHEMA + "_EKP_LIMAN");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF02", ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF03", ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF17A", ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30A", ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF65", ConverterConfiguration.DEFAULT_SCHEMA + "_ENFF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF39", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_HAR");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_PA", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_PYTABLO", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNVDEG", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF40", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF41", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF44", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_ISGRB");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNVAN", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF01", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF02", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF03", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF04", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF04T", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF05", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF06", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF07", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF08", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF09", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF10", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF11", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF12", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF13", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF14", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF25", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF26", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF45", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF48", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF49", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF52", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF53", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF62", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF63", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF64", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF67", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TASER1", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_OBJECT");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF27", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF28", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF29", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF38", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF38T", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF60", ConverterConfiguration.DEFAULT_SCHEMA + "_ERP_UNITE");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TK_KREF06", ConverterConfiguration.DEFAULT_SCHEMA + "_KET_BSR");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03A", ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KREF04", ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KREF05", ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KREF08", ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_TFK_KREF03A", ConverterConfiguration.DEFAULT_SCHEMA + "_KREF03");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF02", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF03", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF04", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF05", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF06", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF07", ConverterConfiguration.DEFAULT_SCHEMA + "_KSSF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF16", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF17", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF18", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF19", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF20", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF21", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF22", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF23", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF24", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF46", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF54", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF55", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF56", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF57", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF59", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF99", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30K", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30T", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30X", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF30");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF32", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF33", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF35", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF36", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF37", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF47", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF31");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF51", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF34");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF43U", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF43");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF58", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF43");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF66", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF50");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF68", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF61");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF69", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF61");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF70", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF61");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF91", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF92", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF93", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF94", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF95", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PERF96", ConverterConfiguration.DEFAULT_SCHEMA + "_PERF90");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF02", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF03", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF04", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF05", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF06", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF07", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_PILF08", ConverterConfiguration.DEFAULT_SCHEMA + "_PILF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF02", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF03", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF04", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF05", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF06", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF07", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF08", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_STAF09", ConverterConfiguration.DEFAULT_SCHEMA + "_STAF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01B", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF03", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF04", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF05", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF06", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF08", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF09", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF09Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF11", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF12", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF13", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF14", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF14Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF16", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF17", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF17Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF18", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02_1", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02Y_1", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF52", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF02");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF15Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF15");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20_1", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20D", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20Y_1", ConverterConfiguration.DEFAULT_SCHEMA + "_UCRF20");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF02", ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF04", ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF01");

		synonymToRealTableNameMap.put(ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF04Y", ConverterConfiguration.DEFAULT_SCHEMA + "_UCUF01");

		synonymToRealTableNameMap.put("ERRF01", "ANLASMA");

		synonymToRealTableNameMap.put("DEVF02", "DEVF01");

		synonymToRealTableNameMap.put("DEVF03", "DEVF01");

		synonymToRealTableNameMap.put("DEVF04", "DEVF01");

		synonymToRealTableNameMap.put("DEVF05", "DEVF01");

		synonymToRealTableNameMap.put("DEVF06", "DEVF01");

		synonymToRealTableNameMap.put("DEVF07", "DEVF01");

		synonymToRealTableNameMap.put("DEVF08", "DEVF01");

		synonymToRealTableNameMap.put("DEVF09", "DEVF01");

		synonymToRealTableNameMap.put("DEVF10", "DEVF01");

		synonymToRealTableNameMap.put("DEVF11", "DEVF01");

		synonymToRealTableNameMap.put("DEVF12", "DEVF01");

		synonymToRealTableNameMap.put("DEVF13", "DEVF01");

		synonymToRealTableNameMap.put("DEVF14", "DEVF01");

		synonymToRealTableNameMap.put("DEVF15", "DEVF01");

		synonymToRealTableNameMap.put("DEVF16", "DEVF01");

		synonymToRealTableNameMap.put("DEVF17", "DEVF01");

		synonymToRealTableNameMap.put("DEVF18", "DEVF01");

		synonymToRealTableNameMap.put("DEVF19", "DEVF01");

		synonymToRealTableNameMap.put("DEVF20", "DEVF01");

		synonymToRealTableNameMap.put("DEVF21", "DEVF01");

		synonymToRealTableNameMap.put("DEVF22", "DEVF01");

		synonymToRealTableNameMap.put("DEVF23", "DEVF01");

		synonymToRealTableNameMap.put("DEVF24", "DEVF01");

		synonymToRealTableNameMap.put("DEVF25", "DEVF01");

		synonymToRealTableNameMap.put("DEVF90", "DEVF01");

		synonymToRealTableNameMap.put("DEVF91", "DEVF01");

		synonymToRealTableNameMap.put("DEVF92", "DEVF01");

		synonymToRealTableNameMap.put("DFRF02", "DFRF01");

		synonymToRealTableNameMap.put("DFRF03", "DFRF01");

		synonymToRealTableNameMap.put("DFRF04", "DFRF01");

		synonymToRealTableNameMap.put("TEKF01", "DFRF01");

		synonymToRealTableNameMap.put("TEKF02", "DFRF01");

		synonymToRealTableNameMap.put("TEKF03", "DFRF01");

		synonymToRealTableNameMap.put("TEKF04", "DFRF01");

		synonymToRealTableNameMap.put("TEKF05", "DFRF01");

		synonymToRealTableNameMap.put("TAR_GEN_DATA2", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TAR_LIMAN", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TAR_MVTADR", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TAR_RUNWAY_GENERAL", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TAR_RUNWAY_SPECIFIC", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TAR_UPLIM", "EKP_LIMAN");

		synonymToRealTableNameMap.put("TKS_LIMAN", "EKP_LIMAN");

		synonymToRealTableNameMap.put("UHM_AKARYAKIT", "EKP_LIMAN");

		synonymToRealTableNameMap.put("UHM_LIMAN_HATBKM", "EKP_LIMAN");

		synonymToRealTableNameMap.put("UHM_LIMAN_TLF", "EKP_LIMAN");

		synonymToRealTableNameMap.put("ENFF02", "ENFF01");

		synonymToRealTableNameMap.put("ENFF03", "ENFF01");

		synonymToRealTableNameMap.put("PERF17A", "ENFF01");

		synonymToRealTableNameMap.put("PERF30A", "ENFF01");

		synonymToRealTableNameMap.put("PERF65", "ENFF01");

		synonymToRealTableNameMap.put("PERF39", "ERP_HAR");

		synonymToRealTableNameMap.put("ERP_PA", "ERP_ISGRB");

		synonymToRealTableNameMap.put("ERP_PYTABLO", "ERP_ISGRB");

		synonymToRealTableNameMap.put("ERP_UNVDEG", "ERP_ISGRB");

		synonymToRealTableNameMap.put("PERF40", "ERP_ISGRB");

		synonymToRealTableNameMap.put("PERF41", "ERP_ISGRB");

		synonymToRealTableNameMap.put("PERF44", "ERP_ISGRB");

		synonymToRealTableNameMap.put("ERP_UNVAN", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF01", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF02", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF03", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF04", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF04T", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF05", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF06", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF07", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF08", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF09", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF10", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF11", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF12", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF13", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF14", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF25", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF26", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF45", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF48", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF49", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF52", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF53", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF62", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF63", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF64", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF67", "ERP_OBJECT");

		synonymToRealTableNameMap.put("TASER1", "ERP_OBJECT");

		synonymToRealTableNameMap.put("PERF27", "ERP_UNITE");

		synonymToRealTableNameMap.put("PERF28", "ERP_UNITE");

		synonymToRealTableNameMap.put("PERF29", "ERP_UNITE");

		synonymToRealTableNameMap.put("PERF38", "ERP_UNITE");

		synonymToRealTableNameMap.put("PERF38T", "ERP_UNITE");

		synonymToRealTableNameMap.put("PERF60", "ERP_UNITE");

		synonymToRealTableNameMap.put("TK_KREF06", "KET_BSR");

		synonymToRealTableNameMap.put("KREF03A", "KREF03");

		synonymToRealTableNameMap.put("KREF04", "KREF03");

		synonymToRealTableNameMap.put("KREF05", "KREF03");

		synonymToRealTableNameMap.put("KREF08", "KREF03");

		synonymToRealTableNameMap.put("TFK_KREF03A", "KREF03");

		synonymToRealTableNameMap.put("KSSF02", "KSSF01");

		synonymToRealTableNameMap.put("KSSF03", "KSSF01");

		synonymToRealTableNameMap.put("KSSF04", "KSSF01");

		synonymToRealTableNameMap.put("KSSF05", "KSSF01");

		synonymToRealTableNameMap.put("KSSF06", "KSSF01");

		synonymToRealTableNameMap.put("KSSF07", "KSSF01");

		synonymToRealTableNameMap.put("PERF16", "PERF15");

		synonymToRealTableNameMap.put("PERF17", "PERF15");

		synonymToRealTableNameMap.put("PERF18", "PERF15");

		synonymToRealTableNameMap.put("PERF19", "PERF15");

		synonymToRealTableNameMap.put("PERF20", "PERF15");

		synonymToRealTableNameMap.put("PERF21", "PERF15");

		synonymToRealTableNameMap.put("PERF22", "PERF15");

		synonymToRealTableNameMap.put("PERF23", "PERF15");

		synonymToRealTableNameMap.put("PERF24", "PERF15");

		synonymToRealTableNameMap.put("PERF46", "PERF15");

		synonymToRealTableNameMap.put("PERF54", "PERF15");

		synonymToRealTableNameMap.put("PERF55", "PERF15");

		synonymToRealTableNameMap.put("PERF56", "PERF15");

		synonymToRealTableNameMap.put("PERF57", "PERF15");

		synonymToRealTableNameMap.put("PERF59", "PERF15");

		synonymToRealTableNameMap.put("PERF99", "PERF15");

		synonymToRealTableNameMap.put("PERF30K", "PERF30");

		synonymToRealTableNameMap.put("PERF30T", "PERF30");

		synonymToRealTableNameMap.put("PERF30X", "PERF30");

		synonymToRealTableNameMap.put("PERF32", "PERF31");

		synonymToRealTableNameMap.put("PERF33", "PERF31");

		synonymToRealTableNameMap.put("PERF35", "PERF31");

		synonymToRealTableNameMap.put("PERF36", "PERF31");

		synonymToRealTableNameMap.put("PERF37", "PERF31");

		synonymToRealTableNameMap.put("PERF47", "PERF31");

		synonymToRealTableNameMap.put("PERF51", "PERF34");

		synonymToRealTableNameMap.put("PERF43U", "PERF43");

		synonymToRealTableNameMap.put("PERF58", "PERF43");

		synonymToRealTableNameMap.put("PERF66", "PERF50");

		synonymToRealTableNameMap.put("PERF68", "PERF61");

		synonymToRealTableNameMap.put("PERF69", "PERF61");

		synonymToRealTableNameMap.put("PERF70", "PERF61");

		synonymToRealTableNameMap.put("PERF91", "PERF90");

		synonymToRealTableNameMap.put("PERF92", "PERF90");

		synonymToRealTableNameMap.put("PERF93", "PERF90");

		synonymToRealTableNameMap.put("PERF94", "PERF90");

		synonymToRealTableNameMap.put("PERF95", "PERF90");

		synonymToRealTableNameMap.put("PERF96", "PERF90");

		synonymToRealTableNameMap.put("PILF02", "PILF01");

		synonymToRealTableNameMap.put("PILF03", "PILF01");

		synonymToRealTableNameMap.put("PILF04", "PILF01");

		synonymToRealTableNameMap.put("PILF05", "PILF01");

		synonymToRealTableNameMap.put("PILF06", "PILF01");

		synonymToRealTableNameMap.put("PILF07", "PILF01");

		synonymToRealTableNameMap.put("PILF08", "PILF01");

		synonymToRealTableNameMap.put("STAF02", "STAF01");

		synonymToRealTableNameMap.put("STAF03", "STAF01");

		synonymToRealTableNameMap.put("STAF04", "STAF01");

		synonymToRealTableNameMap.put("STAF05", "STAF01");

		synonymToRealTableNameMap.put("STAF06", "STAF01");

		synonymToRealTableNameMap.put("STAF07", "STAF01");

		synonymToRealTableNameMap.put("STAF08", "STAF01");

		synonymToRealTableNameMap.put("STAF09", "STAF01");

		synonymToRealTableNameMap.put("UCRF01B", "UCRF01");

		synonymToRealTableNameMap.put("UCRF01Y", "UCRF01");

		synonymToRealTableNameMap.put("UCRF03", "UCRF01");

		synonymToRealTableNameMap.put("UCRF04", "UCRF01");

		synonymToRealTableNameMap.put("UCRF05", "UCRF01");

		synonymToRealTableNameMap.put("UCRF06", "UCRF01");

		synonymToRealTableNameMap.put("UCRF08", "UCRF01");

		synonymToRealTableNameMap.put("UCRF09", "UCRF01");

		synonymToRealTableNameMap.put("UCRF09Y", "UCRF01");

		synonymToRealTableNameMap.put("UCRF11", "UCRF01");

		synonymToRealTableNameMap.put("UCRF12", "UCRF01");

		synonymToRealTableNameMap.put("UCRF13", "UCRF01");

		synonymToRealTableNameMap.put("UCRF14", "UCRF01");

		synonymToRealTableNameMap.put("UCRF14Y", "UCRF01");

		synonymToRealTableNameMap.put("UCRF16", "UCRF01");

		synonymToRealTableNameMap.put("UCRF17", "UCRF01");

		synonymToRealTableNameMap.put("UCRF17Y", "UCRF01");

		synonymToRealTableNameMap.put("UCRF18", "UCRF01");

		synonymToRealTableNameMap.put("UCRF02_1", "UCRF02");

		synonymToRealTableNameMap.put("UCRF02Y", "UCRF02");

		synonymToRealTableNameMap.put("UCRF02Y_1", "UCRF02");

		synonymToRealTableNameMap.put("UCRF52", "UCRF02");

		synonymToRealTableNameMap.put("UCRF15Y", "UCRF15");

		synonymToRealTableNameMap.put("UCRF20_1", "UCRF20");

		synonymToRealTableNameMap.put("UCRF20D", "UCRF20");

		synonymToRealTableNameMap.put("UCRF20Y", "UCRF20");

		synonymToRealTableNameMap.put("UCRF20Y_1", "UCRF20");

		synonymToRealTableNameMap.put("UCUF02", "UCUF01");

		synonymToRealTableNameMap.put("UCUF04", "UCUF01");

		synonymToRealTableNameMap.put("UCUF04Y", "UCUF01");

	}

	
}
