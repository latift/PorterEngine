package tr.com.vbt.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.token.AbstractToken;

public interface PaternManager {

	//public List<AbstractToken> findPatternByTypeCheck(List<AbstractToken> tokenListesi);

	public abstract AbstractCommand findBestMatchedCommand(List<AbstractToken> tokenListesi, int offset) ;
}
