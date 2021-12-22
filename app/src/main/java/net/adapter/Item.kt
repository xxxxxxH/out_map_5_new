package net.adapter

import android.app.Activity
import android.content.Intent
import com.angcyo.dsladapter.DslAdapterItem
import com.angcyo.dsladapter.DslViewHolder
import me.panpf.sketch.SketchImageView
import net.basicmodel.DetailsActivity
import net.basicmodel.R
import net.entity.DataEntity
import net.event.MessageEvent
import net.utils.ScreenUtils
import org.greenrobot.eventbus.EventBus

class Item(val activity: Activity, val type: String) : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.layout_item
    }

    var _itemData: DataEntity? = null
    override fun onItemBind(
        itemHolder: DslViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem,
        payloads: List<Any>
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem, payloads)
        val root = itemHolder.view(R.id.root)
        root?.let {
            it.layoutParams = it.layoutParams.apply {
                height = ScreenUtils.getScreenSize(activity)[0] / 3
            }
            it.setOnClickListener {
                EventBus.getDefault().post(MessageEvent(type,_itemData))
            }
        }
        val img = itemHolder.img(R.id.img) as SketchImageView
        img.displayImage(_itemData!!.imageUrl)
        val name = itemHolder.tv(R.id.img_name)
        name!!.text = _itemData!!.title
    }

}