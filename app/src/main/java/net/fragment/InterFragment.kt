package net.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.angcyo.dsladapter.DslAdapter
import kotlinx.android.synthetic.main.fragment_inter.*
import net.adapter.Item
import net.basicmodel.DetailsActivity
import net.basicmodel.R
import net.entity.DataEntity
import net.event.MessageEvent
import net.listener.DataCallBackListener
import net.utils.RequestUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class InterFragment : BaseFragment(), DataCallBackListener {
    override fun getLayout(): Int {
        return R.layout.fragment_inter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        RequestUtils.getData1(this)
    }

    override fun onError() {

    }

    override fun onSuccess(data: ArrayList<DataEntity>) {
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recycler.layoutManager = linearLayoutManager
        val adapter = DslAdapter()
        data.forEach {
            val item = Item(requireActivity(), "fragment")
            item._itemData = it
            adapter.addLastItem(item)
        }
        recycler.adapter = adapter
//        val adapter = OneAdapter(recycler) {
//            itemModules += DataItem(requireActivity())
//        }
//        adapter.setItems(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        if (msg[0] == "fragment") {
            val i = Intent(activity, DetailsActivity::class.java)
            i.putExtra("data", msg[1] as DataEntity)
            startActivity(i)
        }
    }
}