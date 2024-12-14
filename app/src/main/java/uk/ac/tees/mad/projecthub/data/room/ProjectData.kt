package uk.ac.tees.mad.projecthub.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val projectName: String,
    val projectDescription: String,
    val requiredSkills: String,
    val budget: String,
    val deadline: String,
    val imageUrl : String
)
