package de.louiskronberg.esc2025

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class Api {
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

            if (result.status == HttpStatusCode.NotFound) {
                return null
            }

            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                return null
            }

            val userResponse = Gson().fromJson(result.bodyAsText(), UserBody::class.java)
            Log.i("RESPONSE", userResponse.toString())
            return userResponse
        }

        suspend fun createUser(view: View, apiUrl: String, name: String, idToken: String) {
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
                showError(view, result)
                return
            }
        }

        data class RankingResponse(val countries: List<String>)

        suspend fun getRanking(view: View, apiUrl: String, idToken: String): List<String> {
            val url = "$apiUrl/ranking"
            val client = HttpClient(CIO)
            val result = client.get(url) {
                headers {
                    append("Id-Token", idToken)
                }
            }

            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                showError(view, result)
                return emptyList()
            }

            val ranking = Gson().fromJson(result.bodyAsText(), RankingResponse::class.java)
            Log.i("RESPONSE", ranking.toString())
            return ranking.countries
        }

        suspend fun saveRanking(
            view: View,
            apiUrl: String,
            idToken: String,
            ranking: RankingResponse
        ) {
            val url = "$apiUrl/ranking"
            val body = Gson().toJson(ranking)
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
                showError(view, result)
                return
            }
        }

        data class LockResponse(val lock: Boolean)

        suspend fun getLock(view: View, apiUrl: String, idToken: String): Boolean {
            val url = "$apiUrl/lock"

            val client = HttpClient(CIO)
            val result = client.get(url) {
                headers {
                    append("Id-Token", idToken)
                }
            }

            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                showError(view, result)
                return true
            }

            val lockResponse = Gson().fromJson(result.bodyAsText(), LockResponse::class.java)
            Log.i("RESPONSE", lockResponse.toString())
            return lockResponse.lock
        }

        data class ScoreResponse(val score: Int, val detailed: Map<String, Int>)

        suspend fun getScore(view: View, apiUrl: String, idToken: String): ScoreResponse? {
            val url = "$apiUrl/score"
            val client = HttpClient(CIO)
            val result = client.get(url) {
                headers {
                    append("Id-Token", idToken)
                }
            }

            if (result.status == HttpStatusCode.NotFound) {
                Log.i("RESPONSE", "NO SCORE")
                return null
            }

            if (result.status != HttpStatusCode.OK) {
                Log.i("ERROR", result.toString())
                showError(view, result)
                return null
            }

            val scoreResponse = Gson().fromJson(result.bodyAsText(), ScoreResponse::class.java)
            Log.i("RESPONSE", scoreResponse.toString())
            return scoreResponse
        }

        private fun showError(view: View, response: HttpResponse) {
            Snackbar.make(view, response.status.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
}