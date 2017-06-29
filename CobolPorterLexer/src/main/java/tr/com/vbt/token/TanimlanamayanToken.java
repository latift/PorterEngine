package tr.com.vbt.token;



public class TanimlanamayanToken<T> extends AbstractToken<T>
{
	public TanimlanamayanToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Tanimlanamayan, satirdakiTokenSirasi);
	}
	
}
