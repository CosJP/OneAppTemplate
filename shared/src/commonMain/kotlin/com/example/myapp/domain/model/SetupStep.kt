package com.example.myapp.domain.model

enum class SetupStep {
    NONE,
    PROFILE,
    LOCATION,
    EXTRA,    // TODO: アプリ固有の追加セットアップに合わせてステップを変更する
    COMPLETE
}
