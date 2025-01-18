package com.williamfq.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converters para Room Database
 * Maneja la conversión de tipos complejos que Room no puede manejar directamente
 * Created by William8677 on 2025-01-04
 */
class Converters {
    private val gson = Gson()

    /**
     * Convierte una lista de strings a JSON
     */
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return gson.toJson(list ?: emptyList<String>())
    }

    /**
     * Convierte JSON a lista de strings
     */
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return try {
            gson.fromJson(value, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Convierte una lista de Long a JSON
     */
    @TypeConverter
    fun fromLongList(list: List<Long>?): String {
        return gson.toJson(list ?: emptyList<Long>())
    }

    /**
     * Convierte JSON a lista de Long
     */
    @TypeConverter
    fun toLongList(value: String): List<Long> {
        val type = object : TypeToken<List<Long>>() {}.type
        return try {
            gson.fromJson(value, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}