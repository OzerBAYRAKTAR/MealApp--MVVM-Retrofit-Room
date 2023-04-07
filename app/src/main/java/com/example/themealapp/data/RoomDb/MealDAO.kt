package com.example.themealapp.data.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.themealapp.data.model.Meal


@Dao
interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>
}