package tr.com.vbt.token;

public class ConditionToken extends AbstractToken<String>{

	public ConditionToken(String deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Sabit, satirdakiTokenSirasi);
	}
	
	public ConditionToken() {
		super("", 0,0, TokenTipi.Condition, 0);
	}
}
