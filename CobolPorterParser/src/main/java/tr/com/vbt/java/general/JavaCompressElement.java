package tr.com.vbt.java.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.TokenTipi;

//*  5130   COMPRESS IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD INTO                                                                    
//5132     MAP2.ADSOY1   --> MAP2.ADSOY1=TOZLUK.getAdi+' '+ TOZLUK.getSoyad;
public class JavaCompressElement extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaCompressElement.class);

	private List<AbstractToken> sourceList = new ArrayList<AbstractToken>();

	private AbstractToken dest, source;

	private ArrayToken arrayTypeSource;

	@Override
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();

		try {
			AbstractToken parameter;
			ArrayToken arrayParameter;
			sourceList = (List<AbstractToken>) this.parameters.get("source");
			dest = (AbstractToken) this.parameters.get("dest"); // TODO Bi

			if (dest.isPojoVariable()) {
				return compressToPojo();
			} else if (dest instanceof ArrayToken) { // TODO Bu case analiz
														// edilmeli.
				arrayTypeSource = (ArrayToken) dest;
				return compressToArray();
			} else if (dest.isRecordVariable()) {
				// 3990 COMPRESS SONVALORGUN '/' SONVALORAY '/' SONVALORYIL INTO
				// MAP_DIZISI.D_SONVALOR(PFSAY) LEAVE NO
				return compressToRecord();
			} else {
				return compressToSimple();
			}
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;

	}

	private boolean compressToRecord() {
		JavaClassElement.javaCodeBuffer.append(dest.getDeger().toString().replace('-', '_'));// MAP_DIZISI
		JavaClassElement.javaCodeBuffer.append(".");
		if (dest.getLinkedToken().getTip().equals(TokenTipi.Array)) {

			ArrayToken destArrayToken = (ArrayToken) dest.getLinkedToken();
			AbstractToken firstDimension;

			int firstDimensionSize;

			AbstractToken secDimension;

			int secDimensionSize;

			firstDimension = destArrayToken.getFirstDimension();
			secDimension = destArrayToken.getSecondDimension();

			JavaClassElement.javaCodeBuffer.append(destArrayToken.getDeger().toString().replace('-', '_'));// MAP_DIZISI

			ArrayToken arrayToken = (ArrayToken) dest.getLinkedToken();
			JavaClassElement.javaCodeBuffer.append("[");
			JavaClassElement.javaCodeBuffer.append(ConvertUtilities.castToInt());
			if (arrayToken.getFirstDimension().getDeger() instanceof Integer) {
				JavaClassElement.javaCodeBuffer.append((long) arrayToken.getFirstDimension().getDeger());
			} else {
				JavaClassElement.javaCodeBuffer.append(arrayToken.getFirstDimension().getDeger());
			}
			JavaClassElement.javaCodeBuffer.append("]");

			if (secDimension != null) {
				if (secDimension.getDeger() instanceof Double) {
					secDimensionSize = ((int) (double) secDimension.getDeger());
					JavaClassElement.javaCodeBuffer.append("[" + secDimensionSize + "-1]");
				} else if (firstDimension.getDeger() instanceof Integer) {
					secDimensionSize = ((int) (long)secDimension.getDeger());
					JavaClassElement.javaCodeBuffer.append("[" + secDimensionSize + "-1]");
				} else {
					JavaClassElement.javaCodeBuffer.append("[" + secDimension.getDeger() + "-1]");
				}
			}
		} else {
			JavaClassElement.javaCodeBuffer
					.append("\"" + dest.getLinkedToken().getDeger().toString().replace('-', '_'));
		}

		JavaClassElement.javaCodeBuffer.append(" = ");

		writeFromPartOfCompressCommand();

		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		return false;
	}

	private void writeFromPartOfCompressCommand() {

		String columnName, columnNameGetter;

		for (int i = 0; i < sourceList.size(); i++) {

			source = sourceList.get(i);

			if (source.getTip().equals(TokenTipi.Array)) {
				/*
				 * arrayTypeSource=(ArrayToken) source;
				 * JavaClassElement.javaCodeBuffer.append(CustomStringUtils.
				 * replaceMiddleLineWithSubLine((String)
				 * arrayParameter.getDeger()));
				 * JavaClassElement.javaCodeBuffer.append("["+arrayParameter.
				 * getFirstDimension().getDeger()+"-1]");
				 * JavaClassElement.javaCodeBuffer.append(",");
				 */
			} else if (source.isPojoVariable()) { // MB Style
				if (source.isSubstringCommand()) {
					String type = ConvertUtilities.getPojosFieldType(source);
					if (type.equals("Timestamp")) {
						// CustomFormatter.format(TOZLUK.getTcyegiris())
						JavaClassElement.javaCodeBuffer.append("CustomFormatter.format(");
					}
					JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_')); // TGECICI
					JavaClassElement.javaCodeBuffer.append(".");
					columnName = source.getColumnNameToken().getDeger().toString(); // GEXTRE_SAYFA
																					// -->setGextreSayfa
					columnNameGetter = Utility.viewNameToPojoGetterName(columnName);
					JavaClassElement.javaCodeBuffer.append(columnNameGetter + "()"); // setGextreSayfa();
					if (type.equals("Timestamp")) {
						// CustomFormatter.format(TOZLUK.getTcyegiris())
						JavaClassElement.javaCodeBuffer.append(")");
					}
					JavaClassElement.javaCodeBuffer.append(".substring(");
					if (source.getSubStringStartIndexString() != null) {
						JavaClassElement.javaCodeBuffer.append(source.getSubStringStartIndexString());
					} else {
						JavaClassElement.javaCodeBuffer.append(source.getSubStringStartIndex());
					}
					JavaClassElement.javaCodeBuffer.append(",");
					if (source.getSubStringEndIndexString() != null) {
						JavaClassElement.javaCodeBuffer.append(source.getSubStringEndIndexString());
					} else {
						int endIndex = source.getSubStringEndIndex() + source.getSubStringStartIndex();
						JavaClassElement.javaCodeBuffer.append(endIndex);
					}
					JavaClassElement.javaCodeBuffer.append(").trim()");

				} else {

					String type = ConvertUtilities.getPojosFieldType(source);
					if (type.equals("Timestamp")) {
						// CustomFormatter.format(TOZLUK.getTcyegiris())
						JavaClassElement.javaCodeBuffer.append("CustomFormatter.format(");
					}

					JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_')); // TGECICI
					JavaClassElement.javaCodeBuffer.append(".");
					columnName = source.getColumnNameToken().getDeger().toString(); // GEXTRE_SAYFA
																					// -->setGextreSayfa
					columnNameGetter = Utility.viewNameToPojoGetterName(columnName);
					JavaClassElement.javaCodeBuffer.append(columnNameGetter + "()"); // setGextreSayfa();

					if (type.equals("Timestamp")) {
						// CustomFormatter.format(TOZLUK.getTcyegiris())
						JavaClassElement.javaCodeBuffer.append(")");
					}
				}
			} else if ( source.isConstantVariableWithQuota()) {
				JavaClassElement.javaCodeBuffer.append("\"");
				JavaClassElement.javaCodeBuffer.append(source.getDeger().toString());
				JavaClassElement.javaCodeBuffer.append("\"");
			} else if (source.getTip().equals(TokenTipi.Karakter)) {
				JavaClassElement.javaCodeBuffer.append("\"");
				JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_'));
				JavaClassElement.javaCodeBuffer.append("\"");
			} else if (source.isRedefinedVariable()) {
				JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_') + ".getValue()");
			} else {
				if (source.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_') + ".trim()");
				} else {
					JavaClassElement.javaCodeBuffer.append(source.getDeger().toString().replace('-', '_') + "");
				}
			}

			if (i < sourceList.size() - 1) {
				JavaClassElement.javaCodeBuffer.append(" + ");
			}
		}
		// JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
	}

	private boolean compressToSimple() {
		if (dest.isRedefinedVariable()) {
			JavaClassElement.javaCodeBuffer.append(dest.getDeger().toString().replaceAll("-", "_") + ".setValue(");
		} else {
			JavaClassElement.javaCodeBuffer.append(dest.getDeger().toString().replaceAll("-", "_") + " = ");
		}

		writeFromPartOfCompressCommand();
		if (dest.isRedefinedVariable()) {
			JavaClassElement.javaCodeBuffer.append(")");
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		return true;
	}

	private boolean compressToArray() {
		JavaClassElement.javaCodeBuffer.append(arrayTypeSource.getDeger().toString().replaceAll("-", "_") + "["
				+ConvertUtilities.castToInt()
				+ arrayTypeSource.getFirstDimension().getDeger().toString().replaceAll("-", "_") + "-1]" + " = ");

		writeFromPartOfCompressCommand();
		if (dest.isRedefinedVariable()) {
			JavaClassElement.javaCodeBuffer.append(")");
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
		return true;

	}

	private boolean compressToPojo() {
		JavaClassElement.javaCodeBuffer.append("//UNDEFINED MANUALLY Implement Compress Code: " + dest.getDeger());
		return false;
	}

}
