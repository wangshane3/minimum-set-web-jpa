package com.swang.jpaweb.model;

import lombok.Data;

import javax.persistence.*;

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