package mossinoson.ofir.firstApp.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mossinoson.ofir.firstApp.R
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val args by navArgs<MapsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        val addressLatLng = getLatLngFromAddress(args.address)
        googleMap.apply {
            addMarker(MarkerOptions().position(addressLatLng).title("Marker in ${args.address}"))
            animateCamera(CameraUpdateFactory.newLatLngZoom(addressLatLng, 17f))
        }
    }

    private fun getLatLngFromAddress(address: String) =
        with(Geocoder(context, Locale.getDefault()).getFromLocationName(address, 1)) {
            if (isNotEmpty()) LatLng(this[0].latitude, this[0].longitude) else LatLng(0.0, 0.0)
        }
}