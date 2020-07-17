package com.yogendra.socialmediamvvm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article")
data class Articles(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val createdAt: String,
    val content: String,
    val comments: Long,
    val likes: Long,
    val media: List<ArticleMedia>,
    val user: List<Users>

) {
    override fun toString() = "Article Content: $content"
}

@Entity(tableName = "article_media")
data class ArticleMedia(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val blogId: Int,
    val createdAt: String,
    @SerializedName("image")
    val image_url: String,
    val title: String,
    val url: String

) {
    override fun toString() = "Article Media title: $title, Url: $url"
}

@Entity(tableName = "users")
data class Users(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val blogId: Int? = null,
    val createdAt: String,
    val name: String,
    val lastname: String,
    val city: String,
    val designation: String,
    val about: String,
    @SerializedName("avatar")
    val avatar_url: String

) {
    override fun toString() =
        "Article user full name: $name $lastname, avatar: $avatar_url, blogId:$blogId"

}