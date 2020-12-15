package vn.vdc.nab.alice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationConfig {
	
	@Value("${connectors.bob.timeout:10}")
	private int voucherTimeoutInSeconds;
	
	@Value("${connectors.bob.url}")
	private String voucherUrl;


}
