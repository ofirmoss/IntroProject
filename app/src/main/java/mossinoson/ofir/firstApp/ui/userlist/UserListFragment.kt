package mossinoson.ofir.firstApp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import mossinoson.ofir.firstApp.R
import mossinoson.ofir.firstApp.data.local.entity.User

class UserListFragment : Fragment() {

    private lateinit var usersListRv: RecyclerView
    private lateinit var mUserListViewModel: UserListViewModel
    private lateinit var addBtn: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        usersListRv = requireView().findViewById(R.id.users_list_rv)
        usersListRv.adapter = UserListAdapter().apply {
            onDeleteUserListener = object :UserListAdapter.OnDeleteUserListener{
                override fun onDeleteUser(user: User) {
                    mUserListViewModel.deleteUser(user)
                }
            }
        }

        mUserListViewModel.getAllUsers.observe(viewLifecycleOwner, { users ->
            (usersListRv.adapter as UserListAdapter).setData(users)
        })

        addBtn = requireView().findViewById(R.id.add_btn)
        addBtn.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToFormFragment()
            findNavController().navigate(action)
        }
    }
}