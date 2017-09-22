
package tr.com.vbt.token;

public class SayiToken<Long> extends AbstractToken<Long>
{

	public SayiToken(Long deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Sayi, satirdakiTokenSirasi);
	}
	
	public SayiToken() {
		super(TokenTipi.Sayi);
	}
	
}
