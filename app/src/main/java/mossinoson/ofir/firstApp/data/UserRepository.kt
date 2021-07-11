package mossinoson.ofir.firstApp.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun addUser(user: User) {
        userDao.addUser(user)
    }
}