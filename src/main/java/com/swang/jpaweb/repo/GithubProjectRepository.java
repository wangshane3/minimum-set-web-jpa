package com.swang.jpaweb.repo;

import com.swang.jpaweb.model.GithubProject;
import org.springframework.data.repository.CrudRepository;

public interface GithubProjectRepository extends CrudRepository<GithubProject, Long> {
	GithubProject findByRepoName(String repoName);
}