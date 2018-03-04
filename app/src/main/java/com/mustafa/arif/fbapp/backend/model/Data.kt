package com.mustafa.arif.fbapp.backend.model


class Data {
    private var id: String? = null

    private var picture: String? = null

    private var story: String? = null

    private var created_time: String? = null

    private var message: String? = null

    private var name: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }


    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getPicture(): String? {
        return picture
    }

    fun setPicture(picture: String) {
        this.picture = picture
    }

    fun getStory(): String? {
        return story
    }

    fun setStory(story: String) {
        this.story = story
    }

    fun getCreated_time(): String? {
        return created_time
    }

    fun setCreated_time(created_time: String) {
        this.created_time = created_time
    }

    override fun toString(): String {
        return "ClassPojo [id = $id, picture = $picture, story = $story, created_time = $created_time]"
    }
}