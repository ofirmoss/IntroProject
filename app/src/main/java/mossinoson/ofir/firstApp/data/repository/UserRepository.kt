package mossinoson.ofir.firstApp.data.repository

import androidx.lifecycle.LiveData
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.data.local.db.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun addUser(user: User) {
        userDao.addUser(user)
    }
}