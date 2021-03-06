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
import mossinoson.ofir.firstApp.utils.TimeUtil

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private var users = emptyList<User>()

    var onDeleteUserListener: OnDeleteUserListener? = null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTv: TextView = itemView.findViewById(R.id.user_name_tv)
        val emailTv: TextView = itemView.findViewById(R.id.email_tv)
        val genderTv: TextView = itemView.findViewById(R.id.gender_tv)
        val addressTv: TextView = itemView.findViewById(R.id.address_tv)
        val ageTv: TextView = itemView.findViewById(R.id.age_tv)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.delete_btn)
        val editBtn: ImageButton = itemView.findViewById(R.id.edit_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = users[position]

        with(holder) {
            users[position].apply {
                userNameTv.text = userName
                emailTv.text = email
                genderTv.text = gender
                addressTv.text = address
                ageTv.text = TimeUtil.getDateStr(dobTimestamp)
            }
        }

        holder.editBtn.setOnClickListener {
            val action =
                UserListFragmentDirections.actionUserListFragmentToFormFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.addressTv.setOnClickListener {
            val action =
                UserListFragmentDirections.actionUserListFragmentToMapsFragment(currentItem.address)
            holder.itemView.findNavController().navigate(action)
        }

        holder.deleteBtn.setOnClickListener {
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
