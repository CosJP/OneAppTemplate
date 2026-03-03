# OneAppTemplate

Android KMP スターターテンプレート。新規 Android アプリ開発の初期セットアップ済み。

## 含まれているもの

### 技術スタック
| カテゴリ | ライブラリ |
|---|---|
| アーキテクチャ | KMP（app + shared モジュール） |
| UI | Jetpack Compose / Material3 |
| ナビゲーション | Navigation Compose 2.9.0 |
| DI | Koin 4.0.2 |
| API通信 | Ktor 3.1.3 |
| 認証 | Credential Manager / AppAuth |
| 設定永続化 | multiplatform-settings |
| 画像読み込み | Coil 3 |
| プッシュ通知 | Firebase Cloud Messaging |

### 実装済みフロー
```
スプラッシュ → 利用規約同意 → ログイン → プロフィール設定 → 位置情報設定 → 追加設定 → ホーム
```
- Google Sign-In（Credential Manager）動作確認済み
- Apple / Instagram 認証はボタンUI + TODO のみ

### バージョン構成
- Kotlin 2.1.21 / AGP 9.0.1 / Gradle 9.1.0
- Compose BOM 2025.04.00 / Lifecycle 2.9.0

## 使い方

### 1. テンプレートから作成
GitHub の「Use this template」でリポジトリを作成。

### 2. パッケージ名の変更
プロジェクト全体で `com.example.myapp` を実際のパッケージ名に置換。

```bash
# 例
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.kts" -o -name "*.toml" \) \
  -exec sed -i 's/com\.example\.myapp/com.yourcompany.yourapp/g' {} \;
```

ディレクトリ構造も合わせて変更：
- `app/src/main/kotlin/com/example/myapp/` → 実際のパッケージパスに移動
- `shared/src/commonMain/kotlin/com/example/myapp/` → 実際のパッケージパスに移動

### 3. Firebase セットアップ
1. Firebase Console でプロジェクト作成・Android アプリ追加
2. デバッグ SHA-1 を登録
   ```bash
   # Android Studio 付属の keytool を使う（keytool は PATH に入っていないことが多い）
   "/path/to/AndroidStudio/jbr/bin/keytool" -list -v \
     -keystore ~/.android/debug.keystore \
     -alias androiddebugkey -storepass android -keypass android
   ```
3. `google-services.json` を `app/` に配置
4. `AuthViewModel.kt` の `WEB_CLIENT_ID` を `google-services.json` 内 `client_type:3` の `client_id` に変更

### 4. アプリ固有の実装
`CLAUDE.md` のチェックリストを参照。

## ライセンス
MIT