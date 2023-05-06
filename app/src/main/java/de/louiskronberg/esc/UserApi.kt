package de.louiskronberg.esc

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UserApi {
    data class UserBody(val name: String)

    companion object {
        suspend fun getUser(apiUrl: String, idToken: String): UserBody? {
            val url = "$apiUrl/user"
            val client = HttpClient(CIO)
            val result = client.get(url) {
                contentType(ContentType.Application.Json)
                headers {
                    append("Id-Token", idToken)
                }
            }

            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                return null
            }

            val userResponse = Gson().fromJson(result.bodyAsText(), UserBody::class.java)
            Log.i("RESPONSE", userResponse.toString())
            return userResponse
        }

        suspend fun createUser(apiUrl: String, name: String, idToken: String) {
            val url = "$apiUrl/user"
            val body = Gson().toJson(UserBody(name))
            val client = HttpClient(CIO)
            val result = client.post(url) {
                contentType(ContentType.Application.Json)
                headers {
                    append("Id-Token", idToken)
                }
                setBody(body)
            }
            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                return
            }
        }
    }
}