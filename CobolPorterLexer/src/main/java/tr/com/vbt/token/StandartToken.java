package tr.com.vbt.token;

public class StandartToken<T> extends AbstractToken<T>{

	public StandartToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Standart, satirdakiTokenSirasi);
	}
	
}
