package mossinoson.ofir.firstApp.ui.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mossinoson.ofir.firstApp.data.local.db.UserDatebase
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.data.repository.UserRepository

class UserListViewModel(application: Application) : AndroidViewModel(application) {
    val getAllUsers: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatebase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUsers = repository.getAllUsers
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

}