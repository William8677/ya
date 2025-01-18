package com.williamfq.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.williamfq.data.entities.*

/**
 * Convertidores necesarios para que Room maneje tipos personalizados en la entidad `Story`.
 */
class StoryConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromMediaType(value: MediaType?): String = value?.name ?: MediaType.TEXT.name

    @TypeConverter
    fun toMediaType(value: String?): MediaType = try {
        value?.let { MediaType.valueOf(it) } ?: MediaType.TEXT
    } catch (e: IllegalArgumentException) {
        MediaType.TEXT
    }

    @TypeConverter
    fun fromTags(tags: List<String>?): String = tags?.joinToString(",") { it.trim() } ?: ""

    @TypeConverter
    fun toTags(tags: String?): List<String> {
        if (tags.isNullOrBlank()) return emptyList()
        return tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun fromCommentsList(comments: List<Comment>?): String {
        return gson.toJson(comments)
    }

    @TypeConverter
    fun toCommentsList(data: String?): List<Comment> {
        if (data.isNullOrBlank()) return emptyList()
        val listType = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromReactionsList(reactions: List<Reaction>?): String {
        return gson.toJson(reactions)
    }

    @TypeConverter
    fun toReactionsList(data: String?): List<Reaction> {
        if (data.isNullOrBlank()) return emptyList()
        val listType = object : TypeToken<List<Reaction>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromPoll(poll: Poll?): String? {
        return gson.toJson(poll)
    }

    @TypeConverter
    fun toPoll(data: String?): Poll? {
        if (data.isNullOrBlank()) return null
        val type = object : TypeToken<Poll>() {}.type
        return gson.fromJson(data, type)
    }
}