package io.github.wotaslive.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.github.wotaslive.data.model.SyncInfo.Content.Group
import io.reactivex.Flowable

@Dao
interface GroupDao {
    @Insert(onConflict = REPLACE)
    fun addGroup(group: Group)

    @Query("SELECT * FROM `group`")
    fun getAll(): Flowable<List<Group>>

    @Query("SELECT * FROM `group` WHERE group_id=:id LIMIT 1")
    fun getGroup(id: Int): Group
}