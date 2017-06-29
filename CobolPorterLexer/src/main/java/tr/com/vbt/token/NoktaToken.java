

package tr.com.vbt.token;


public class NoktaToken<T> extends AbstractToken<T>
{
	public NoktaToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Nokta, satirdakiTokenSirasi);
	}
	
	public NoktaToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean isStarter, boolean isEnder) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Nokta, satirdakiTokenSirasi,isStarter,isEnder);
	}
	
	public NoktaToken(T deger) {
		super(deger, 0, 0, TokenTipi.Nokta, 0);
	}
	
}
