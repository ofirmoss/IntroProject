package mossinoson.ofir.firstApp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mossinoson.ofir.firstApp.R

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePlace()
    }

    private fun initializePlace() {
//        val apiKey = ""
//        Places.initialize(applicationContext, apiKey)
//        PlacesClient placesClient = Places.createClient(this)
    }
}