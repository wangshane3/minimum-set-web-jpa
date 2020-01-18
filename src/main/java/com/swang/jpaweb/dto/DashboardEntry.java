package com.swang.jpaweb.dto;

import com.swang.jpaweb.model.GithubProject;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardEntry { // DTO
	private final GithubProject project;
	private final List<RepositoryEvent> events;

	public DashboardEntry(GithubProject project, List<RepositoryEvent> events) {
		this.project = project;
		this.events = events;
	}
}