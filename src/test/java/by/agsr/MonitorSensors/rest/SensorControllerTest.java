package by.agsr.MonitorSensors.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonFileReader jsonFileReader;

    @Test
    public void successCreateRequest() throws Exception {
        executePostRequestAndCompareResults(
                "rest/test_case_1/request.json",
                "rest/test_case_1/response.json"
        );
    }

    @Test
    public void sensorNameNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_2/request.json",
                "rest/test_case_2/response.json"
        );
    }

    @Test
    public void sensorNameNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_3/request.json",
                "rest/test_case_3/response.json"
        );
    }

    @Test
    public void sensorModelNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_4/request.json",
                "rest/test_case_4/response.json"
        );
    }

    @Test
    public void sensorModelNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_5/request.json",
                "rest/test_case_5/response.json"
        );
    }

    @Test
    public void sensorDescriptionNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_6/request.json",
                "rest/test_case_6/response.json"
        );
    }

    @Test
    public void sensorLocationNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_7/request.json",
                "rest/test_case_7/response.json"
        );
    }

    @Test
    public void sensorTypeEmpty() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_8/request.json",
                "rest/test_case_8/response.json"
        );
    }

    @Test
    public void sensorRangeNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_9/request.json",
                "rest/test_case_9/response.json"
        );
    }

    @Test
    public void sensorRangeFromFieldNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_10/request.json",
                "rest/test_case_10/response.json"
        );
    }

    @Test
    public void sensorRangeToFieldNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_11/request.json",
                "rest/test_case_11/response.json"
        );
    }

    @Test
    public void sensorRangeFromNotPositive() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_12/request.json",
                "rest/test_case_12/response.json"
        );
    }

    @Test
    public void sensorRangeToNotPositive() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_13/request.json",
                "rest/test_case_13/response.json"
        );
    }

    @Test
    public void sensorUnitNotExist() throws Exception {
        executePostRequestAndCompareResults(
                "rest/test_case_14/request.json",
                "rest/test_case_14/response.json"
        );
    }

    @Test
    public void sensorTypeNotExist() throws Exception {
        executePostRequestAndCompareResults(
                "rest/test_case_15/request.json",
                "rest/test_case_15/response.json"
        );
    }

    @Test
    public void sensorRangeNotExist() throws Exception {
        executePostRequestAndCompareResults(
                "rest/test_case_16/request.json",
                "rest/test_case_16/response.json"
        );
    }

    @Test
    public void sensorRangeIsNotCorrect() throws Exception {
        executePostRequestAndCompareResults(
                "rest/test_case_17/request.json",
                "rest/test_case_17/response.json"
        );
    }

    @Test
    public void getAllSensors() throws Exception {
        MvcResult result = mockMvc.perform(get("/agsr/monitor/sensors/"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile("rest/test_case_18/response.json");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(responseBodyContent), mapper.readTree(jsonResponse));
    }

    @Test
    public void deleteSensor() throws Exception {
        MvcResult result = mockMvc.perform(delete("/agsr/monitor/sensors/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("Sensor with id:1 deleted successfully!", responseBody);
    }


    private void executePostWithBadRequestAndCompareResults(String jsonRequestFilePath,
                                                            String jsonResponseFilePath) throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile(jsonRequestFilePath);
        MvcResult result = mockMvc.perform(post("/agsr/monitor/sensors/")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(responseBodyContent), mapper.readTree(jsonResponse));
    }

    private void executePostRequestAndCompareResults(String jsonRequestFilePath,
                                                     String jsonResponseFilePath) throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile(jsonRequestFilePath);
        MvcResult result = mockMvc.perform(post("/agsr/monitor/sensors/")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(responseBodyContent), mapper.readTree(jsonResponse));
    }

}
