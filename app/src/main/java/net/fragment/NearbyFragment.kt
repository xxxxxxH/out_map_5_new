package net.fragment

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_near.*
import net.basicmodel.R
import net.utils.ResourceManager
import net.widget.CustomRadioBtn

class NearbyFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_near
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val data =
            ResourceManager.getResourceByFolder(requireContext(), R.mipmap::class.java, "mipmap")
        data.forEach {
            val rb = CustomRadioBtn(requireContext())
            rb.setRb(requireContext(), requireActivity(), it.id, it.name)
            group.addView(rb)
        }
    }
}