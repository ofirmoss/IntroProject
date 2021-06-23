package mossinoson.ofir.firstApp

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var userNameEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userNameEt = findViewById<EditText>(R.id.user_name_et)
        val emailEt = findViewById<EditText>(R.id.email_et)
        val passwordEt = findViewById<EditText>(R.id.password_et)
        val passwordValidationEt = findViewById<EditText>(R.id.password_validation_et)
        val genderRg = findViewById<RadioGroup>(R.id.gender_rg)
        val citySpinner = findViewById<Spinner>(R.id.city_spinner)
        val ageEt = findViewById<EditText>(R.id.age_et)
        val submitBtn: Button = findViewById(R.id.submit_btn)

        submitBtn.setOnClickListener {
            if (userNameEt.text.length < 3) {
                Toast.makeText(
                    this,
                    "user name should contain at least 3 chars",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                Toast.makeText(this, "email should be valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordEt.text.isEmpty() || passwordValidationEt.text.isEmpty() || passwordEt.text.toString() != passwordValidationEt.text.toString()) {
                Toast.makeText(
                    this,
                    "password and password validation should be the same",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (genderRg.checkedRadioButtonId == -1) {
                Toast.makeText(this, "please select a gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            Spinner1.getSelectedItem().toString().trim().equals("Pick one")
            if (citySpinner.selectedItem == null) {
                Toast.makeText(this, "please select a city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ageEt.text.isEmpty()) {
                Toast.makeText(this, "please insert age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            submit()
        }
    }

    private fun submit() {
        var user = User("ofir", "mossinson", "asda")
        user.email = "sd"
        Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show()
    }
}
