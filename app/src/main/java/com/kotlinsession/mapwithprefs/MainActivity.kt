package com.kotlinsession.mapwithprefs

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kotlinsession.mapwithprefs.prefs.AppPreferences
import com.kotlinsession.mapwithprefs.prefs.PrefKeys
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Toast.makeText(
            applicationContext,
            AppPreferences.getInstance(this)?.getString(PrefKeys.ADDRESS),
            Toast.LENGTH_SHORT
        ).show()


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val dhaka = LatLng(23.7509, 90.3904)
        mMap.addMarker(MarkerOptions().position(dhaka).draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 13f))


        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker) {
                var latLng = marker.position as LatLng
                val geocoder = Geocoder(applicationContext, Locale.getDefault())


                val addressShort =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0]

                saveAddressToSharedPref(addressShort.getAddressLine(0))
            }

            override fun onMarkerDragStart(marker: Marker) {

            }

            override fun onMarkerDrag(marker: Marker) {

            }

        })


    }


    private fun saveAddressToSharedPref(address: String) {
        AppPreferences.getInstance(this)?.saveString(PrefKeys.ADDRESS, address)
    }
}
