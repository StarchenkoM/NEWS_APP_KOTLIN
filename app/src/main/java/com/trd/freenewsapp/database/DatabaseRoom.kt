package com.trd.freenewsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trd.freenewsapp.constants.Constants.ROOM_DATABASE_VERSION

@TypeConverters(TypeConverter::class)
@Database(
    entities = [NewsRoomEntity::class],
    version = ROOM_DATABASE_VERSION
)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}
