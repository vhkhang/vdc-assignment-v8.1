package vn.vdc.nab.alice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
public class AliceApplicationTests extends SpringTestingContext {

	protected static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	protected MockMvc mockMvc;

	@Autowired
	protected HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockHttpSession mockSession;

	@Before
	public void setUpMockMvc() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.mockSession = new MockHttpSession();
	}

	protected MockHttpServletRequestBuilder postInSession(String url) {
		return post(url).session(mockSession);
	}

	protected MockHttpServletRequestBuilder getInSession(String url) {
		return get(url).session(mockSession);
	}

	/**
	 * Creates a valid, versioned url from the given tail: assuming the
	 * current-version is 1, given "customers/12" will return "/v1/customers/tail
	 * 
	 * @param tail the url to be versioned
	 * @return given url prefixt with versioning information.
	 */
	// protected String versionedUrl(String tail) {
//	    String versionedUrl = this.versionedUrl(tail, versionConfiguration.getCurrent());
//	    return versionedUrl;
	// }

	/**
	 * Creates a valid, versioned url from the given tail: assuming 1 is given as
	 * version an "customers/12" as tail, "/v1/customers/tail" will be returned
	 * 
	 * @param tail    the url to be versioned
	 * @param version the veriosn
	 * @return given url prefixt with versioning information.
	 */
	protected String versionedUrl(String tail, int version) {
		if (tail.startsWith("/")) {
			return "/v" + version + tail;
		} else {
			return "/v" + version + "/" + tail;
		}
	}

	/**
	 * Converts the given Object (DTO respectivelly) into well formed JSON.
	 * 
	 * @param dto
	 * @return the dto as json
	 * @throws IOException
	 */
	protected String json(Object dto) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(dto, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	protected <T> T extractContentAs(final ResultActions result, Class<T> clazz) throws UnsupportedEncodingException {
		String contentAsString = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		return json2Object(contentAsString, clazz);
	}

	protected <T> T json2Object(String json, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
