import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.ComposeUIViewController
import com.shared.AppContent
import platform.UIKit.UIViewController

class AppContentController() {
    fun AppContentContoller(): UIViewController {
        return ComposeUIViewController {
            AppContent()
        }
    }

}