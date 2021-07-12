package mossinoson.ofir.firstApp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mossinoson.ofir.firstApp.R

class UserListFragment : Fragment() {

    private lateinit var usersListRv: RecyclerView
    private lateinit var mUserListViewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        usersListRv = requireView().findViewById(R.id.users_list_rv)
        usersListRv.adapter = UserListAdapter()

        mUserListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        mUserListViewModel.getAllUsers.observe(viewLifecycleOwner, { users ->
            (usersListRv.adapter as UserListAdapter).setData(users)
        })
    }
}