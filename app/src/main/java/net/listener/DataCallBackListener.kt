package net.listener

import net.entity.DataEntity

interface DataCallBackListener {
    fun onError()
    fun onSuccess(data: ArrayList<DataEntity>)
}