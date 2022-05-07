package com.github.mnemotechnician.mbuilds

import kotlinx.serialization.*

@Serializable
data class LastCommitInfo(
	val sha: String,
	@SerialName("node_id")
	val nodeId: String,

	val commit: CommitInfo,

	val url: String,
	@SerialName("html_url")
	val htmlUrl: String,
	@SerialName("comments_url")
	val commentsUrl: String
)

@Serializable
data class CommitInfo(
	val author: AuthorInfo,
	val committer: AuthorInfo,

	val message: String,
	val url: String,

	@SerialName("comment_count")
	val commentCount: Int
)

@Serializable
data class AuthorInfo(
	val name: String,
	val email: String,
	val date: String
)
