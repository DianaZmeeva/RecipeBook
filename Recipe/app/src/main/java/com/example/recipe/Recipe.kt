package com.example.recipe

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.List


class Recipe {
    @SerializedName("uuid")
    @Expose
    private var uuid: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("images")
    @Expose
    private var images: kotlin.collections.List<String>? = null
    @SerializedName("lastUpdated")
    @Expose
    private var lastUpdated: Int = 0
    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("instructions")
    @Expose
    private var instructions: String? = null
    @SerializedName("difficulty")
    @Expose
    private var difficulty: Int = 0

    fun getUuid(): String? {
        return uuid
    }

    fun setUuid(uuid: String) {
        this.uuid = uuid
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImages(): kotlin.collections.List<String>? {
        return images
    }

    fun setImages(images: kotlin.collections.List<String>) {
        this.images = images
    }

    fun getLastUpdated(): Int {
        return lastUpdated
    }

    fun setLastUpdated(lastUpdated: Int) {
        this.lastUpdated = lastUpdated
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getInstructions(): String? {
        return instructions
    }

    fun setInstructions(instructions: String) {
        this.instructions = instructions
    }

    fun getDifficulty(): Int {
        return difficulty
    }

    fun setDifficulty(difficulty: Int) {
        this.difficulty = difficulty
    }
}