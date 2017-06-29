package tr.com.vbt.token;

public class SabitToken<T> extends AbstractToken<T>{

	public SabitToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Sabit, satirdakiTokenSirasi);
	}
}
