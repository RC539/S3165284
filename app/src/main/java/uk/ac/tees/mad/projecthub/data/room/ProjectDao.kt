package uk.ac.tees.mad.projecthub.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject(project: uk.ac.tees.mad.projecthub.data.room.ProjectData)

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<ProjectData>

    @Query("DELETE FROM projects")
    suspend fun deleteAllProjects()

}