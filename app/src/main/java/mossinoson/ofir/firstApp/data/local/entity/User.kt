package mossinoson.ofir.firstApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    val userName: String,
    val email: String,
    val password: String,
    val gender: String,
    val city: String,
    val age: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)