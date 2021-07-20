package mossinoson.ofir.firstApp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    val userName: String,
    val email: String,
    val password: String,
    val gender: String,
    val city: String,
    val age: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): Parcelable