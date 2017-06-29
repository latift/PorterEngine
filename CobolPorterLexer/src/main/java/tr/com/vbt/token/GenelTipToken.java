

package tr.com.vbt.token;


public class GenelTipToken<T> extends AbstractToken<T>
{
	public GenelTipToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.GenelTip, satirdakiTokenSirasi);
	}
	
	public GenelTipToken() {
		super(TokenTipi.GenelTip);
	}
	
}
