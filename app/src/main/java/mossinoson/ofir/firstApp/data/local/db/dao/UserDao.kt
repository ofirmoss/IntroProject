package mossinoson.ofir.firstApp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import mossinoson.ofir.firstApp.data.local.entity.User

@Dao
interface  UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>
}