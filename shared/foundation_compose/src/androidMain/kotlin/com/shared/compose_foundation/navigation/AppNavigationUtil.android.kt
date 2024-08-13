package com.shared.compose_foundation.navigation
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.shared.compose_foundation.activity.LocalActivity

@Composable
actual fun CloseAppOnBackPress() {
    val activity = LocalActivity.current as Activity
    BackHandler(enabled = true){
        activity.finish()
    }
}
