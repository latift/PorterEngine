
package tr.com.vbt.token;

public class SayiToken<Integer> extends AbstractToken<Integer>
{

	public SayiToken(Integer deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Sayi, satirdakiTokenSirasi);
	}
	
	public SayiToken() {
		super(TokenTipi.Sayi);
	}
	
}
