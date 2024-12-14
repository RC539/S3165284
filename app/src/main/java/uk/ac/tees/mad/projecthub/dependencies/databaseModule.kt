package uk.ac.tees.mad.projecthub.dependencies

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.projecthub.data.room.Database
import uk.ac.tees.mad.projecthub.data.room.ProjectDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object databaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java,
            "project_database",
            ).build()
    }

    @Provides
    @Singleton
    fun provideProjectDao(database: Database): ProjectDao {
        return database.projectDao()
    }

}