package net.adapter

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import me.panpf.sketch.SketchImageView
import net.basicmodel.R
import net.entity.DataEntity
import net.utils.ScreenUtils

class DataItem(val activity: Activity) : ItemModule<DataEntity>() {
    init {
        config {
            layoutResource = R.layout.layout_item
        }

        onBind { model, viewBinder, metadata ->
            val root = viewBinder.findViewById<CardView>(R.id.root)
            root.let {
                it.layoutParams = it.layoutParams.apply {
                    height = ScreenUtils.getScreenSize(activity)[0] / 3
                }
            }
            val img = viewBinder.findViewById<SketchImageView>(R.id.img)
            img.displayImage(model.imageUrl)
            val name = viewBinder.findViewById<TextView>(R.id.img_name)
            name.text = model.title
        }

        eventHooks += ClickEventHook<DataEntity>().apply {
            onClick { model, viewBinder, metadata ->
                Toast.makeText(activity, model.title,Toast.LENGTH_SHORT).show()
            }
        }
    }
}