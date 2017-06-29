

package tr.com.vbt.token;


//#SP-STATUS-SAYFA(#SAYFA,*)  -->  ArrayToken(SP-STATUS-SAYFA)
public class ArrayToken<T> extends AbstractToken<T>
{
	
	public AbstractToken firstDimension;
	
	public AbstractToken secondDimension;
	
	
	public ArrayToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, AbstractToken firstDimension) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Array, satirdakiTokenSirasi);
		this.firstDimension=firstDimension;
		this.secondDimension=secondDimension;
	}
	
	
	public ArrayToken(T deger, int satirNumarasi, int uzunluk, int satirdakiTokenSirasi, AbstractToken firstDimension,  AbstractToken secondDimension) {
		super(deger, satirNumarasi, uzunluk, TokenTipi.Array, satirdakiTokenSirasi);
		this.firstDimension=firstDimension;
		this.secondDimension=secondDimension;
	}
	public ArrayToken() {
		super(TokenTipi.Array);
	}

	public AbstractToken getFirstDimension() {
		return firstDimension;
	}

	public void setFirstDimension(AbstractToken firstDimension) {
		this.firstDimension = firstDimension;
	}

	public AbstractToken getSecondDimension() {
		return secondDimension;
	}

	public void setSecondDimension(AbstractToken secondDimension) {
		this.secondDimension = secondDimension;
	}
	
	
	
}
