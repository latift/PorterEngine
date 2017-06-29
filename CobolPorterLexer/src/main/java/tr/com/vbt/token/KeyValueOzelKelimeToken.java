package tr.com.vbt.token;

public class KeyValueOzelKelimeToken<T, K> extends AbstractToken<T>
{
	private K value;
	
	public KeyValueOzelKelimeToken(T deger, K value, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.KeyValueOzelKelime, satirdakiTokenSirasi);
		this.value=value;
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder("Token:");
		if(deger!=null){
			sb.append(deger.toString());
		}
		if(tip!=null){
			sb.append("  TokenTip :"+tip.toString());
		}
		if(value!=null){
			sb.append(" Value:"+value.toString());
		}
		return sb.toString();
	}
	
	
	
	
}
