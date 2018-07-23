package tr.com.vbt.token;

public class CommandKeyToken<T> extends AbstractToken<T>
{
	public CommandKeyToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.CommandKey, satirdakiTokenSirasi);
	}
	
	public CommandKeyToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean isStarter, boolean isEnder) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.CommandKey, satirdakiTokenSirasi,isStarter, isEnder);
	}

	public CommandKeyToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, boolean visualToken) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.CommandKey, satirdakiTokenSirasi,visualToken);
	}
}
