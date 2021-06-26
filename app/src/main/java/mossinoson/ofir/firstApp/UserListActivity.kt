package mossinoson.ofir.firstApp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {
    private lateinit var userNameTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var passwordTv: TextView
    private lateinit var genderTv: TextView
    private lateinit var cityTv: TextView
    private lateinit var ageTv: TextView

    private lateinit var adapter: UserAdapter
    private lateinit var usersListRv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val userDataList = intent.getParcelableArrayListExtra<User>("EXTRA_USER_LIST")

        usersListRv = findViewById(R.id.users_list_rv)
        userDataList?.let { users ->
            setUserListAdapter(users)
        }
    }

    private fun setUserListAdapter(userDataList: ArrayList<User>) {
        adapter = UserAdapter(userDataList)
        usersListRv.adapter = adapter
        usersListRv.layoutManager = LinearLayoutManager(this)
    }
}