package com.swang.jpaweb;

import com.swang.jpaweb.model.User;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
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
class RegistrationControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationControllerTest.class);
    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().getInterceptors()
                .add(new BasicAuthenticationInterceptor("admin", "admin"));
        headers = new HttpHeaders();
    }

    @Test
    void findAll() {
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/registration"), HttpMethod.GET,
                new HttpEntity<Void>(null, headers), String.class);
        logger.info("getAll endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("username"));
    }

    @Test
    void create() throws JSONException {
        final User user = new User("johnSmith", "password",
                "john", "smith", "johnsmith@gmail.com");
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/registration"), HttpMethod.POST,
                new HttpEntity<User>(user, headers), String.class);
        logger.info("create endpoint returned " + response.getBody());
        assertNotNull(response, "Response must be published JSON");
        assertTrue(response.getBody().contains("username"));
        final String expected = "{username:johnSmith,firstName:john,lastName:smith,email:johnsmith@gmail.com}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void return401() { // no/wrong login returns 401
        restTemplate.getRestTemplate().getInterceptors().clear();
        final ResponseEntity<String> response = restTemplate.exchange(
                createURL("/registration"), HttpMethod.GET,
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