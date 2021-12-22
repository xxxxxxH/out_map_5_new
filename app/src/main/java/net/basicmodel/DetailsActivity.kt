package net.basicmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.angcyo.dsladapter.DslAdapter
import kotlinx.android.synthetic.main.activity_details.*
import net.adapter.Item
import net.entity.DataEntity
import net.event.MessageEvent
import net.listener.DataCallBackListener
import net.utils.RequestUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class DetailsActivity : AppCompatActivity(), DataCallBackListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        EventBus.getDefault().register(this)
        val i = intent
        val data = i.getSerializableExtra("data") as DataEntity?
        data?.let {
            img.displayImage(it.imageUrl)
            RequestUtils.getData2(this, it.key, it)
        }

    }

    override fun onError() {

    }

    override fun onSuccess(data: ArrayList<DataEntity>) {
        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recycler.layoutManager = linearLayoutManager
        val adapter = DslAdapter()
        data.forEach {
            val item = Item(this, "activity")
            item._itemData = it
            adapter.addLastItem(item)
        }
        recycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        if (msg[0] == "activity") {
            val entity = msg[1] as DataEntity
            img.displayImage(entity.imageUrl)
        }
    }
}