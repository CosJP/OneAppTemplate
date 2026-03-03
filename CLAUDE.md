# CLAUDE.md

このテンプレートを使った新規アプリ開発時のガイド。

## テンプレートの構成

Android KMP スターターテンプレート。以下が実装済み：

- **KMP構成**: `app`（Android UI）+ `shared`（共通ロジック）の2モジュール構成
- **初回起動フロー**: スプラッシュ → 利用規約同意 → ログイン → プロフィール設定 → 位置情報設定 → 追加設定 → ホーム
- **Google Sign-In**: Credential Manager（`GetSignInWithGoogleOption`）で実装済み
- **DI**: Koin（`appModule` + `sharedModule`）
- **API通信**: Ktor + kotlinx.serialization
- **設定永続化**: multiplatform-settings（SharedPreferences）
- **画像読み込み**: Coil 3

## 新規アプリ開始時のチェックリスト

### 1. プロジェクト固有の設定
- [ ] `app/build.gradle.kts` の `namespace` / `applicationId` を変更（`com.example.myapp` → 実際のパッケージ名）
- [ ] `shared/build.gradle.kts` の `namespace` を変更（`com.example.myapp.shared` → 実際のパッケージ名）
- [ ] `settings.gradle.kts` の `rootProject.name` を変更
- [ ] `app/src/main/res/values/strings.xml` のアプリ名を変更

### 2. Firebase セットアップ
- [ ] Firebase Console でプロジェクト作成・アプリ追加
- [ ] デバッグ SHA-1 を Firebase Console に登録（Android Studio 付属の keytool で取得）
- [ ] `google-services.json` を `app/` に配置
- [ ] `AuthViewModel.kt` の `WEB_CLIENT_ID` を `google-services.json` の `client_type:3` の値に変更

### 3. アプリ固有実装
- [ ] `SplashScreen.kt` のアプリ名・ロゴを変更
- [ ] `OnboardingScreen.kt` の利用規約・PP本文を記載
- [ ] `AuthScreen.kt` のタイトルを変更（不要な認証ボタンは削除）
- [ ] `ExtraSetupScreen.kt` をアプリ固有のセットアップ内容に書き換え
- [ ] `SetupStep.kt` のステップ定義をアプリに合わせて調整
- [ ] `Color.kt` のブランドカラーを変更
- [ ] `SharedModule.kt` の `BASE_URL` をサーバーURLに変更

### 4. Apple / Instagram 認証（必要な場合）
- [ ] Apple Developer アカウントで Sign in with Apple を設定
- [ ] Meta Developer アカウントでアプリ登録
- [ ] `AuthViewModel.kt` の `signInWithApple` / `signInWithInstagram` を AppAuth で実装

## バージョン構成

- Kotlin 2.1.21 / AGP 9.0.1 / Gradle 9.1.0
- Compose BOM 2025.04.00 / Navigation Compose 2.9.0 / Lifecycle 2.9.0
- Koin 4.0.2 / Ktor 3.1.3 / Firebase BOM 33.12.0

## AGP 9.0 の注意点

- `kotlin-android` プラグインを `app` に明示的に追加しない（AGP 9 が内蔵）
- KMP ライブラリには `com.android.kotlin.multiplatform.library` を使う
- `android {}` ブロックは `kotlin {}` の中に書く
- `kotlinOptions {}` は廃止 → `compilerOptions {}` を使う