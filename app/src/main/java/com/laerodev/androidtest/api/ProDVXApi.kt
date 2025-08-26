package com.laerodev.androidtest.api

import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

private const val protocol = "http://"
private const val host = "localhost"
private const val port = "3535"
private const val version = "v1"

private const val baseRequest = "${protocol}${host}:${port}/${version}"

private const val defaultToken = "MEYCIQCeykYnEnmf3pH0KmmwR5qxLI6H4b7ZgqKLlTZWK1xVJgIhAO6C1CuS1Fm5RC26c99u62bJAAoAEXKp1_m10HbvWRyQ"
private const val extraToken = ""
private const val token = defaultToken

suspend fun sendRequest(method: HttpMethod, endpoint: String, params: Map<String, String>? = null): HttpResponse? {
    try {
        val url = buildString {
            append("${baseRequest}$endpoint")
            params?.let {
                append("?")
                append(it.entries.joinToString("&") {entry -> "${entry.key}=${entry.value}" })
            }
        }
        val response: HttpResponse = httpClient.request(url) {
            this.method = method
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        return response

    } catch (e: Exception){
        "Error: ${e.message}"
        return null
    }
}

suspend fun sleepDevice(): HttpResponse? {
    return sendRequest(HttpMethod.Get, "/sleepDevice")
}