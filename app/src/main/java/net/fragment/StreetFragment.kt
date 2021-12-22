package net.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import net.basicmodel.R
import net.event.MessageEvent
import net.utils.MapManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class StreetFragment : BaseFragment() {
    var sydeny = LatLng(-33.87365, 151.20689)
    var mStreetViewPanorama: StreetViewPanorama? = null
    var marker: Marker? = null
    var markerPosition: LatLng? = null
    override fun getLayout(): Int {
        return R.layout.fragment_street
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {
        markerPosition = MapManager.setMarkPosition(requireContext())
        val pf = (activity as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.streetviewpanorama) as SupportStreetViewPanoramaFragment?
        MapManager.getStreetViewPanoramaAsync(pf)
        val mf = requireActivity().supportFragmentManager.findFragmentById(R.id.mapstreet) as SupportMapFragment?
        mf?.let {
            MapManager.streetGetMapAsync(it, mStreetViewPanorama!!, markerPosition!!)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        when (msg[0]) {
            "marker" -> {
                marker = msg[1] as Marker?
            }
            "StreetViewPanorama" -> {
                mStreetViewPanorama = msg[1] as StreetViewPanorama
            }
            "StreetViewPanoramaLocation" -> {
                marker?.position = msg[1] as LatLng?
                mStreetViewPanorama?.setPosition(markerPosition)
            }
            "moveCamera" -> {
                val map: GoogleMap = msg[1] as GoogleMap
                map.moveCamera(
                    MapManager.getPosition(
                        markerPosition!!.latitude,
                        markerPosition!!.longitude
                    )
                )
            }
        }
    }
}