package mossinoson.ofir.firstApp.ui.form

import android.content.Context
import android.widget.ArrayAdapter
import mossinoson.ofir.firstApp.R

class AddressAdapter(context: Context) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, context.resources.getStringArray(R.array.addresses)) {
}