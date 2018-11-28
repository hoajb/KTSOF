package it.hoanguyenminh.ktsof.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.hoanguyenminh.ktsof.repository.data.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}