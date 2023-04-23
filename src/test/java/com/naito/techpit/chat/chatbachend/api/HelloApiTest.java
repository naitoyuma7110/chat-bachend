package com.naito.techpit.chat.chatbachend.api;

import java.net.URL;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
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

  // DataSource:aplication.ymlで設定した接続情報のDBを抽象化したクラス
  @Autowired
  private DataSource dataSource;

  @Test
  public void helloTest() throws Exception {

    // IDatabaseTesterを使用してDB操作の準備
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/hello/hello/default/given/");
    // CsvURLDataSetでcsv形式のテストデータをセット
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    // 実行
    databaseTester.onSetup();

    mockMvc.perform(
        // MockMvcRequestBuilders：テスト用リクエストを作成する
        MockMvcRequestBuilders.get("/hello").content("""
            {
              "name": "Lavulite"
            }
            """).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        // MockMvcResultMatchers：レスポンスを検証する
        .andExpect(MockMvcResultMatchers.status().isOk())
        // JSONAssert.assertEquals(expected, actual, strict)
        .andExpect((result) -> JSONAssert.assertEquals("""
            {
              "message": "Hello, world!"
            }
              """, result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualTestTable = actualDataSet.getTable("test");
    var expectedUrl = this.getClass().getResource("/hello/hello/default/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    var expectedTestTable = expectedDataSet.getTable("test");
    Assertion.assertEquals(expectedTestTable, actualTestTable);

  }

}
