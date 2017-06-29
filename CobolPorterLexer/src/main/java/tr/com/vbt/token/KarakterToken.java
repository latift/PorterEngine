

package tr.com.vbt.token;


public class KarakterToken<Char> extends AbstractToken<Char>
{
	public KarakterToken(Char deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Karakter, satirdakiTokenSirasi);
	}
	
	public KarakterToken(Char deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean isStarter, boolean isEnder) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Karakter, satirdakiTokenSirasi,isStarter,isEnder);
	}
	
	public KarakterToken() {
		super(TokenTipi.Karakter);
	}
	
}
