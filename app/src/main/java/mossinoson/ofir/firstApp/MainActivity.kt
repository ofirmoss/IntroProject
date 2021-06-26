package mossinoson.ofir.firstApp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var passwordValidationEt: EditText
    private lateinit var genderRg: RadioGroup
    private lateinit var citySpinner: Spinner
    private lateinit var ageEt: EditText
    private lateinit var submitBtn: Button
    private var usersList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userNameEt = findViewById(R.id.user_name_et)
        emailEt = findViewById(R.id.email_et)
        passwordEt = findViewById(R.id.password_et)
        passwordValidationEt = findViewById(R.id.password_validation_et)
        genderRg = findViewById(R.id.gender_rg)
        citySpinner = findViewById(R.id.city_spinner)
        ageEt = findViewById(R.id.age_et)
        submitBtn = findViewById(R.id.submit_btn)

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
        val user = User(
            userNameEt.text.toString(),
            emailEt.text.toString(),
            passwordEt.text.toString(),
            if (genderRg.checkedRadioButtonId == R.id.male_rb) "male" else "female",
            citySpinner.selectedItem.toString(),
            ageEt.text.toString().toInt()
        )

        usersList.add(user)

        Intent(this, UserListActivity::class.java).also {
            it.putParcelableArrayListExtra("EXTRA_USER_LIST", ArrayList(usersList))
            startActivity(it)
        }
    }
}
