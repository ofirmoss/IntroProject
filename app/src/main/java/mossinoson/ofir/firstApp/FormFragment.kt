package mossinoson.ofir.firstApp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class FormFragment : Fragment() {

    private lateinit var userNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var passwordValidationEt: EditText
    private lateinit var genderRg: RadioGroup
    private lateinit var citySpinner: Spinner
    private lateinit var ageEt: EditText
    private lateinit var submitBtn: Button
    private var usersList = Users()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

        submitBtn.setOnClickListener {
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
                Toast.makeText(requireContext(), "please select a gender", Toast.LENGTH_SHORT).show()
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

//        val bundle = bundleOf("users" to usersList)
//        findNavController().navigate(R.id.userListFragment, bundle)
        val action = FormFragmentDirections.actionFormFragmentToUserListFragment(usersList)
        findNavController().navigate(action)

//        Intent(this, UserListActivity::class.java).also {
//            it.putParcelableArrayListExtra("EXTRA_USER_LIST", ArrayList(usersList))
//            startActivity(it)
//        }

    }
}
