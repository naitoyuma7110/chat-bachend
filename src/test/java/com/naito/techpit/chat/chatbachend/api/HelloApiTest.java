package com.naito.techpit.chat.chatbachend.api;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void helloTest() throws Exception {
    mockMvc.perform(
//      MockMvcRequestBuilders：テスト用リクエストを作成する
        MockMvcRequestBuilders.get("/hello")
            .content("""
                {
                  "name": "Lavulite"
                }
                """)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
//          MockMvcResultMatchers：レスポンスを検証する
            .andExpect(MockMvcResultMatchers.status().isOk())
//          JSONAssert.assertEquals(expected, actual, strict)
            .andExpect((result) -> JSONAssert.assertEquals("""
              {
                "message": "Hello, world!"
              }
                """,
            result.getResponse().getContentAsString(),
            false));
  }
  
}
