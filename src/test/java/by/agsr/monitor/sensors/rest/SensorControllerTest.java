package by.agsr.monitor.sensors.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonFileReader jsonFileReader;

    @Test
    @WithMockUser(roles = "Administrator")
    @Transactional
    public void successCreateRequest() throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile("rest/test_case_1/request.json");
        MvcResult result = mockMvc.perform(post("/agsr/api/sensors")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile("rest/test_case_1/response.json");

        assertJson(responseBodyContent)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(jsonResponse);
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorNameNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_2/request.json",
                "rest/test_case_2/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorNameNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_3/request.json",
                "rest/test_case_3/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorModelNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_4/request.json",
                "rest/test_case_4/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorModelNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_5/request.json",
                "rest/test_case_5/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorDescriptionNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_6/request.json",
                "rest/test_case_6/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorLocationNoFitSize() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_7/request.json",
                "rest/test_case_7/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorTypeEmpty() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_8/request.json",
                "rest/test_case_8/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_9/request.json",
                "rest/test_case_9/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeFromFieldNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_10/request.json",
                "rest/test_case_10/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeToFieldNull() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_11/request.json",
                "rest/test_case_11/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeFromNotPositive() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_12/request.json",
                "rest/test_case_12/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeToNotPositive() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_13/request.json",
                "rest/test_case_13/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorUnitNotExist() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_14/request.json",
                "rest/test_case_14/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorTypeNotExist() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_15/request.json",
                "rest/test_case_15/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeNotExist() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_16/request.json",
                "rest/test_case_16/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void sensorRangeIsNotCorrect() throws Exception {
        executePostWithBadRequestAndCompareResults(
                "rest/test_case_17/request.json",
                "rest/test_case_17/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Viewer")
    public void getAllSensors() throws Exception {
        executeGetWithOkCodeAndCompareResults("",
                "rest/test_case_18/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Viewer")
    public void getSensorsByName() throws Exception {
        executeGetWithOkCodeAndCompareResults("/search/byName?name=meter",
                "rest/test_case_19/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Viewer")
    public void getEmptyListSensorsWhenNoFoundName() throws Exception{
        executeGetWithOkCodeAndCompareResults("/search/byName?name=notFoundName",
                "rest/test_case_20/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void getAllSensorsWhenEmptyName() throws Exception{
        executeGetWithOkCodeAndCompareResults("/search/byName?name=",
                "rest/test_case_21/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void getSensorsByModel() throws Exception {
        executeGetWithOkCodeAndCompareResults("/search/byModel?model=ac",
                "rest/test_case_22/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void getEmptyListSensorsWhenNoFoundModel() throws Exception{
        executeGetWithOkCodeAndCompareResults("/search/byModel?model=nonFoundModel",
                "rest/test_case_23/response.json"
        );
    }

    @Test
    @WithMockUser(roles = "Viewer")
    public void getAllSensorsWhenEmptyModel() throws Exception{
        executeGetWithOkCodeAndCompareResults("/search/byModel?model=",
                "rest/test_case_24/response.json"
        );
    }

    @Test
    @Transactional
    @WithMockUser(roles = "Administrator")
    public void deleteSensor() throws Exception {
        MvcResult result = mockMvc.perform(delete("/agsr/api/sensors/1"))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("", responseBody);
    }

    private void executeGetWithOkCodeAndCompareResults(String url,
                                                       String jsonResponseFilePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/agsr/api/sensors" + url))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        assertJson(jsonResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(responseBodyContent);
    }

    private void executePostWithBadRequestAndCompareResults(String jsonRequestFilePath,
                                                            String jsonResponseFilePath) throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile(jsonRequestFilePath);
        MvcResult result = mockMvc.perform(post("/agsr/api/sensors")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();
        String jsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        assertJson(jsonResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(responseBodyContent);
    }
}