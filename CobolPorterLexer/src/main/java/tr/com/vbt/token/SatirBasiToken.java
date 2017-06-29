package tr.com.vbt.token;

public class SatirBasiToken<T> extends AbstractToken<T>
{
	public SatirBasiToken(int satirNumarasi, T deger) {
		super(satirNumarasi, TokenTipi.SatirBasi);
		this.tip=TokenTipi.SatirBasi;
		this.deger=deger;
		
	}
	
	public SatirBasiToken(int satirNumarasi, boolean isStarter, boolean isEnder, T deger) {
		super(satirNumarasi, TokenTipi.SatirBasi,isStarter,  isEnder);
		this.tip=TokenTipi.SatirBasi;
		this.deger=deger;
		
	}
	

}
