package net.utils

import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import net.entity.DataEntity
import net.listener.DataCallBackListener

object RequestUtils {
    fun getData1(listener: DataCallBackListener) {
        EasyHttp.get("https://www.google.com/" + "streetview/feed/gallery/data.json")
            .execute<String>(object : SimpleCallBack<String>() {
                override fun onError(e: ApiException?) {
                    listener.onError()
                }

                override fun onSuccess(t: String?) {
                    val data = strToObject1(t!!)
                    listener.onSuccess(data)
                }
            })
    }

    fun strToObject1(str: String): ArrayList<DataEntity> {
        val result = ArrayList<DataEntity>()
        val map: Map<String, DataEntity> =
            JSON.parseObject(str, object : TypeReference<Map<String, DataEntity>>() {})
        val m: Set<Map.Entry<String, DataEntity>> = map.entries
        val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
        while (it.hasNext()) {
            val en: Map.Entry<String, DataEntity> = it.next()
            val json = JSON.toJSON(en.value)
            val entity: DataEntity = JSON.parseObject(json.toString(), DataEntity::class.java)
            entity.key = en.key
            if (TextUtils.isEmpty(entity.panoid)) {
                continue
            } else {
                if (entity.panoid == "LiAWseC5n46JieDt9Dkevw") {
                    continue
                }
            }
            if (entity.fife) {
                entity.imageUrl =
                    "https://lh4.googleusercontent.com/" + entity.panoid + "/w400-h300-fo90-ya0-pi0/"
                continue
            } else {
                entity.imageUrl =
                    "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity.panoid
            }
            result.add(entity)
        }
        return result
    }

    fun getData2(listener: DataCallBackListener, key: String, big: DataEntity) {
        EasyHttp.get("https://www.google.com/streetview/feed/gallery/collection/$key.json")
            .execute<String>(object : SimpleCallBack<String>() {
                override fun onError(e: ApiException?) {
                    listener.onError()
                }

                override fun onSuccess(t: String?) {
                    val data = strToObject2(t!!, big)
                    listener.onSuccess(data)
                }
            })
    }

    fun strToObject2(str: String, big: DataEntity): ArrayList<DataEntity> {
        val result = ArrayList<DataEntity>()
        val map: Map<String, DataEntity> =
            JSON.parseObject(str, object : TypeReference<Map<String, DataEntity>>() {})
        val m: Set<Map.Entry<String, DataEntity>> = map.entries
        val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
        while (it.hasNext()) {
            val en: Map.Entry<String, DataEntity> = it.next()
            val json = JSON.toJSON(en.value)
            val entity: DataEntity = JSON.parseObject(json.toString(), DataEntity::class.java)
            entity.pannoId = entity.panoid
            if (big.fife) {
                entity.imageUrl =
                    "https://lh4.googleusercontent.com/" + entity.pannoId + "/w400-h300-fo90-ya0-pi0/"
            } else {
                entity.imageUrl =
                    "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity.panoid
            }
            result.add(entity)
        }
        return result
    }
}