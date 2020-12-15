package vn.vdc.nab.alice.util;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Provide json mapper methods
 */
@Component
public class JsonMapper {
  
  private static final String EMPTY = "";
  
  @Autowired
  LoggingUtil loggingUtil;

  /**
   * Parses object to json
   * @param obj refs to object will be parsed
   * @return Json if the object is parseable. Otherwise return Empty string
   */
  public String toJson(Object obj) {
    if(Objects.isNull(obj)) {
      return EMPTY;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
    	mapper.setSerializationInclusion(Include.NON_NULL);
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      this.loggingUtil.warn("JSON object could not be parsed!\n" + obj + "\n" + e.getMessage());
      return EMPTY;
    }
  }
  
  public String toJson(Object obj, StdSerializer<Object> serializer) {
    if(Objects.isNull(obj)) {
      return EMPTY;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
    	SimpleModule module = new SimpleModule();
      module.addSerializer(Object.class, serializer);
    	mapper.registerModule(module);
    	mapper.setSerializationInclusion(Include.NON_NULL);
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      this.loggingUtil.warn("JSON object could not be parsed!\n" + obj + "\n" + e.getMessage());
      return EMPTY;
    }
  }
  
  /**
   * @param stream
   * @param clazz
   * @return
   */
  public <T> Optional<T> toObj(InputStream stream, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return Optional.ofNullable(mapper.readValue(stream, clazz));
    } catch (Exception e) {
      this.loggingUtil.warn("Input could not be parsed to JSON!\n" + stream + "\n" + e.getMessage());
      return Optional.empty();
    }
  }
  
  public boolean isEmptyRequest(Object request) {
  	if(request == null) {
  		return true;
  	}
  	
		String json = this.toJson(request);
		json = json.replaceAll("\\s", "");
		return json.equals("{}");
	}
}
