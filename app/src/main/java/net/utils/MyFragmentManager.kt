package net.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import net.basicmodel.R
import net.fragment.*

class MyFragmentManager {

    var mapFragment: MapFragment? = null
    var nearbyFragment: NearbyFragment? = null
    var streetFragment: StreetFragment? = null
    var interFragment: InterFragment? = null

    companion object {
        private var i: MyFragmentManager? = null
            get() {
                field ?: run {
                    field = MyFragmentManager()
                }
                return field
            }

        @Synchronized
        fun get(): MyFragmentManager {
            return i!!
        }
    }

    fun showFragment(position: Int, fm: FragmentManager) {
        val ft = fm.beginTransaction()
        hideAllFragment(ft)
        when (position) {
            0 -> {
                mapFragment = createFragment(fm, "tag") as MapFragment?
                mapFragment?.let {
                    ft.show(it)
                } ?: run {
                    mapFragment = MapFragment()
                    ft.add(R.id.content, mapFragment!!, "map")
                }
            }
            1 -> {
                nearbyFragment = createFragment(fm, "near") as NearbyFragment?
                nearbyFragment?.let {
                    ft.show(it)
                } ?: run {
                    nearbyFragment = NearbyFragment()
                    ft.add(R.id.content, nearbyFragment!!, "near")
                }
            }
            2 -> {
                streetFragment = createFragment(fm, "street") as StreetFragment?
                streetFragment?.let {
                    ft.show(it)
                } ?: run {
                    streetFragment = StreetFragment()
                    ft.add(R.id.content, streetFragment!!, "street")
                }
            }
            3 -> {
                interFragment = createFragment(fm, "inter") as InterFragment?
                interFragment?.let {
                    ft.show(it)
                } ?: run {
                    interFragment = InterFragment()
                    ft.add(R.id.content, interFragment!!, "inter")
                }
            }
        }
        ft.commit()
    }

    private fun hideAllFragment(ft: FragmentTransaction) {
        mapFragment?.let {
            ft.hide(it)
        }
        nearbyFragment?.let {
            ft.hide(it)
        }
        streetFragment?.let {
            ft.hide(it)
        }
        interFragment?.let {
            ft.hide(it)
        }
    }

    private fun createFragment(fm: FragmentManager, tag: String): Fragment? {
        var f: Fragment? = fm.findFragmentByTag(tag)
        f?.let {
            it as BaseFragment
        } ?: run {
            f = null
        }
        return f
    }
}