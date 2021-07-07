package mossinoson.ofir.firstApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class UserListFragment : Fragment() {

    private lateinit var usersListRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val users = arguments?.let { UserListFragmentArgs.fromBundle(it).users } as List<User>

        usersListRv = requireView().findViewById(R.id.users_list_rv)
        usersListRv.adapter = UserAdapter(users)
    }
}