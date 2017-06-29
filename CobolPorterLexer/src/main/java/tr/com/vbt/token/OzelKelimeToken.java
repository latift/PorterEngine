package tr.com.vbt.token;

public class OzelKelimeToken<T> extends AbstractToken<T>
{
	public OzelKelimeToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.OzelKelime, satirdakiTokenSirasi);
	}
	
	public OzelKelimeToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean isStarter, boolean isEnder) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.OzelKelime, satirdakiTokenSirasi,isStarter, isEnder);
	}

	public OzelKelimeToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean visualToken) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.OzelKelime, satirdakiTokenSirasi,visualToken);
	}
}
