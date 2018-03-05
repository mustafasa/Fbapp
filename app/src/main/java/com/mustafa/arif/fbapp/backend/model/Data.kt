package com.mustafa.arif.fbapp.backend.model

import android.os.Parcel
import android.os.Parcelable


class Data() : Parcelable {
    private var id: String? = null

    private var picture: String? = null

    private var full_picture: String? = null

    private var permalink_url: String? = null

    private var story: String? = null

    private var created_time: String? = null

    private var message: String? = null

    private var name: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        picture = parcel.readString()
        full_picture = parcel.readString()
        permalink_url = parcel.readString()
        story = parcel.readString()
        created_time = parcel.readString()
        message = parcel.readString()
        name = parcel.readString()
    }

    fun getFullPicture(): String? {
        return full_picture
    }

    fun setPermalinkUrl(permalink_url: String) {
        this.permalink_url = permalink_url
    }

    fun getPermalinkUrl(): String? {
        return permalink_url
    }

    fun setFullPicture(full_picture: String) {
        this.full_picture = full_picture
    }

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

    fun getCreatedTime(): String? {
        return created_time
    }

    fun setCreatedTime(created_time: String) {
        this.created_time = created_time
    }

    override fun toString(): String {
        return "ClassPojo [id = $id, picture = $picture, story = $story, created_time = $created_time]"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(picture)
        parcel.writeString(full_picture)
        parcel.writeString(permalink_url)
        parcel.writeString(story)
        parcel.writeString(created_time)
        parcel.writeString(message)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}