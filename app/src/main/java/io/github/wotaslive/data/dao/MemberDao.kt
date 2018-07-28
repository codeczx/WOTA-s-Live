package io.github.wotaslive.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.github.wotaslive.data.model.SyncInfo
import io.reactivex.Flowable

@Dao
interface MemberDao {
    @Insert(onConflict = REPLACE)
    fun addMember(memberInfo: SyncInfo.Content.MemberInfo)

    @Query("SELECT * FROM `member`")
    fun getAll(): Flowable<List<SyncInfo.Content.MemberInfo>>

    @Query("SELECT * FROM `member` WHERE member_id=:id LIMIT 1")
    fun getMember(id: Int): SyncInfo.Content.MemberInfo
}