package uk.ac.tees.mad.projecthub.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProjectData::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun projectDao() : ProjectDao
}