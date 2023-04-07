package com.example.themealapp.data.RoomDb

import android.content.Context
import androidx.room.*
import com.example.themealapp.data.model.Meal


@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase: RoomDatabase() {
    abstract fun mealDao(): MealDAO

    companion object{
        @Volatile
        var INSTANCE : MealDataBase? = null

        @Synchronized
        fun getInstance(context: Context): MealDataBase{
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDataBase
        }
    }
}