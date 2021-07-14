package mossinoson.ofir.firstApp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mossinoson.ofir.firstApp.data.local.entity.User
import mossinoson.ofir.firstApp.data.local.db.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
public abstract class UserDatebase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatebase? = null

        fun getDatabase(context: Context): UserDatebase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatebase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}