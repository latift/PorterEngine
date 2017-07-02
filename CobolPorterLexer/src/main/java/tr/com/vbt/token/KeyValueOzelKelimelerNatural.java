package tr.com.vbt.token;

import java.util.HashSet;

import tr.com.vbt.token.KeyValueOzelKelimeler.KeyValueKeyword;



public class KeyValueOzelKelimelerNatural {

	public HashSet<KeyValueKeyword> keyValueKeywords = new HashSet<KeyValueKeyword>();

	public class KeyValueKeyword {
		String key;

		public KeyValueKeyword(String key) {
			super();
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

	}

	public KeyValueOzelKelimelerNatural() {
		super();
		keyValueKeywords.add(new KeyValueKeyword("SOURCE-COMPUTER"));
		keyValueKeywords.add(new KeyValueKeyword("OBJECT-COMPUTER"));
		keyValueKeywords.add(new KeyValueKeyword("ASSIGN_TO"));
		//keyValueKeywords.add(new KeyValueKeyword("EXIT"));
		keyValueKeywords.add(new KeyValueKeyword("ORGANIZATION_IS"));
		keyValueKeywords.add(new KeyValueKeyword("ACCESS_MODE_IS"));
		keyValueKeywords.add(new KeyValueKeyword("FILE_STATUS_IS"));
		keyValueKeywords.add(new KeyValueKeyword("RECORD_KEY_IS"));
		keyValueKeywords.add(new KeyValueKeyword("IDENTIFICATION_DIVISION"));
		keyValueKeywords.add(new KeyValueKeyword("PROGRAM-ID"));
		keyValueKeywords.add(new KeyValueKeyword("AUTHOR"));
		keyValueKeywords.add(new KeyValueKeyword("DATE_WRITTEN"));
		keyValueKeywords.add(new KeyValueKeyword("FD"));
		keyValueKeywords.add(new KeyValueKeyword("REDEFINES"));
		keyValueKeywords.add(new KeyValueKeyword("OCCURS"));
		keyValueKeywords.add(new KeyValueKeyword("BLOCK"));
		keyValueKeywords.add(new KeyValueKeyword("RECORDING_MODE_IS"));
		keyValueKeywords.add(new KeyValueKeyword("LABEL_RECORDS_ARE"));
		keyValueKeywords.add(new KeyValueKeyword("REDEFINE"));
	}
}

