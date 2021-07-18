package mossinoson.ofir.firstApp.ui.form

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import mossinoson.ofir.firstApp.R
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.ui.userlist.UserListViewModel


class FormFragment : Fragment() {

    private val args by navArgs<FormFragmentArgs>()

    private lateinit var userNameTil: TextInputLayout
    private lateinit var emailTil: TextInputLayout
    private lateinit var passwordTil: TextInputLayout
    private lateinit var passwordValidationTil: TextInputLayout
    private lateinit var genderRg: RadioGroup
    private lateinit var citySpinner: Spinner
    private lateinit var ageTil: TextInputLayout
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().apply {
            userNameTil = findViewById(R.id.user_name_et)
            emailTil = findViewById(R.id.email_et)
            passwordTil = findViewById(R.id.password_et)
            passwordValidationTil = findViewById(R.id.password_validation_et)
            genderRg = findViewById(R.id.gender_rg)
            citySpinner = findViewById(R.id.city_spinner)
            ageTil = findViewById(R.id.age_et)
            submitBtn = findViewById(R.id.submit_btn)
        }


        user = args.user
        user?.let {
            isNew = false
            fillUserDetails()
        }

        userNameTil.editText?.doAfterTextChanged {
            userNameTil.error = null
        }

        emailTil.editText?.doAfterTextChanged {
            userNameTil.error = null
        }

        submitBtn.setOnClickListener {

            if (userNameTil.editText?.text.toString() == "pass") {
                navigateToUserList()
                return@setOnClickListener
            }

            if (userNameTil.editText?.text?.length ?: 0 < 3) {
                userNameTil.error = "user name should contain at least 3 chars"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailTil.editText?.text.toString()).matches()) {
                emailTil.error = "email should be valid"
                return@setOnClickListener
            }

            if (passwordTil.editText?.text?.isEmpty() == true || passwordValidationTil.editText?.text?.isEmpty() == true || passwordTil.editText?.text.toString() != passwordValidationTil.editText?.text.toString()) {
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

            if (ageTil.editText?.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "please insert age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submit()
        }
    }

    private fun fillUserDetails() {
        user?.apply {
            userNameTil.editText?.setText(userName)
            emailTil.editText?.setText(email)
            passwordTil.editText?.setText(password)
            passwordValidationTil.editText?.setText(password)
            genderRg.check(if (gender == "male") R.id.male_rb else R.id.female_rb)
//            val citiesMap = {
//                "Haifa": 0,
//                "Tel aviv": 1,
//                "Eilat": 2
//            }
//            citySpinner.setSelection(citiesMap[user.city])
            ageTil.editText?.setText(age.toString())
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
            userNameTil.editText?.text.toString(),
            emailTil.editText?.text.toString(),
            passwordTil.editText?.text.toString(),
            if (genderRg.checkedRadioButtonId == R.id.male_rb) "male" else "female",
            citySpinner.selectedItem.toString(),
            ageTil.editText?.text.toString().toInt(),
            user?.id ?: 0
        )
    }

    private fun navigateToUserList() {
        val action = FormFragmentDirections.actionFormFragmentToUserListFragment()
        findNavController().navigate(action)
    }
}
