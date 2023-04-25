package com.naito.techpit.chat.chatbachend.api;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
public class ChannelApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @ParameterizedTest
  @MethodSource("createTestProvider")
  public void createTest(String requestBody, String expectedBody, String dbPath) throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    var givenUrl = this.getClass().getResource("/channels/create/" + dbPath + "/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // Postリクエストの作成
    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(requestBody)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualChannelsTable = actualDataSet.getTable("channels");
    var expectedUri = this.getClass().getResource("/channels/create/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUri);
    var expectedChannelsTable = expectedDataSet.getTable("channels");
    Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);

  }


  @ParameterizedTest
  @MethodSource("updateTestProvider")
  public void updateTest(int id, String requestBody, String dbPath) throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    var givenUrl = this.getClass().getResource("/channels/update/" + dbPath + "/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // expectedBody:requestBodyに{id:1}を追加したもの
    var expectedBodyMapper = new ObjectMapper(); // ObjectMapper:jsonの参照/編集ができる
    var expectedNode = expectedBodyMapper.readTree(requestBody);
    ((ObjectNode) expectedNode).put("id", 1); // キャストしたObjectNodeの.putメソッドで(key:value)を追加
    var expectedBody = expectedNode.toString();

    mockMvc.perform(MockMvcRequestBuilders.put("/channels/" + id) // URLでidを指定
        .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals(expectedBody,
            result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualChannelsTable = actualDataSet.getTable("channels");
    var expectedUri = this.getClass().getResource("/channels/update/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUri);
    var expectedChannelsTable = expectedDataSet.getTable("channels");
    Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);

  }

  // no-record(事前DBレコードなし),またidの自動伝番のテストのため、idなし/あり両方をテスト
  // Arguments.arguments(String requestBody, String expectedBody, String dbPath)
  private static Stream<Arguments> createTestProvider() {
    return Stream.of(Arguments.arguments("""
        {
          "name": "はじめてのチャンネル"
        }
        """, """
        {
          "id": 1,
          "name": "はじめてのチャンネル"
        }
          """, "no-record"), Arguments.arguments("""
        {
          "name": "APIで追加するチャンネル"
        }
        """, """
        {
          "id": 3,
          "name": "APIで追加するチャンネル"
        }
          """, "multi-record"));
  }


  // Arguments.arguments(int id, String requestBody, String dbPath)
  private static Stream<Arguments> updateTestProvider() {
    // "channels/1"へのリクエスト
    return Stream.of(Arguments.arguments(1, """
        {
          "name": "このレコードを更新"
        }
        """, "success"));
  }
}
