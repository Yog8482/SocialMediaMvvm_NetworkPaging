package com.yogendra.socialmediamvvm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.yogendra.socialmediamvvm.utils.DATE_FORMAT_12
import com.yogendra.socialmediamvvm.utils.DATE_FORMAT_20
import com.yogendra.socialmediamvvm.utils.formatDateFromDateString
import java.math.RoundingMode
import java.text.DecimalFormat

@Entity(tableName = "article")
data class Articles(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val createdAt: String,
    val content: String,
    val comments: Long,
    val likes: Long,
    val media: List<ArticleMedia>?,
    val user: List<Users>

) {
    override fun toString() = "Article Content: $content"

    fun getDateFormatted(): String {
        return formatDateFromDateString(DATE_FORMAT_12, DATE_FORMAT_20, createdAt)
    }

    fun getFormattedComments(): String {

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        if (comments > 1000)
            return "${df.format(comments.toDouble() / 1000)}K"
        else
            return "${comments}"
    }

    fun getFormattedlikes(): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        if (likes > 1000)
            return "${df.format(likes.toDouble() / 1000)}K"
        else
            return "${likes}"

    }
}

//@Entity(tableName = "article_media")
data class ArticleMedia(
//    @PrimaryKey
//    @ColumnInfo(name = "id")
    val id: String,
    val blogId: String,
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
    val id: String,
    val blogId: String? = null,//default null
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

