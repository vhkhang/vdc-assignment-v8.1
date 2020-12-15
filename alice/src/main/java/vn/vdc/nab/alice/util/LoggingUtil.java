package vn.vdc.nab.alice.util;

import org.springframework.stereotype.Component;

@Component
public class LoggingUtil {

	public void info(String msg) {
		System.out.println(msg);
	}

	public void error(String msg) {
		System.err.println(msg);
	}

	public void warn(String msg) {
		System.err.println(msg);
	}

}
