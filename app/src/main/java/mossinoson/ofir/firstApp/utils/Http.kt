package mossinoson.ofir.firstApp.utils

import android.util.Log
import okhttp3.*
import java.io.IOException

object Http {

    private val client = OkHttpClient()

    fun get(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ofir", e.message ?: "")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("ofir", response.body()?.string() ?: "")
            }
        })
    }

}