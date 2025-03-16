package br.alisson.edu.tapago.di

import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.api.UserApi
import br.alisson.edu.tapago.data.remote.repository.AuthRepository
import br.alisson.edu.tapago.data.remote.repository.UserRepository
import br.alisson.edu.tapago.data.utils.AuthInterceptor
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesAuthAPI(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenManager: TokenManager
    ): AuthRepository = AuthRepository(authApi, tokenManager)

    @Provides
    @Singleton
    fun providesUserAPI(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        userManager: UserManager
    ): UserRepository = UserRepository(userApi, userManager)
}
