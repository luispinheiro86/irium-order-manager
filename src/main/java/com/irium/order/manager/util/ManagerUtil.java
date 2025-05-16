package com.irium.order.manager.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class ManagerUtil {

  private static final ObjectMapper mapper = new ObjectMapper()
	  .setSerializationInclusion(JsonInclude.Include.NON_NULL)
	  .registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	  .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
	  .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static <T> T convertTo(Object o, Class<T> clazz) {
	return mapper.convertValue(o, clazz);
  }

  public static <T> List<T> convertToList(Object o, Class<T> clazz) {
	CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
	return mapper.convertValue(o, type);
  }


}
