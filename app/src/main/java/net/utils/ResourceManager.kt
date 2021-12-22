package net.utils

import android.content.Context
import net.entity.ResourceEntity

object ResourceManager {
    fun getResourceByFolder(
        context: Context,
        clazz: Class<*>,
        folderName: String,
    ): ArrayList<ResourceEntity> {
        val result = ArrayList<ResourceEntity>()
        for (field in clazz.fields) {
            val name = field.name
            if (!name.startsWith("map") && !name.startsWith("ic_")
                && !name.startsWith("loca") && !name.startsWith("inter") && !name.startsWith("near")
                && !name.startsWith("search") && !name.startsWith("street") && !name.startsWith("down")
                && !name.startsWith("up") 
            ) {
                val id = context.resources.getIdentifier(name, folderName, context.packageName)
                val entity = ResourceEntity(name, id)
                result.add(entity)
            }
        }
        return result
    }
}