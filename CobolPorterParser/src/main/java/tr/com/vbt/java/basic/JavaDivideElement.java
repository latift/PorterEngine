package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

//DIVIDE A INTO B  --> B (B=B/A)

//DIVIDE 10 INTO PFSAY GIVING TOPLAM_SAYFA REMAINDER KALAN   --> TOPLAM_SAYFA=PFSAY/10; KALAN=PFSAY%10;
public class JavaDivideElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaDivideElement.class);

	private AbstractToken bolunen;

	private AbstractToken bolen;

	private AbstractToken sonuc;

	private AbstractToken kalan;

	public boolean writeJavaToStream() throws Exception {
		super.writeJavaToStream();

		int sourceNumAsInt;

		sonuc = (AbstractToken) this.getParameters().get("sonuc");

		bolunen = (AbstractToken) this.getParameters().get("bolunen");

		bolen = (AbstractToken) this.getParameters().get("bolen");

		kalan = (AbstractToken) this.getParameters().get("kalan");

		try {
			
			String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(sonuc);
			
			String typeOfCopFrom=ConvertUtilities.getTypeOfVariable(bolunen);
			
			if(typeOfCopyTo!=null && typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopFrom!=null && typeOfCopFrom.equalsIgnoreCase("bigdecimal")){
				divideBigDecimal();
				return true;
			}
			
			// sonuc= bolunen/bolen;
			if (sonuc != null) {
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sonuc));

				JavaClassElement.javaCodeBuffer.append("=");

				if (bolunen.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolunen));
				} else if (bolunen.getDeger() instanceof Double) {
					double bolunenDbl = (Double) bolunen.getDeger();
					if (bolunenDbl % 1 == 0) {
						JavaClassElement.javaCodeBuffer.append((long) bolunenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolunenDbl);
					}
				}

				JavaClassElement.javaCodeBuffer.append("/");

				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolen));
		
				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
			}

			// kalan=bolunen % kalan;
			if (kalan != null) {
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(kalan));

				JavaClassElement.javaCodeBuffer.append("=");

				if (bolunen.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolunen));
				} else if (bolunen.getDeger() instanceof Double) {
					double bolunenDbl = (Double) bolunen.getDeger();
					if (bolunenDbl % 1 == 0) {
						JavaClassElement.javaCodeBuffer.append((long) bolunenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolunenDbl);
					}
				}

				JavaClassElement.javaCodeBuffer.append("%");

				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolen));

				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
			}

		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}

		return true;
	}

	private void divideBigDecimal() throws Exception {
					// sonuc= bolunen/bolen;
					//bg3 = bg1.divide(bg2, 3, RoundingMode.CEILING);
					if (sonuc != null) {
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sonuc));

						JavaClassElement.javaCodeBuffer.append("=");
						
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolunen));

						JavaClassElement.javaCodeBuffer.append(".divide(");

						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolen));
						
						JavaClassElement.javaCodeBuffer.append(").setScale(2,RoundingMode.DOWN)"); //scale
						
				
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
					}

					//MathContext mc = new MathContext(2); // 2 precision
					// bg3 = bg1.remainder(bg2, mc);

					if (kalan != null) {
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(kalan));

						JavaClassElement.javaCodeBuffer.append("=");
						
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolunen));

						JavaClassElement.javaCodeBuffer.append(".remainder(");

						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolen));
						
						JavaClassElement.javaCodeBuffer.append(").setScale(2,RoundingMode.DOWN)"); //scale
						
				
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
					}
		
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
