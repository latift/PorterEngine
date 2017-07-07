package tr.com.vbt.lexer;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

public class AmpersandManager {

	/*
	 **S** &FIELD0 = 'FORMAT PS=24 LS=132 SG=OFF' 
	  *S** &FIELD1 = 'NEWPAGE ' 
	 * S** &FIELD2 = 'WRITE ' 
	 * S** &FIELD3 = ' *LINE-COUNT > 22 ' 
	 * S** &FIELD4 = *PAGE-NUMBER '
	 * 
	 * 	Line 28: *S**  +KOD1 = 'SICNO30'
		Line 29: *S**  +KOD2 = 'SICNO30'
		Line 32: *S**  +KOD1 = 'SOYAD30'
		Line 33: *S**  +KOD2 = 'SOYAD30'
		Line 36: *S**  +KOD1 = 'ISIM130'
		Line 37: *S**  +KOD2 = 'ISIM130'
		Line 40: *S**  +KOD1 = 'ISIM230'
		Line 41: *S**  +KOD2 = 'ISIM230'
		
		
			&WITH
	 	    &KOSUL
	 */
	public static void operateAmpersands(List<AbstractToken> tokenListesi) {

		int satirNo;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {
			if (tokenListesi.get(i).isKarakter('&')) {
				if (tokenListesi.get(i + 1).isKelime("FIELD0")) {

					satirNo = tokenListesi.get(i).getSatirNumarasi();

					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken<>("OFF", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken('=', satirNo, 0, 0));
					tokenListesi.add(i, new KelimeToken<>("SG", satirNo, 0, 0));

					tokenListesi.add(i, new KelimeToken<>("132", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken('=', satirNo, 0, 0));
					tokenListesi.add(i, new KelimeToken<>("LS", satirNo, 0, 0));

					tokenListesi.add(i, new KelimeToken<>("24", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken('=', satirNo, 0, 0));
					tokenListesi.add(i, new KelimeToken<>("PS", satirNo, 0, 0));

					tokenListesi.add(i, new OzelKelimeToken("FORMAT", satirNo, 0, 0));

					/*
					 * tokenListesi.remove(i); OzelKelimeToken ot=new
					 * OzelKelimeToken<String>(ReservedNaturalKeywords.
					 * AMPERSAND, 0, 0, 0,true); ot.setVisualToken(true);
					 * ot.setAmpersand(true); tokenListesi.add(i,ot);
					 * tokenListesi.get(i+1).setAmpersand(true);
					 */
				} else if (tokenListesi.get(i + 1).isKelime("FIELD1")) {
					// *S** &FIELD1 = 'NEWPAGE '
					satirNo = tokenListesi.get(i).getSatirNumarasi();

					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new OzelKelimeToken("NEWPAGE", satirNo, 0, 0));

				} else if (tokenListesi.get(i + 1).isKelime("FIELD2")) {
					// *S** &FIELD2 = 'WRITE '
					satirNo = tokenListesi.get(i).getSatirNumarasi();

					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new OzelKelimeToken("WRITE", satirNo, 0, 0));

				} else if (tokenListesi.get(i + 1).isKelime("FIELD3")) {
					
					//  * S** &FIELD3 = ' *LINE-COUNT > 22 ' 
					satirNo = tokenListesi.get(i).getSatirNumarasi();

					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new SayiToken("22", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken(">", satirNo, 0, 0));
					tokenListesi.add(i, new KelimeToken("LINE_COUNT", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken("*", satirNo, 0, 0));
			
				} else if (tokenListesi.get(i + 1).isKelime("FIELD4")) {
					 //* S** &FIELD4 = *PAGE-NUMBER '
					satirNo = tokenListesi.get(i).getSatirNumarasi();

					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken("PAGE_NUMBER", satirNo, 0, 0));
					tokenListesi.add(i, new KarakterToken("*", satirNo, 0, 0));
			
				}else if (tokenListesi.get(i + 1).isKelime("KOD1")) {
					//*S**  +KOD1 = 'SICNO30'
					satirNo = tokenListesi.get(i).getSatirNumarasi();
					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken("SICNO30", satirNo, 0, 0));
					
				}else if (tokenListesi.get(i + 1).isKelime("KOD2")) {
					//*S**  +KOD2 = 'SICNO30'
					satirNo = tokenListesi.get(i).getSatirNumarasi();
					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken("SICNO30", satirNo, 0, 0));
				}else if (tokenListesi.get(i + 1).isKelime("WITH")) {
					//*S**  +KOD2 = 'SICNO30'
					satirNo = tokenListesi.get(i).getSatirNumarasi();
					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken("ELLE_SET_ET_WITH", satirNo, 0, 0));
				}else if (tokenListesi.get(i + 1).isKelime("KOSUL")) {
					//*S**  +KOD2 = 'SICNO30'
					satirNo = tokenListesi.get(i).getSatirNumarasi();
					tokenListesi.remove(i);
					tokenListesi.remove(i);
					tokenListesi.add(i, new KelimeToken("ELLE_SET_ET_KOSUL", satirNo, 0, 0));
				}
			}

			else if (tokenListesi.get(i).isKarakter('+') && tokenListesi.get(i + 1).isOneOfKelime("FIELD0","FIELD1","FIELD2","FIELD3","FIELD4","FIELD5","KOD1","KOD2","WITH","KOSUL")) {
				tokenListesi.remove(i);
				
			}

		}
	}

}
