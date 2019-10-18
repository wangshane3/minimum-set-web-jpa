package com.swang.jpaweb;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.swang.jpaweb.dto.RepositoryEvent;
import com.swang.jpaweb.client.GithubClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(GithubClient.class)
@Import({MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class})
public class GithubClientTests {
	@Autowired
	private MockRestServiceServer mockRestServiceServer;
	@Autowired
	private GithubClient githubClient;

	@Test
	public void fetchEvents() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("X-RateLimit-Remaining", "42");
		this.mockRestServiceServer
				.expect(requestTo("https://api.github.com/repos/wangshane3/minimum-set-web-jpa/issues/events"))
				.andRespond(withSuccess(new ClassPathResource("events.json", getClass()), MediaType.APPLICATION_JSON)
						.headers(responseHeaders));

		ResponseEntity<RepositoryEvent[]> responseEntity = this.githubClient.fetchEvents("wangshane3", "minimum-set-web-jpa");
		List<RepositoryEvent> repositoryEvents = Arrays.asList(responseEntity.getBody());

		assertThat(repositoryEvents).hasSize(4);
		assertThat(repositoryEvents.get(0).getIssue().getTitle()).startsWith("Update README.md");
		assertThat(repositoryEvents.get(0).getActor().getLogin()).isEqualTo("wangshane3");
	}
}