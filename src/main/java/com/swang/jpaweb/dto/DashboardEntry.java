package com.swang.jpaweb.dto;

import java.util.List;

import com.swang.jpaweb.model.GithubProject;

import lombok.Getter;

@Getter
public class DashboardEntry { // DTO
	private final GithubProject project;
	private final List<RepositoryEvent> events;

	public DashboardEntry(GithubProject project, List<RepositoryEvent> events) {
		this.project = project;
		this.events = events;
	}
}