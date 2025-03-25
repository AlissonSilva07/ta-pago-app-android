package br.alisson.edu.tapago.di

import br.alisson.edu.tapago.data.remote.api.AnalyticsApi
import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.api.ExpensesApi
import br.alisson.edu.tapago.data.remote.api.UserApi
import br.alisson.edu.tapago.data.remote.repository.AnalyticsRepositoryImpl
import br.alisson.edu.tapago.data.remote.repository.AuthRepositoryImpl
import br.alisson.edu.tapago.data.remote.repository.ExpensesRepositoryImpl
import br.alisson.edu.tapago.data.remote.repository.UserRepositoryImpl
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.domain.repository.AnalyticsRepository
import br.alisson.edu.tapago.domain.repository.AuthRepository
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import br.alisson.edu.tapago.domain.repository.UserRepository
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
    ): AuthRepository = AuthRepositoryImpl(authApi, tokenManager)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        userManager: UserManager
    ): UserRepository = UserRepositoryImpl(userApi, userManager)

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        analyticsApi: AnalyticsApi,
    ): AnalyticsRepository = AnalyticsRepositoryImpl(analyticsApi)

    @Provides
    @Singleton
    fun provideExpensesRepository(
        expensesApi: ExpensesApi,
    ): ExpensesRepository = ExpensesRepositoryImpl(expensesApi)
}