package com.swang.jpaweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity // DAO
public class GithubProject {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private String orgName;

	@Column(nullable = false, unique = true)
	private String repoName;
}