package vn.vdc.nab.alice;

import org.springframework.context.annotation.Import;

public class TestConfigurations {

	@Import(AliceApplication.class)
	public static class Mocks {

	}

}
