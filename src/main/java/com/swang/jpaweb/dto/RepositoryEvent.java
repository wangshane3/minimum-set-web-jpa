package com.swang.jpaweb.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class RepositoryEvent {
	private final Type type;
	private final OffsetDateTime creationTime;
	private final Actor actor;
	private final Issue issue;

	@JsonCreator
	public RepositoryEvent(@JsonProperty("event") String type,
			@JsonProperty("created_at") OffsetDateTime creationTime,
			@JsonProperty("actor") Actor actor,
			@JsonProperty("issue") Issue issue) {
		this.type = Type.valueOf(type.toUpperCase());
		this.creationTime = creationTime;
		this.actor = actor;
		this.issue = issue;
	}

	public enum Type {
		CLOSED,
		REOPENED,
		SUBSCRIBED,
		UNSUBSCRIBED,
		MERGED,
		REFERENCED,
		MENTIONED,
		ASSIGNED,
		UNASSIGNED,
		LABELED,
		UNLABELED,
		MILESTONED,
		DEMILESTONED,
		RENAMED,
		LOCKED,
		UNLOCKED,
		HEAD_REF_DELETED,
		HEAD_REF_RESTORED,
		CONVERTED_NOTE_TO_ISSUE,
		MOVED_COLUMNS_IN_PROJECT,
		COMMENT_DELETED,
		REVIEW_REQUESTED,
		HEAD_REF_FORCE_PUSHED,
		BASE_REF_FORCE_PUSHED;
	}
}