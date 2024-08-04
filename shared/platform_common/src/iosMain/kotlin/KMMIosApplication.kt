import com.shared.compose_foundation.FoundationConfiguration
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.configModules
import com.shared.tic_tac_toe.FeatureTicTacToeConfiguration
import org.koin.core.context.startKoin
import platform.Foundation.NSUserDefaults

class KMMIosApplication constructor() {
    fun initApp() {
        startKoin {
            KMMContextProvider.setNSObject(
                NSUserDefaults.standardUserDefaults()
            )
            configModules()
        }
    }

}