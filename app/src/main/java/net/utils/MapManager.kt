package net.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import net.basicmodel.R
import net.event.MessageEvent
import org.greenrobot.eventbus.EventBus

@SuppressLint("MissingPermission")
object MapManager {

    fun showErrorDlg(code: Int, activity: Activity) {
        GooglePlayServicesUtil.getErrorDialog(code, activity, 0).show()
    }


    fun getMapAsync(mapView: MapView, context: Context) {
        mapView.getMapAsync { p0 ->
            EventBus.getDefault().post(MessageEvent("map", p0))
            moveCamera(p0)
            p0?.let {
                it.setOnMyLocationButtonClickListener {
                    val locationManager: LocationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
                    if (location.latitude != 0.0 && location.longitude != 0.0) {
                        EventBus.getDefault()
                            .post(MessageEvent("location", location.latitude, location.longitude))
                    } else {
                        EventBus.getDefault().post(MessageEvent("location", 33.9204, -117.9465))
                    }
                    false
                }
            }
        }
    }

    fun setMap(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
    }

    fun setLocation(map: GoogleMap, lat: Double, lgt: Double) {
        map.animateCamera(getPosition(lat, lgt), 1000, null)
    }

    fun getPosition(la: Double, lo: Double): CameraUpdate {
        val position = CameraPosition.builder().target(LatLng(la, lo))
            .zoom(15.5f)
            .bearing(0f)
            .tilt(25f)
            .build()
        return CameraUpdateFactory.newCameraPosition(position)
    }

    fun moveCamera(map: GoogleMap) {
        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.73, -73.99)))
    }

    fun mapActivityResult(context: Context, data: Intent, mMap: GoogleMap) {
        val place = PlacePicker.getPlace(data, context)
        val toastMsg = String.format("Place: %s", place.name)
        Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show()
        mMap.addMarker(MarkerOptions().position(place.latLng).title("Marker in Sydney"))
        mMap.animateCamera(getPosition(place.latLng.latitude, place.latLng.longitude), 1000, null)
    }

    fun setMarkPosition(context: Context): LatLng {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        return if (location != null) {
            LatLng(location.latitude, location.longitude)
        } else {
            LatLng(-33.87365, 151.20689)
        }
    }

    fun getStreetViewPanoramaAsync(pf: SupportStreetViewPanoramaFragment?) {
        pf?.let {
            it.getStreetViewPanoramaAsync(object : OnStreetViewPanoramaReadyCallback {
                override fun onStreetViewPanoramaReady(p0: StreetViewPanorama?) {
                    EventBus.getDefault().post(MessageEvent("StreetViewPanorama", p0))
                    p0!!.setOnStreetViewPanoramaChangeListener(object :
                        StreetViewPanorama.OnStreetViewPanoramaChangeListener {
                        override fun onStreetViewPanoramaChange(p0: StreetViewPanoramaLocation?) {
                            EventBus.getDefault()
                                .post(MessageEvent("StreetViewPanoramaLocation", p0!!.position))
                        }
                    })
                }
            })
        }
    }

    fun streetGetMapAsync(mf: SupportMapFragment?, mStreetViewPanorama: StreetViewPanorama,
                          markerPosition: LatLng) {
        mf?.let {
            it.getMapAsync {googleMap->
                googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDragStart(marker: Marker) {}
                    override fun onMarkerDrag(marker: Marker) {}
                    override fun onMarkerDragEnd(marker: Marker) {
                        mStreetViewPanorama.setPosition(marker.position, 150)
                    }
                })
                googleMap.moveCamera(
                    getPosition(markerPosition.latitude, markerPosition.longitude)
                )
                val marker = googleMap.addMarker(
                    getMarkerOptions(
                        markerPosition.latitude,
                        markerPosition.longitude
                    )
                )
                EventBus.getDefault().post(MessageEvent("marker", marker))
                googleMap.setOnMapClickListener { latLng ->
                    googleMap.clear()
                    val marker =
                        googleMap.addMarker(
                            getMarkerOptions(latLng.latitude, latLng.longitude)
                        )
                    mStreetViewPanorama.setPosition(latLng)
                    EventBus.getDefault().post(MessageEvent("marker", marker))
                }
            }
        }
    }

    fun getMarkerOptions(la: Double, lo: Double): MarkerOptions {
        return MarkerOptions().position(LatLng(la, lo))
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)).draggable(true)
    }
}