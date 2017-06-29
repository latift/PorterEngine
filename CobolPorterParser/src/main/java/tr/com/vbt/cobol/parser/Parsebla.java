package tr.com.vbt.cobol.parser;

public interface Parsebla {

		//String oku-- CurrentTokenı set et. Child register et. Cobol Aşğaıcını oluştur.
		void parse();
		
		//String oku-- CurrentTokenı set et. Child için parse çağır.
		void parseChild();
		
		//Child element listesine child ekle
		void addChild(AbstractCommand e);

		//
		void export(StringBuilder sb);
		
		String exportContents();
		
		String exportCommands();
}
