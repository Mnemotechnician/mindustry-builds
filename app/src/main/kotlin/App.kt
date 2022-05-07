package com.github.mnemotechnician.mbuilds

import java.io.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.kotlinx.json.*

val baseUrl = "https://api.github.com"
val userName = "anuken"
val repoName = "mindustry"
val branchName = "erekir"

val outputFile = "LAST_UPDATE"
val resultFile = "RESULT"

val json = Json {
	prettyPrint = true
	ignoreUnknownKeys = true
}

val client = HttpClient(CIO) {
	install(ContentNegotiation) {
		json(json)
	}
}

suspend fun main() {
	val previousInfo = try {
		val text = File(outputFile).readText()
		Json.decodeFromString<LastCommitInfo>(text)
	} catch (e: Exception) {
		println(e)
		null
	}

	val url = getCommitUrl(userName, repoName, branchName)
	val info: LastCommitInfo = client.get(url).body()

	val success = previousInfo == null || previousInfo.sha != info.sha
	if (success) {
		File(outputFile).writeText(json.encodeToString(info))
	}

	print("::set-output name=SUCCESS::$success")
}

fun getRepoUrl(user: String, repo: String) = "$baseUrl/repos/$user/$repo"

fun getCommitUrl(user: String, repo: String, ref: String) = "${getRepoUrl(user, repo)}/commits/$ref"
