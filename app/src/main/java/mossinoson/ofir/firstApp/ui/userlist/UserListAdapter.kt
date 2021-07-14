package mossinoson.ofir.firstApp.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import mossinoson.ofir.firstApp.R
import mossinoson.ofir.firstApp.data.local.entity.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private var users = emptyList<User>()

    var onDeleteUserListener: OnDeleteUserListener?=null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val user_name_tv: TextView = itemView.findViewById(R.id.user_name_tv)
        val email_tv: TextView = itemView.findViewById(R.id.email_tv)
        val gender_tv: TextView = itemView.findViewById(R.id.gender_tv)
        val city_tv: TextView = itemView.findViewById(R.id.city_tv)
        val age_tv: TextView = itemView.findViewById(R.id.age_tv)
        val delete_btn: ImageButton = itemView.findViewById(R.id.delete_btn)
        val edit_btn: ImageButton = itemView.findViewById(R.id.edit_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = users[position]

        with(holder) {
            user_name_tv.text = users[position].userName
            email_tv.text = users[position].email
            gender_tv.text = users[position].gender
            city_tv.text = users[position].city
            age_tv.text = users[position].age.toString()
        }

        holder.edit_btn.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToFormFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.delete_btn.setOnClickListener {
            onDeleteUserListener?.onDeleteUser(currentItem)
        }
    }

    override fun getItemCount(): Int {
       return users.size
    }

    fun setData(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    interface OnDeleteUserListener {
        fun onDeleteUser(user: User)
    }
}
