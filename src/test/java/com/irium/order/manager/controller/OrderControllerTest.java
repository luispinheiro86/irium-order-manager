package com.irium.order.manager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.irium.order.manager.enumeration.Status;
import com.irium.order.manager.model.request.OrderRequest;
import com.irium.order.manager.model.response.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

  private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                        .registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Autowired
  private MockMvc mvc;

  @Test
  void givenOrderRequest_whenCreateOrder_shouldReturnOrder() throws Exception {
    OrderRequest request = new OrderRequest();
    request.setItemId(1L);
    request.setUserId(2L);
    request.setQuantity(5);
    String result = mvc.perform(post("/api/orders")
                                    .content(mapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    OrderResponse response = mapper.readValue(result, OrderResponse.class);
    assertEquals(5, response.getQuantity());
    assertEquals(Status.PENDING,response.getStatus());
  }

  @Test
  void givenOrderId_whenGetOrder_shouldReturnOrder() throws Exception {
    String result = mvc.perform(get("/api/orders/5").contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    OrderResponse response = mapper.readValue(result, OrderResponse.class);
    assertEquals(10, response.getQuantity());
    assertEquals(Status.PENDING,response.getStatus());
  }

  @Test
  void whenGetAllOrders_shouldReturnListOfOrders() throws Exception {
    String result = mvc.perform(get("/api/orders").contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, OrderResponse.class);
    List<OrderResponse> response = mapper.readValue(result, listType);
    assertEquals(3, response.size());
  }

}
