import com.shared.auth.SharedAuthModuleConfigurationIos
import com.shared.compose_foundation.SharedFoundationConfiguration
import com.shared.compose_foundation.module_config.ModuleConfiguration
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.tic_tac_toe.TicTacToeConfiguration
import org.koin.core.context.startKoin
import platform.Foundation.NSUserDefaults

class KMMIosApplication constructor() {

    val modules = mutableListOf(
        SharedFoundationConfiguration ,
        SharedAuthModuleConfigurationIos,
        TicTacToeConfiguration
    )

    fun initApp() {
        startKoin {
            KMMContextProvider.setNSObject(
                NSUserDefaults.standardUserDefaults()
            )
            modules.forEach {
                it.configKoinModules(this)
            }
            modules.forEach {
                it.onKoinConfigurationFinish()
            }
        }
    }

}