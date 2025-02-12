package com.furniconbreeze.app.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furniconbreeze.app.AppConstant

/**
 * Created by Saikat on 10-Jul-20.
 */
@Dao
interface ProjectListDao {

    @Query("SELECT * FROM " + AppConstant.PROJECT_LIST)
    fun getAll(): List<ProjectListEntity>

    @Insert
    fun insertAll(vararg project: ProjectListEntity)

    @Query("DELETE FROM " + AppConstant.PROJECT_LIST)
    fun deleteAll()

}