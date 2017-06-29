package tr.com.vbt.token;

public class YorumToken<T> extends AbstractToken<T>{

	public YorumToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Yorum, satirdakiTokenSirasi);
	}
}
