package vass.rickymorty.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vass.rickymorty.data.repository.CharacterRepositoryImpl
import vass.rickymorty.domain.repository.CharacterRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindRepository(impl: CharacterRepositoryImpl): CharacterRepository
}