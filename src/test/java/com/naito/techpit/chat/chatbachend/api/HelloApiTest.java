package com.naito.techpit.chat.chatbachend.api;

import java.net.URL;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

  @ParameterizedTest
  @MethodSource("helloTestProvider") // helloTestProviderメソッドによりパラメータを取得
  public void helloTest(String queryString, String expectedBody, String dbPath) throws Exception {

    // IDatabaseTesterを使用してDBテストの準備(実際のDBに影響を及ぼさない)
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/hello/hello/" + dbPath + "/given/");
    // CsvURLDataSetでcsv形式のテストデータをセット
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();


    // リクエストとレスポンスのテスト
    mockMvc.perform(
        // MockMvcRequestBuilders：テスト用リクエストを作成する
        MockMvcRequestBuilders.get("/hello" + queryString).accept(MediaType.APPLICATION_JSON))
        // MockMvcResultMatchers：レスポンスを検証する
        .andExpect(MockMvcResultMatchers.status().isOk())
        // JSONAssert.assertEquals(expected, actual, strict(比較の厳格さ))
        .andExpect((result) -> JSONAssert.assertEquals(expectedBody,
            result.getResponse().getContentAsString(), false));

    // DBのテスト
    // var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualDataSet = databaseTester.getDataSet();
    var expectedUrl = this.getClass().getResource("/hello/hello/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    // var actualTable = expectedDataSet.getTable("test");
    // var expectedTable = actualDataSet.getTable("test");
    Assertion.assertEquals(actualDataSet, expectedDataSet);


  }

  /*
   * 引数を提供するメソッドの条件
   * 
   * - static メソッドである - ArgumentsクラスのStreamを返却する
   */
  private static Stream<Arguments> helloTestProvider() {
    // Arguments.arguments(queryString, expectedBody, dbPath)
    return Stream.of(Arguments.arguments("", """
        {
          "message": "Hello, world!"
        }
        """, "default"), Arguments.arguments("?name=techpit", """
        {
          "message": "Hello, techpit"
        }
        """, "techpit"));
  }

}
