package com.naito.techpit.chat.chatbachend.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ChannelAPIへのリクエストとレスポンス、DB処理に対するテスト")
public class ChannelApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @Test
  public void createTest() throws Exception {
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    var givenUrl = this.getClass().getResource("/channels/findAll/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    var expectedBody = """
        [
          {
            "id": 1,
            "name": "findAllのテスト"
          },
          {
            "id": 2,
            "name": "2つ目のチャンネル"
          },
                  {
            "id": 3,
            "name": "全件所得用データ"
          }
        ]
          """;

    // Postリクエストの作成
    mockMvc
        .perform(MockMvcRequestBuilders.get("/channels").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));


  }

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

  @Test
  public void updateTest() throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    var givenUrl = this.getClass().getResource("/channels/update/success/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    mockMvc.perform(MockMvcRequestBuilders.put("/channels/" + 1).content("""
        {
          "name" : "updateしたレコード"
        }
        """).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals("""
            {
              "id" : 1,
              "name" : "updateしたレコード"
            }
            """, result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualChannelsTable = actualDataSet.getTable("channels");
    var expectedUri = this.getClass().getResource("/channels/update/success/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUri);
    var expectedChannelsTable = expectedDataSet.getTable("channels");
    Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);

  }

  @Test
  public void updateFailedTest() throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    var givenUrl = this.getClass().getResource("/channels/update/success/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    mockMvc.perform(MockMvcRequestBuilders.put("/channels/" + 1).content("""
        {
          "name" : "updateしたレコード"
        }
        """).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals("""
            {
              "id" : 1,
              "name" : "updateしたレコード"
            }
            """, result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualChannelsTable = actualDataSet.getTable("channels");
    var expectedUri = this.getClass().getResource("/channels/update/success/expected/");
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
