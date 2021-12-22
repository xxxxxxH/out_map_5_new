package net.basicmodel

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.places.ui.PlacePicker
import com.yanzhenjie.permission.AndPermission
import com.zhouyou.http.EasyHttp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_float.*
import net.event.MessageEvent
import net.utils.MyFragmentManager
import org.greenrobot.eventbus.EventBus


class MainActivity : AppCompatActivity() {
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EasyHttp.init(application)
        AndPermission.with(this)
            .runtime()
            .permission(permissions)
            .onGranted {
                initBottomBar()
                initMapType()
            }.onDenied {
                finish()
            }.start()

    }

    private fun initBottomBar() {
        MyFragmentManager.get().showFragment(0, supportFragmentManager)
        val navigationController = tab.material()
            .addItem(R.mipmap.map, "Map")
            .addItem(R.mipmap.near, "Nearby")
            .addItem(R.mipmap.street, "StreetView")
            .addItem(R.mipmap.interactive, "Interactive")
            .build()
        navigationController.addSimpleTabItemSelectedListener { index, old ->
            if (index != 0) {
                fmm.visibility = View.GONE
            } else {
                fmm.visibility = View.VISIBLE
            }
            MyFragmentManager.get().showFragment(index, supportFragmentManager)
        }
    }

    private fun initMapType() {
        search.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("s"))
        }
        normal.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("normal"))
        }
        hybird.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("hybird"))
        }
        sat.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("sat"))
        }
        terrain.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("terrain"))
        }
    }
}