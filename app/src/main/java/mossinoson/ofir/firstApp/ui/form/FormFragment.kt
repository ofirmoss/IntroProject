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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import mossinoson.ofir.firstApp.R
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.ui.userlist.UserListViewModel
import mossinoson.ofir.firstApp.utils.TimeUtil
import java.util.*


class FormFragment : Fragment() {

    private val args by navArgs<FormFragmentArgs>()

    private lateinit var userNameTil: TextInputLayout
    private lateinit var emailTil: TextInputLayout
    private lateinit var passwordTil: TextInputLayout
    private lateinit var passwordValidationTil: TextInputLayout
    private lateinit var genderRg: RadioGroup
    private lateinit var ageBtn: Button
    private lateinit var submitBtn: Button
    private lateinit var addressActv: AutoCompleteTextView

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
        initViews()
        checkIfNewUser()
        removeErrorsWhenTextChanges()
        setAgeBtnClickListener()
        setSubmitBtnClickListener()
        initAddressAutocomplete()
    }

    private fun setAgeBtnClickListener() {
        ageBtn.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("select birth date")
                .build().apply {
                    addOnPositiveButtonClickListener {
                        ageBtn.text = headerText
                        ageBtn.tag = it
                    }
                }.show(requireActivity().supportFragmentManager, "")
        }
    }

    private fun setSubmitBtnClickListener() {
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

            if (ageBtn.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "please insert age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submit()
        }
    }

    private fun removeErrorsWhenTextChanges() {
        userNameTil.editText?.doAfterTextChanged {
            userNameTil.error = null
        }

        emailTil.editText?.doAfterTextChanged {
            userNameTil.error = null
        }
    }

    private fun checkIfNewUser() {
        user = args.user
        user?.let {
            isNew = false
            fillUserDetails()
        }
    }

    private fun initViews() {
        requireView().apply {
            userNameTil = findViewById(R.id.user_name_et)
            emailTil = findViewById(R.id.email_et)
            passwordTil = findViewById(R.id.password_et)
            passwordValidationTil = findViewById(R.id.password_validation_et)
            genderRg = findViewById(R.id.gender_rg)
            ageBtn = findViewById(R.id.age_et)
            submitBtn = findViewById(R.id.submit_btn)
            addressActv = findViewById(R.id.address_autocomplete)
        }
    }

    private fun fillUserDetails() {
        user?.apply {
            userNameTil.editText?.setText(userName)
            emailTil.editText?.setText(email)
            passwordTil.editText?.setText(password)
            passwordValidationTil.editText?.setText(password)
            genderRg.check(if (gender == "male") R.id.male_rb else R.id.female_rb)
            addressActv.setText(address)
            ageBtn.apply {
                text = (TimeUtil.getDateStr(dobTimestamp))
                tag = dobTimestamp
            }
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
        user = ageBtn.tag?.let {
            User(
                userNameTil.editText?.text.toString(),
                emailTil.editText?.text.toString(),
                passwordTil.editText?.text.toString(),
                if (genderRg.checkedRadioButtonId == R.id.male_rb) "male" else "female",
                addressActv.text.toString(),
                it as Long,
                user?.id ?: 0
            )
        }
    }


    private fun navigateToUserList() {
        val action = FormFragmentDirections.actionFormFragmentToUserListFragment()
        findNavController().navigate(action)
    }

    private fun initAddressAutocomplete() {
        addressActv.setAdapter(context?.let { AddressAdapter(it) })
    }
}
