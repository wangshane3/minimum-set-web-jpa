package com.swang.jpaweb.repo;

import org.springframework.data.repository.CrudRepository;

import com.swang.jpaweb.model.GithubProject;

public interface GithubProjectRepository extends CrudRepository<GithubProject, Long> {
	GithubProject findByRepoName(String repoName);
}