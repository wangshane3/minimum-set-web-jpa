package com.swang.jpaweb;

import com.swang.jpaweb.dto.JoggingEntry;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpawebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntryControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(EntryControllerTest.class);
    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().getInterceptors()
                .add(new BasicAuthenticationInterceptor("user", "password"));
        headers = new HttpHeaders();
    }

    @Test @Order(1)
    void findAll() {
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/api/jogging"), HttpMethod.GET,
                new HttpEntity<Void>(null, headers), String.class);
        logger.info("getAll endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("date"));
    }

    @Test @Order(2)
    void findOne() throws JSONException {
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/api/jogging/2"), HttpMethod.GET,
                new HttpEntity<Void>(null, headers), String.class);
        logger.info("getOne endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("date"));
        final String expected = "{duration:562,distance:12342,location:'London,uk'}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test @Order(3)
    void report() throws JSONException {
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/api/jogging/report"), HttpMethod.GET,
                new HttpEntity<Void>(null, headers), String.class);
        logger.info("get report endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("speed"));
        final String expected = "[{week:1,speed:21,dailyDistance:12342},{week:2,speed:468,dailyDistance:13579}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test @Order(4)
    void create() throws JSONException {
        JoggingEntry entry = new JoggingEntry(29, 13579, "London,uk");
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/api/jogging"), HttpMethod.POST,
                new HttpEntity<JoggingEntry>(entry, headers), String.class);
        logger.info("create endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("date"));
        final String expected = "{duration:29,distance:13579,location:'London,uk'}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test @Order(5)
    void return401() { // no/wrong login returns 401
        restTemplate.getRestTemplate().getInterceptors().clear();
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/api/jogging"), HttpMethod.GET,
                new HttpEntity<Void>(null, headers), String.class);
        logger.info("getAll without login endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertEquals(response.getStatusCodeValue(), 401);
        assertTrue(response.getBody().contains("error\":\"Unauthorized"));
    }

    private String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}