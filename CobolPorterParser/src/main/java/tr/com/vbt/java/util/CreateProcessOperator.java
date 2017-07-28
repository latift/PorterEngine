package tr.com.vbt.java.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.JavaUndefinedElement;
import tr.com.vbt.java.basic.JavaAcceptElement;
import tr.com.vbt.java.basic.JavaAddElement;
import tr.com.vbt.java.basic.JavaBecomesEqualToElementV2;
import tr.com.vbt.java.basic.JavaCallFunctionElement;
import tr.com.vbt.java.basic.JavaClosePrinter;
import tr.com.vbt.java.basic.JavaCommentElement;
import tr.com.vbt.java.basic.JavaCopyElementV2;
import tr.com.vbt.java.basic.JavaDisplayElement;
import tr.com.vbt.java.basic.JavaDivideElement;
import tr.com.vbt.java.basic.JavaExamineForGivingIndexElement;
import tr.com.vbt.java.basic.JavaExitElement;
import tr.com.vbt.java.basic.JavaGlobalUsing;
import tr.com.vbt.java.basic.JavaIncludeElement;
import tr.com.vbt.java.basic.JavaInitializeElement;
import tr.com.vbt.java.basic.JavaLocalUsing;
import tr.com.vbt.java.basic.JavaMultiplyElement;
import tr.com.vbt.java.basic.JavaReInputElement;
import tr.com.vbt.java.basic.JavaRedefineElement;
import tr.com.vbt.java.basic.JavaResetElement;
import tr.com.vbt.java.basic.JavaReturnElement;
import tr.com.vbt.java.basic.JavaScannerElement;
import tr.com.vbt.java.basic.JavaSetControl;
import tr.com.vbt.java.basic.JavaSubtractElement;
import tr.com.vbt.java.basic.JavaUpdateElement;
import tr.com.vbt.java.conditions.JavaCaseElement;
import tr.com.vbt.java.conditions.JavaDefaultElement;
import tr.com.vbt.java.conditions.JavaElseElement;
import tr.com.vbt.java.conditions.JavaElseIfElement;
import tr.com.vbt.java.conditions.JavaIfElementV2;
import tr.com.vbt.java.conditions.JavaIgnoreElement;
import tr.com.vbt.java.conditions.JavaSwitchElementV2;
import tr.com.vbt.java.conditions.JavaWhen;
import tr.com.vbt.java.database.JavaDBViewOfDataTypeElement;
import tr.com.vbt.java.database.JavaDeleteElement;
import tr.com.vbt.java.database.JavaFindNumberWithElement;
import tr.com.vbt.java.database.JavaFindWithElement;
import tr.com.vbt.java.database.JavaGetElement;
import tr.com.vbt.java.database.JavaIfNoRecordsElement;
import tr.com.vbt.java.database.JavaReadByFromDBElement;
import tr.com.vbt.java.database.JavaReadFromDBElement;
import tr.com.vbt.java.database.JavaSaveElement;
import tr.com.vbt.java.database.JavaSqlSelectElement;
import tr.com.vbt.java.file.JavaAtEndElement;
import tr.com.vbt.java.file.JavaCloseFile;
import tr.com.vbt.java.file.JavaDeleteRecordFromFile;
import tr.com.vbt.java.file.JavaFileOpenElement;
import tr.com.vbt.java.file.JavaInvalidKeyElement;
import tr.com.vbt.java.file.JavaMergeElement;
import tr.com.vbt.java.file.JavaNotAtEndElement;
import tr.com.vbt.java.file.JavaNotInvalidKeyElement;
import tr.com.vbt.java.file.JavaReadFileElement;
import tr.com.vbt.java.file.JavaRewriteFileElement;
import tr.com.vbt.java.file.JavaSelectFileElement;
import tr.com.vbt.java.file.JavaSortElement;
import tr.com.vbt.java.file.JavaStartFile;
import tr.com.vbt.java.file.JavaWriteFileElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaCompressElement2;
import tr.com.vbt.java.general.JavaFunctionElement;
import tr.com.vbt.java.general.JavaFunctionMainElement;
import tr.com.vbt.java.general.JavaGeneralVariableElement;
import tr.com.vbt.java.general.JavaLocal;
import tr.com.vbt.java.general.JavaOneDimensionArrayElement;
import tr.com.vbt.java.general.JavaOneDimensionRedefineArray;
import tr.com.vbt.java.general.JavaOneDimensionRedefineArrayOfSimpleElement;
import tr.com.vbt.java.general.JavaParameter;
import tr.com.vbt.java.general.JavaProgramGrup;
import tr.com.vbt.java.general.JavaRedefineDataTypeElement;
import tr.com.vbt.java.general.JavaRedefineGrupElement;
import tr.com.vbt.java.general.JavaSetKeyElement;
import tr.com.vbt.java.general.JavaTwoDimensionArrayElement;
import tr.com.vbt.java.general.JavaTwoDimensionRedifineArrayElement;
import tr.com.vbt.java.jsp.general.JSPGeneral;
import tr.com.vbt.java.loops.JavaDoElement;
import tr.com.vbt.java.loops.JavaEscapeBottomElement;
import tr.com.vbt.java.loops.JavaEscapeRoutineElement;
import tr.com.vbt.java.loops.JavaEscapeTopElement;
import tr.com.vbt.java.loops.JavaFor;
import tr.com.vbt.java.loops.JavaForElementPerformVarying;
import tr.com.vbt.java.loops.JavaForTimesElement;
import tr.com.vbt.java.loops.JavaGotoElement;
import tr.com.vbt.java.loops.JavaIfNoRecords;
import tr.com.vbt.java.loops.JavaOnErrorElement;
import tr.com.vbt.java.loops.JavaPerformThru;
import tr.com.vbt.java.loops.JavaRepeatElement;
import tr.com.vbt.java.loops.JavaRepeatUntilElement;
import tr.com.vbt.java.loops.JavaRepeatWhileElement;
import tr.com.vbt.java.loops.JavaWhileElement;
import tr.com.vbt.java.screen.JavaAtEndOfPageElement;
import tr.com.vbt.java.screen.JavaAtTopOfPageElement;
import tr.com.vbt.java.screen.JavaBaseElement;
import tr.com.vbt.java.screen.JavaControlWindowScreen;
import tr.com.vbt.java.screen.JavaDefineWindow;
import tr.com.vbt.java.screen.JavaInputElement;
import tr.com.vbt.java.screen.JavaInputUsingMapElement;
import tr.com.vbt.java.screen.JavaNewPageElement;
import tr.com.vbt.java.screen.JavaSize;
import tr.com.vbt.java.screen.JavaTerminateElement;
import tr.com.vbt.java.screen.JavaTitle;
import tr.com.vbt.java.screen.JavaWriteElement;
import tr.com.vbt.java.subroutines.JavaCallClassElement;
import tr.com.vbt.java.subroutines.JavaCallNatElement;
import tr.com.vbt.java.subroutines.JavaFetchElement;
import tr.com.vbt.java.subroutines.JavaFetchReturnElement;
import tr.com.vbt.util.ConverterConfiguration;

