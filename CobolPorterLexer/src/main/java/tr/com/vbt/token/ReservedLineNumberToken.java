
package tr.com.vbt.token;

public class ReservedLineNumberToken<Integer> extends AbstractToken<Integer>
{

	public ReservedLineNumberToken(Integer deger, int satirNumarasi) {
		super(deger, TokenTipi.SatirNumaralariIcinRezerve);
	}
	
}
