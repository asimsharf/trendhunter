//package com.sudagoarth.trendhunter;
//
//import com.jayway.jsonpath.JsonPath;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static java.lang.reflect.Array.get;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ProductsControllerTest {
//    @Autowired
//    MockMvc mvc;
//
//    @Test
//    void createAndFetch() throws Exception {
//        var create = """
//      {"name":"Adidas Ultraboost","brand":"Adidas","category":"Shoes","price":179.99}
//      """;
//        var res = mvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(create))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var id = JsonPath.read(res.getResponse().getContentAsString(), "$.id");
//        mvc.perform(get("/api/products/" + id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.brand").value("Adidas"));
//    }
//}