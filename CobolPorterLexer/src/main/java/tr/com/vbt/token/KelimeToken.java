

package tr.com.vbt.token;


public class KelimeToken<T> extends AbstractToken<T>
{
	public KelimeToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Kelime, satirdakiTokenSirasi);
	}
	
	public KelimeToken() {
		super(TokenTipi.Kelime);
	}
	
}
