package com.yogendra.socialmediamvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yogendra.socialmediamvvm.data.ArticleMedia
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Converters
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.datasource.local.dao.ArticlesDao
import com.yogendra.socialmediamvvm.datasource.local.dao.UsersDao
import com.yogendra.socialmediamvvm.utils.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(
    entities = [Articles::class,
        Users::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    abstract fun usersDao(): UsersDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                /* .addCallback(object : RoomDatabase.Callback() {
                     override fun onCreate(db: SupportSQLiteDatabase) {
                         super.onCreate(db)
                         val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                         WorkManager.getInstance(context).enqueue(request)
                     }
                 })*/
                .build()
        }
    }
}
