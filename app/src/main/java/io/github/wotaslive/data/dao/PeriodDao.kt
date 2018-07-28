package io.github.wotaslive.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.github.wotaslive.data.model.SyncInfo
import io.reactivex.Flowable

@Dao
interface PeriodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPeriod(period: SyncInfo.Content.Period)

    @Query("SELECT * FROM `period`")
    fun getAll(): Flowable<List<SyncInfo.Content.Period>>

    @Query("SELECT * FROM `period` WHERE period_id=:id LIMIT 1")
    fun getPeriod(id: Int): SyncInfo.Content.Period
}