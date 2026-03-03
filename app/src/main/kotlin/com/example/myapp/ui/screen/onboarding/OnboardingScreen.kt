package com.example.myapp.ui.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.data.settings.AppSettings
import org.koin.compose.koinInject

@Composable
fun OnboardingScreen(
    onAgreed: () -> Unit,
    appSettings: AppSettings = koinInject()
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // TODO: アプリ名に差し替える
            Text(
                text = "MyApp へようこそ",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "ご利用の前に、以下の利用規約とプライバシーポリシーをお読みください。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // TODO: 実際の利用規約文を記載する
            TermsCard(
                title = "利用規約",
                content = "利用規約の内容をここに記載してください。"
            )

            // TODO: 実際のプライバシーポリシー文を記載する
            TermsCard(
                title = "プライバシーポリシー",
                content = "プライバシーポリシーの内容をここに記載してください。"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    appSettings.agreedToTerms = true
                    onAgreed()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("同意してはじめる")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TermsCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
