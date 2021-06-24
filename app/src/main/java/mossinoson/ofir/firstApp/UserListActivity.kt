package mossinoson.ofir.firstApp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {
    private lateinit var userNameTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var passwordTv: TextView
    private lateinit var genderTv: TextView
    private lateinit var cityTv: TextView
    private lateinit var ageTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        userNameTv = findViewById(R.id.user_name_tv)
        emailTv = findViewById(R.id.email_tv)
        passwordTv = findViewById(R.id.password_tv)
        genderTv = findViewById(R.id.gender_tv)
        cityTv = findViewById(R.id.city_tv)
        ageTv = findViewById(R.id.age_tv)

        val userData = intent.getParcelableExtra<User>("EXTRA_USER")

        userNameTv.text = getString(R.string.user_name_category, userData?.userName)
        emailTv.text = getString(R.string.email_category, userData?.email)
        passwordTv.text = getString(R.string.password_category, userData?.password)
        genderTv.text = getString(R.string.gender_category, userData?.gender)
        cityTv.text = getString(R.string.city_category, userData?.city)
        ageTv.text = getString(R.string.age_category, userData?.age)
    }

}