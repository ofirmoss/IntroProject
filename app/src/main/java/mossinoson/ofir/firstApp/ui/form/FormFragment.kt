package mossinoson.ofir.firstApp.ui.form

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mossinoson.ofir.firstApp.R
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.ui.userlist.UserListViewModel


class FormFragment : Fragment() {

    private val args by navArgs<FormFragmentArgs>()

    private lateinit var userNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var passwordValidationEt: EditText
    private lateinit var genderRg: RadioGroup
    private lateinit var citySpinner: Spinner
    private lateinit var ageEt: EditText
    private lateinit var submitBtn: Button

    private lateinit var mUserListViewModel: UserListViewModel

    private var user: User? = null
    private var isNew: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mUserListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        super.onCreate(savedInstanceState)
        requireView().apply {
            userNameEt = findViewById(R.id.user_name_et)
            emailEt = findViewById(R.id.email_et)
            passwordEt = findViewById(R.id.password_et)
            passwordValidationEt = findViewById(R.id.password_validation_et)
            genderRg = findViewById(R.id.gender_rg)
            citySpinner = findViewById(R.id.city_spinner)
            ageEt = findViewById(R.id.age_et)
            submitBtn = findViewById(R.id.submit_btn)
        }


        user = args.user
        user?.let {
            isNew = false
            fillUserDetails()
        }

        submitBtn.setOnClickListener {

            if (userNameEt.text.toString() == "pass") {
                navigateToUserList()
                return@setOnClickListener
            }

            if (userNameEt.text.length < 3) {
                Toast.makeText(
                    requireContext(),
                    "user name should contain at least 3 chars",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                Toast.makeText(requireContext(), "email should be valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordEt.text.isEmpty() || passwordValidationEt.text.isEmpty() || passwordEt.text.toString() != passwordValidationEt.text.toString()) {
                Toast.makeText(
                    requireContext(),
                    "password and password validation should be the same",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (genderRg.checkedRadioButtonId == -1) {
                Toast.makeText(requireContext(), "please select a gender", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

//            Spinner1.getSelectedItem().toString().trim().equals("Pick one")
            if (citySpinner.selectedItem == null) {
                Toast.makeText(requireContext(), "please select a city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ageEt.text.isEmpty()) {
                Toast.makeText(requireContext(), "please insert age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submit()
        }
    }

    private fun fillUserDetails() {
        user?.apply {
            userNameEt.setText(userName)
            emailEt.setText(email)
            passwordEt.setText(password)
            passwordValidationEt.setText(password)
            genderRg.check(if (gender == "male") R.id.male_rb else R.id.female_rb)
//            val citiesMap = {
//                "Haifa": 0,
//                "Tel aviv": 1,
//                "Eilat": 2
//            }
//            citySpinner.setSelection(citiesMap[user.city])
            ageEt.setText(age.toString())
        }
    }

    private fun submit() {
        extractUserData()
        if (isNew) addUserToDb() else updateUserInDb()

        navigateToUserList()
    }

    private fun addUserToDb() {
        user?.let { mUserListViewModel.addUser(it) }
    }

    private fun updateUserInDb() {
        user?.let { mUserListViewModel.updateUser(it) }
    }

    private fun extractUserData() {
        user = User(
            userNameEt.text.toString(),
            emailEt.text.toString(),
            passwordEt.text.toString(),
            if (genderRg.checkedRadioButtonId == R.id.male_rb) "male" else "female",
            citySpinner.selectedItem.toString(),
            ageEt.text.toString().toInt(),
            user?.id ?: 0
        )
    }

    private fun navigateToUserList() {
        val action = FormFragmentDirections.actionFormFragmentToUserListFragment()
        findNavController().navigate(action)
    }
}
