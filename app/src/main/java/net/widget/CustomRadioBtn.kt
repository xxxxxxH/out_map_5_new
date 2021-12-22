package net.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import net.basicmodel.R
import net.utils.PackageUtils
import net.utils.ScreenUtils

class CustomRadioBtn : LinearLayout {
    private var rb: RadioButton? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context): View {
        val v: View = LayoutInflater.from(context).inflate(R.layout.layout_radiobutton, this, true)
        rb = v.findViewById(R.id.rb)
        return v
    }

    fun setRb(context: Context, activity: Activity, id: Int, content: String) {
        rb?.let {
            it.layoutParams = it.layoutParams.apply {
                width = ScreenUtils.getScreenSize(activity)[1] / 3
                height = ScreenUtils.getScreenSize(activity)[0] / 5
            }
            it.gravity = Gravity.CENTER
            it.text = content
            it.setCompoundDrawables(null, setDrawableTop(context, id), null, null)
            it.setOnClickListener {
                click(context, content)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDrawableTop(context: Context, id: Int): Drawable {
        val d = context.resources.getDrawable(id)
        d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
        return d
    }

    private fun click(context: Context, s: String) {
        if (!PackageUtils.checkAppInstalled(context, "com.google.android.apps.maps")) {
            Toast.makeText(context, "not found google map", Toast.LENGTH_SHORT).show()
            return
        }
        PackageUtils.startApp(context, Uri.parse("http://maps.google.com/maps?q=$s&hl=en"))
    }
}