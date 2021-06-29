package mossinoson.ofir.firstApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {

    private lateinit var usersListRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val userDataList = intent.getParcelableArrayListExtra<User>("EXTRA_USER_LIST")

        usersListRv = findViewById(R.id.users_list_rv)
        userDataList?.let { users ->
            usersListRv.adapter = UserAdapter(users)
        }
    }
}