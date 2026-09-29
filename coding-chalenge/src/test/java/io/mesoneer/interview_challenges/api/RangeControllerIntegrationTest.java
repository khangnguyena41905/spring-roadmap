package io.mesoneer.interview_challenges.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class RangeControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  private ResultActions postContains(String body) throws Exception {
    return mockMvc.perform(post("/api/range/contains")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body));
  }

  private String request(String type, String range, String value) {
    return """
        {"type": "%s", "range": "%s", "value": "%s"}
        """.formatted(type, range, value);
  }

  @Test
  public void should_return_true__when_integer_range_contains_value() throws Exception {
    postContains(request("INTEGER", "[1, 5)", "3"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.contains").value(true));
  }

  @Test
  public void should_return_false__when_integer_range_does_not_contain_value() throws Exception {
    postContains(request("INTEGER", "[1, 5)", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contains").value(false));
  }

  @Test
  public void should_support_open_ended_range() throws Exception {
    postContains(request("INTEGER", "[Infinity, 100)", "-9000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contains").value(true));
  }

  @Test
  public void should_support_decimal_range() throws Exception {
    postContains(request("DECIMAL", "(1.5, 2.5]", "2.50"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contains").value(true));
  }

  @Test
  public void should_support_date_range() throws Exception {
    postContains(request("DATE", "[2020-01-01, 2020-12-31]", "2021-01-01"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contains").value(false));
  }

  @Test
  public void should_support_string_range() throws Exception {
    postContains(request("STRING", "(abc, xyz)", "mno"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contains").value(true));
  }

  @Test
  public void should_return_bad_request__when_range_has_invalid_format() throws Exception {
    postContains(request("INTEGER", "[1, 5", "3"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("Invalid range format: [1, 5"));
  }

  @Test
  public void should_return_bad_request__when_lowerbound_is_bigger_than_upperbound() throws Exception {
    postContains(request("INTEGER", "[5, 1]", "3"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("Lower bound must be less than or equal to upper bound."));
  }

  @Test
  public void should_return_bad_request__when_value_does_not_match_type() throws Exception {
    postContains(request("INTEGER", "[1, 5]", "abc"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("Invalid value: abc"));
    postContains(request("DATE", "[2020-01-01, 2020-12-31]", "2020-13-01"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("Invalid value: 2020-13-01"));
  }

  @Test
  public void should_return_bad_request__when_type_is_not_supported() throws Exception {
    postContains(request("COLOR", "[1, 5]", "3"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void should_return_bad_request__when_a_field_is_missing() throws Exception {
    postContains("""
        {"type": "INTEGER", "value": "3"}
        """)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.detail").value("Fields 'type', 'range' and 'value' are required."));
  }

  @Test
  public void should_return_bad_request__when_body_is_malformed() throws Exception {
    postContains("{not json")
        .andExpect(status().isBadRequest());
  }

  @Test
  public void should_expose_openapi_specification() throws Exception {
    mockMvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/range/contains'].post").exists());
  }

}
