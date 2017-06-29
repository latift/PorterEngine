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
			// sonuc= bolunen/bolen;
			if (sonuc != null) {
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sonuc));

				JavaClassElement.javaCodeBuffer.append("=");

				if (bolunen.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolunen));
				} else if (bolunen.getDeger() instanceof Double) {
					double bolunenDbl = (Double) bolunen.getDeger();
					if (bolunenDbl % 1 == 0) {
						JavaClassElement.javaCodeBuffer.append((int) bolunenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolunenDbl);
					}
				}

				JavaClassElement.javaCodeBuffer.append("/");

				if (bolen.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(bolen));
				} else if (bolen.getDeger() instanceof Double) {
					int bolenInt;
					double bolenDbl = (Double) bolen.getDeger();
					if (bolenDbl % 1 == 0) {
						JavaClassElement.javaCodeBuffer.append((int) bolenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolenDbl);
					}
				}

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
						JavaClassElement.javaCodeBuffer.append((int) bolunenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolunenDbl);
					}
				}

				JavaClassElement.javaCodeBuffer.append("%");

				if (bolen.getDeger() instanceof String) {
					JavaClassElement.javaCodeBuffer.append(bolen.getDeger());
				} else if (bolen.getDeger() instanceof Double) {
					int bolenInt;
					double bolenDbl = (Double) bolen.getDeger();
					if (bolenDbl % 1 == 0) {
						JavaClassElement.javaCodeBuffer.append((int) bolenDbl);
					} else {
						JavaClassElement.javaCodeBuffer.append(bolenDbl);
					}
				}

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

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
