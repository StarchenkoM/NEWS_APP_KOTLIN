package com.trd.freenewsapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromNewsHashMap(inputMap: Map<String, String>?): String {
        return if (inputMap == null) "" else Gson().toJson(inputMap)
    }

    @TypeConverter
    fun toNewsHashMap(inputString: String): HashMap<String, String>?{
        return Gson().fromJson(inputString, object : TypeToken<HashMap<String, String>?>() {}.type)
    }
}
