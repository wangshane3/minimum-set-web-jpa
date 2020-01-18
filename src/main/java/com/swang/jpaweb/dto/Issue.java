package com.swang.jpaweb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Issue {
	private final String htmlUrl;
	private final int number;
	private final String title;

	@JsonCreator
	public Issue(@JsonProperty("html_url") String htmlUrl,
			@JsonProperty("number") int number,
			@JsonProperty("title") String title) {
		this.htmlUrl = htmlUrl;
		this.number = number;
		this.title = title;
	}
}