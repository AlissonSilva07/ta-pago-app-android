package br.alisson.edu.tapago.di

import br.alisson.edu.tapago.data.remote.api.AnalyticsApi
import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.api.UserApi
import br.alisson.edu.tapago.data.remote.repository.AnalyticsRepository
import br.alisson.edu.tapago.data.remote.repository.AuthRepository
import br.alisson.edu.tapago.data.remote.repository.UserRepository
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.data.utils.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenManager: TokenManager
    ): AuthRepository = AuthRepository(authApi, tokenManager)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        userManager: UserManager
    ): UserRepository = UserRepository(userApi, userManager)

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        analyticsApi: AnalyticsApi,
    ): AnalyticsRepository = AnalyticsRepository(analyticsApi)
}