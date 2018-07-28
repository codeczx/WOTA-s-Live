package io.github.wotaslive.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.wotaslive.data.dao.GroupDao
import io.github.wotaslive.data.dao.MemberDao
import io.github.wotaslive.data.dao.PeriodDao
import io.github.wotaslive.data.dao.TeamDao
import io.github.wotaslive.data.model.SyncInfo.Content

@Database(entities = [Content.MemberInfo::class, Content.Team::class, Content.Group::class, Content.Period::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun groupDao(): GroupDao
    abstract fun teamDao(): TeamDao
    abstract fun periodDao(): PeriodDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: Room.databaseBuilder(context, AppDatabase::class.java, "wota.db").build()
            }
        }
    }
}