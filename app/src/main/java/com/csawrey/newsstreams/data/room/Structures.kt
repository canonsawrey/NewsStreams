package com.csawrey.newsstreams.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.csawrey.newsstreams.common.Sort
import com.csawrey.newsstreams.edit_stream.EditorItem
import com.csawrey.newsstreams.edit_stream.EditorSearchItem

@Entity
data class DatabaseNewsStream(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "created") val created: Long
)

@Entity(foreignKeys = [ForeignKey(entity = DatabaseNewsStream::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("parent_stream"),
    onDelete = ForeignKey.CASCADE) ] )

data class DatabaseSearchItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val parent_stream: Long,
    @ColumnInfo(name = "keyword") val keyword: String,
    @ColumnInfo(name = "sort") val sort: String,
    @ColumnInfo(name = "weight") val weight: String,
    @ColumnInfo(name = "daysOld") val daysOld: Int,
    @ColumnInfo(name = "created") val created: Long
) {
    fun toEditorItem(): EditorItem = EditorSearchItem(
        this.uid,
        this.keyword,
        Sort.fromString(this.sort),


}

@Entity(foreignKeys = [ForeignKey(entity = DatabaseSearchItem::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("parent_search_item"),
    onDelete = ForeignKey.CASCADE) ] )

data class DatabaseCachedStoryItem(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    val parent_search_item: Long,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "created") val created: Long
)