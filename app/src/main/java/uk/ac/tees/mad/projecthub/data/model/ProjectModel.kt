package uk.ac.tees.mad.projecthub.data.model

data class ProjectModel(
    val projectName : String ="",
    val projectDescription : String = "",
    val requiredSkills : String = "",
    val deadline : String = "",
    val budget : String = "",
    val imageUrl : String = ""
)
