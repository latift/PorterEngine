package tr.com.generated;

import org.apache.log4j.Logger;


public class BasicVerbsProgram {
	
	final static Logger logger = Logger.getLogger(BasicVerbsProgram.class);
	
	int A;
	int B;
	int C;
	int D;
	int E;
	String WS_STUDENT_NAME;
	int WS_NUM1;
	int WS_NUM2;
	int WS_NUM3;
	int WS_NUM4;

	public void F0_MAIN_PROGRAM() {
		B = B - (A);
		WS_STUDENT_NAME = System.console().readLine();
		WS_NUM1 = WS_NUM2 + WS_NUM1;
		B = A * B;
		B = B / A;
		WS_NUM4 = (WS_NUM1 * WS_NUM2) - WS_NUM3;
		logger.info("I did it ");
		E = D;
		A001();
		return;
	}

	public void A001() {
		logger.info("I am at inside of A001 ");
	}

	public void LOOP_FUNCTION() {
		for (int i = 0; i < 3.0; i++) {
			A001();
		}
	}

	public void CONDITIONAL_FUNCTION() {
		if (A == 1 && A == 2) {
			logger.info(" A 1  veya 2  OLABILIR.. ");
			logger.info("A ");
			if (A == 1 && A == 2) {
				logger.info(" A 1  veya 2  OLABILIR.. ");
			}
		} else {
			logger.info("A ");
		}
	}

}