package mossinoson.ofir.firstApp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun getDateStr(timestamp: Long): String = SimpleDateFormat( "MMM dd, yyyy", Locale.getDefault()).format(timestamp)
}