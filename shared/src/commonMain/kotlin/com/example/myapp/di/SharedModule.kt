package com.example.myapp.di

import com.example.myapp.data.remote.createHttpClient
import com.example.myapp.data.repository.UserRepositoryImpl
import com.example.myapp.data.settings.AppSettings
import com.example.myapp.domain.repository.UserRepository
import org.koin.dsl.module

// TODO: サーバー実装後に本番URLへ書き換える
private const val BASE_URL = "https://api.example.com"

val sharedModule = module {
    single { AppSettings(get()) }
    single { createHttpClient() }
    single<UserRepository> {
        UserRepositoryImpl(
            httpClient = get(),
            settings = get(),
            baseUrl = BASE_URL
        )
    }
    // TODO: アプリ固有のリポジトリをここに追加する
}