public class CreateProcessOperator extends ProcessOperator {

	final static Logger logger = LoggerFactory.getLogger(CreateProcessOperator.class);

	public CreateProcessOperator(Rule rule, AbstractCommand sourceElement) {
		super(rule);
		if (sourceElement != null) {
			parentJava = sourceElement.getParentJavaElement();
		}
	}

	@Override
	public AbstractJavaElement operateRule(Rule rule, AbstractCommand sourceElement) {
		String javaElement = rule.getJavaElement();

		switch (javaElement) {
		case "JavaClassElement":
			elementForCreate = new JavaClassElement();
			break;
		case "JavaClassGeneral":
			elementForCreate = JavaClassGeneral.getInstance();
			break;
		case "JavaUndefinedElement":
			elementForCreate = new JavaUndefinedElement();
			break;
		case "JavaGeneralVariableElement":
			elementForCreate = new JavaGeneralVariableElement();
			break;
		case "JavaOneDimensionArrayElement":
			elementForCreate = new JavaOneDimensionArrayElement();
			break;
		case "JavaTwoDimensionArrayElement":
			elementForCreate = new JavaTwoDimensionArrayElement();
			break;
		case "JavaRedefineGrupElement":
			elementForCreate = new JavaRedefineGrupElement();
			break;
		case "JavaRedefineDataTypeElement":
			elementForCreate = new JavaRedefineDataTypeElement();
			break;
		case "JavaOneDimensionRedefineArrayOfSimpleElement":
			elementForCreate = new JavaOneDimensionRedefineArrayOfSimpleElement();
			break;
		case "JavaTwoDimensionRedifineArrayElement":
			elementForCreate = new JavaTwoDimensionRedifineArrayElement();
			break;
		case "JavaInnerClassElement":
			// TODO:
			break;
		case "JavaFunctionMainElement":
			elementForCreate = new JavaFunctionMainElement();
			break;
		case "JavaFunctionElement":
			elementForCreate = new JavaFunctionElement();
			break;
		case "JavaSetKeyElement":
			elementForCreate = new JavaSetKeyElement();
			break;

		// Basic Verbs
		case "JavaCopyElement":
			elementForCreate = new JavaCopyElementV2();
			break;
		case "JavaAddElement":
			elementForCreate = new JavaAddElement();
			break;
		case "JavaExamineForGivingIndexElement":
			elementForCreate = new JavaExamineForGivingIndexElement();
			break;
		case "JavaCallFunctionElement":
			elementForCreate = new JavaCallFunctionElement();
			break;
		case "JavaFetchElement":
			elementForCreate = new JavaFetchElement();
			break;
		case "JavaFetchReturnElement":
			elementForCreate = new JavaFetchReturnElement();
			break;
		case "JavaReturnElement":
			elementForCreate = new JavaReturnElement();
			break;
		case "JavaInitializeElement":
			elementForCreate = new JavaInitializeElement();
			break;
		case "JavaDisplayElement":
			elementForCreate = new JavaDisplayElement();
			break;
		case "JavaScannerElement":
			elementForCreate = new JavaScannerElement();
			break;
		case "JavaAcceptElement":
			elementForCreate = new JavaAcceptElement();
			break;
		case "JavaSubtractElement":
			elementForCreate = new JavaSubtractElement();
			break;
		case "JavaMultiplyElement":
			elementForCreate = new JavaMultiplyElement();
			break;
		case "JavaDivideElement":
			elementForCreate = new JavaDivideElement();
			break;
		case "JavaCommentElement":
			elementForCreate = new JavaCommentElement();
			break;
		case "JavaExitElement":
			elementForCreate = new JavaExitElement();
			break;
		case "JavaRedefineElement":
			elementForCreate = new JavaRedefineElement();
			break;
		case "JavaCompressElement":
			elementForCreate = new JavaCompressElement2();
			break;
		case "JavaUpdateElement":
			elementForCreate = new JavaUpdateElement();
			break;
		case "JavaSaveElement":
			elementForCreate = new JavaSaveElement();
			break;
		case "JavaDeleteElement":
			elementForCreate = new JavaDeleteElement();
			break;
		case "JavaNewPageElement":
			elementForCreate = new JavaNewPageElement();
			break;
		case "JavaTerminateElement":
			elementForCreate = new JavaTerminateElement();
			break;
		// Loop
		case "JavaForTimesElement":
			elementForCreate = new JavaForTimesElement();
			break;
		case "JavaWhileElement":
			elementForCreate = new JavaWhileElement();
			break;
		case "JavaForElementPerformVarying":
			elementForCreate = new JavaForElementPerformVarying();
			break;
		case "JavaPerformThru":
			elementForCreate = new JavaPerformThru();
			break;
		case "JavaGotoElement":
			elementForCreate = new JavaGotoElement();
			break;
		case "JavaRepeatElement":
			elementForCreate = new JavaRepeatElement();
			break;
		case "JavaDoElement":
			elementForCreate = new JavaDoElement();
			break;
		case "JavaOnErrorElement":
			elementForCreate = new JavaOnErrorElement();
			break;
		case "JavaRepeatUntilElement":
			elementForCreate = new JavaRepeatUntilElement();
			break;
		case "JavaRepeatWhileElement":
			elementForCreate = new JavaRepeatWhileElement();
			break;
		case "JavaEscapeTopElement":
			elementForCreate = new JavaEscapeTopElement();
			break;
		case "JavaEscapeBottomElement":
			elementForCreate = new JavaEscapeBottomElement();
			break;
		case "JavaEscapeRoutineElement":
			elementForCreate = new JavaEscapeRoutineElement();
			break;

		// Conditional
		case "JavaIfElement":
			elementForCreate = new JavaIfElementV2();
			break;
		case "JavaElseIfElement":
			elementForCreate = new JavaElseIfElement();
			break;
		case "JavaElseElement":
			elementForCreate = new JavaElseElement();
			break;
		case "JavaSwitchElement":
			elementForCreate = new JavaSwitchElementV2();
			break;
		case "JavaCaseElement":
			elementForCreate = new JavaCaseElement();
			break;
		case "JavaDefaultElement":
			elementForCreate = new JavaDefaultElement();
			break;
		case "JavaIgnoreElement":
			elementForCreate = new JavaIgnoreElement();
			break;

		// File Operations
		case "JavaFileOpenElement":
			elementForCreate = new JavaFileOpenElement();
			break;
		case "JavaReadFile":
			elementForCreate = new JavaReadFileElement();
			break;
		case "JavaWriteFile":
			elementForCreate = new JavaWriteFileElement();
			break;
		case "JavaRewriteFile":
			elementForCreate = new JavaRewriteFileElement();
			break;
		case "JavaDeleteRecordFromFile":
			elementForCreate = new JavaDeleteRecordFromFile();
			break;
		case "JavaStartFile":
			elementForCreate = new JavaStartFile();
			break;
		case "JavaCloseFile":
			elementForCreate = new JavaCloseFile();
			break;
		case "JavaSelectFileElement":
			elementForCreate = new JavaSelectFileElement();
			break;
		case "JavaSort":
			elementForCreate = new JavaSortElement();
			break;
		case "JavaMerge":
			elementForCreate = new JavaMergeElement();
			break;
		case "JavaInvalidKeyElement":
			elementForCreate = new JavaInvalidKeyElement();
			break;
		case "JavaNotInvalidKeyElement":
			elementForCreate = new JavaNotInvalidKeyElement();
			break;
		case "JavaAtEndElement":
			elementForCreate = new JavaAtEndElement();
			break;
		case "JavaNotAtEndElement":
			elementForCreate = new JavaNotAtEndElement();
			break;
		case "JavaCallClassElement":
			elementForCreate = new JavaCallClassElement();
			break;
		case "JavaBecomesEqualToElement":
			elementForCreate = new JavaBecomesEqualToElementV2();
			break;
		case "JavaCallNatElement":
			elementForCreate = new JavaCallNatElement();
			break;

		// Screen
		case "JavaReInputElement":
			elementForCreate = new JavaReInputElement();
			break;
		case "JavaAtTopOfPageElement":
			elementForCreate = new JavaAtTopOfPageElement();
			break;
		case "JavaAtEndOfPageElement":
			elementForCreate = new JavaAtEndOfPageElement();
			break;
		case "JavaInputUsingMapElement":
			elementForCreate = new JavaInputUsingMapElement();
			break;
		case "JavaWriteElement":
			elementForCreate = new JavaWriteElement();
			break;
		case "JavaInputElement":
			elementForCreate = new JavaInputElement();
			break;
		case "JavaResetElement":
			elementForCreate = new JavaResetElement();
			break;

		// Database
		case "JavaReadFromDBElement":
			elementForCreate = new JavaReadFromDBElement();
			break;
		case "JavaReadByFromDBElement":
			elementForCreate = new JavaReadByFromDBElement();
			break;
		case "JavaFindWithElement":
			elementForCreate = new JavaFindWithElement();
			break;
		case "JavaFindNumberWithElement":
			elementForCreate = new JavaFindNumberWithElement();
			break;
		case "JavaGetElement":
			elementForCreate = new JavaGetElement();
			break;
		case "JavaDBViewOfDataTypeElement":
			elementForCreate = new JavaDBViewOfDataTypeElement();
			break;
		case "JavaSqlSelectElement":
			elementForCreate = new JavaSqlSelectElement();
			break;

		case "JavaBaseElement":
			elementForCreate = new JavaBaseElement();
			break;
		case "JavaClosePrinter":
			elementForCreate = new JavaClosePrinter();
			break;
		case "JavaControlWindowScreen":
			elementForCreate = new JavaControlWindowScreen();
			break;
		case "JavaDefineWindow":
			elementForCreate = new JavaDefineWindow();
			break;
		case "JavaFor":
			elementForCreate = new JavaFor();
			break;
		case "JavaGlobalUsing":
			elementForCreate = new JavaGlobalUsing();
			break;
		case "JavaIfNoRecords":
			elementForCreate = new JavaIfNoRecords();
			break;
		case "JavaLocalUsing":
			elementForCreate = new JavaLocalUsing();
			break;
		case "JavaProgramGrup":
			elementForCreate = new JavaProgramGrup();
			break;
		case "JavaOneDimensionRedefineArray":
			elementForCreate = new JavaOneDimensionRedefineArray();
			break;
		case "JavaSetControl":
			elementForCreate = new JavaSetControl();
			break;
		case "JavaSize":
			elementForCreate = new JavaSize();
			break;
		case "JavaTitle":
			elementForCreate = new JavaTitle();
			break;
		case "JavaWhen":
			elementForCreate = new JavaWhen();
			break;
		case "JavaDivide":
			elementForCreate = new JavaDivideElement();
			break;
		case "JavaIfNoRecordsElement":
			elementForCreate = new JavaIfNoRecordsElement();
			break;
		case "JavaIncludeElement":
			elementForCreate = new JavaIncludeElement();
			break;
		case "JavaLocal":
			elementForCreate = new JavaLocal();
			break;
		case "JavaParameter":
			elementForCreate = new JavaParameter();
			break;

		// JSP
		case "JSPGeneral":
			elementForCreate = JSPGeneral.getInstance();
			break;

		default:
		}

		AbstractJavaElement aje;
		if (!javaElement.equals("JavaClassGeneral") && !javaElement.equals("JSPGeneral")) {
			aje = sourceElement.getParent().getJavaElement();
			if (aje == null) {
				// TODO: Bu koda girdi ise sorun var demektir. Burayı hata
				// dosyasına loglayabiliriz.
				if (ConverterConfiguration.sourceLan.equals("NATURAL")) {
					aje = JavaClassGeneral.getInstance().getChildWithName("JavaNaturalClassElement");
				} else if (ConverterConfiguration.sourceLan.equals("COBOL")) {
					aje = JavaClassGeneral.getInstance().getChildWithName("JavaClassElement");
				}
			}
			sourceElement.setParentJavaElement(aje);
			if (sourceElement.getParent().getJavaElement() == null) {

			}
			sourceElement.getParentJavaElement().getChildren().add(elementForCreate);
		}
		try {
			elementForCreate.setParameters(sourceElement.getParameters());
			setSourceCode(sourceElement);
		} catch (Exception e) {
			logger.info(javaElement + " should be defined at CreateProcessOperator");
			e.printStackTrace();
			throw e;
		}
		sourceElement.setJavaElement(elementForCreate);
		logger.info("OPERATE RULE:" + rule.getRuleNum() + "  " + rule.getCobolDetailedName() + "  SourceElement:" + sourceElement.getDetailedCobolName() + "  JavaParentElement:" + sourceElement.getParentJavaElement() + " Added:" + sourceElement.getJavaElement());

		return elementForCreate;
	}

	private void setSourceCode(AbstractCommand sourceElement) {
		elementForCreate.setSourceCode(sourceElement);

	}

}
