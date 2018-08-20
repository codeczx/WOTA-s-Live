package io.github.wotaslive.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.github.wotaslive.data.model.SyncInfo
import io.reactivex.Flowable

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTeam(period: SyncInfo.Content.Team)

    @Query("SELECT * FROM `team`")
    fun getAll(): Flowable<List<SyncInfo.Content.Team>>

    @Query("SELECT * FROM `team` WHERE team_id=:id LIMIT 1")
    fun getTeam(id: Int): SyncInfo.Content.Team

    @Query("SELECT * FROM `TEAM` WHERE group_id=:id")
    fun getTeams(id: Int): Flowable<List<SyncInfo.Content.Team>>
}