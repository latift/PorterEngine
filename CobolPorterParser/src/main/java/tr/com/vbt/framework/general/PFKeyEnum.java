package tr.com.vbt.framework.general;

public enum PFKeyEnum {

	ENTER,
	PF1,
	PF2,
	PF3,
	PF4,
	PF5,
	PF6,
	PF7,
	PF8,
	PF9,
	PF10,
	PF11,
	PF12,
	PF13,
	PF14,
	PF15,
	PF16,
	PF17,
	PF18,
	PF19,
	PF20,
	PF21,
	PF22,
	PF23,
	PF24, QUESTION_MARK;
	
	
	
	public static PFKeyEnum convertPfKeyStringToEnum(String pfKey) {
		switch (pfKey) {
		case "ENTER":
			return ENTER;
		case "PF1":
			return PF1;
		case "PF2":
			return PF2;
		case "PF3":
			return PF3;
		case "PF4":
			return PF4;
		case "PF5":
			return PF5;
		case "PF6":
			return PF6;
		case "PF7":
			return PF7;
		case "PF8":
			return PF8;
		case "PF9":
			return PF9;
		case "PF10":
			return PF10;
		case "PF11":
			return PF11;
		case "PF12":
			return PF12;
		case "PF13":
			return PF13;
		case "PF14":
			return PF14;
		case "PF15":
			return PF15;
		case "PF16":
			return PF16;
		case "PF17":
			return PF17;
		case "PF18":
			return PF18;
		case "PF19":
			return PF19;
		case "PF20":
			return PF20;
		case "PF21":
			return PF21;
		case "PF22":
			return PF22;
		case "PF23":
			return PF23;
		case "PF24":
			return PF24;
		case "?":
			return QUESTION_MARK;
		default:
			return null;
		}
	
	}
}
