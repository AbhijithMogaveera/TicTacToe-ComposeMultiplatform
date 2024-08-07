import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.configCommonModules
import org.koin.core.context.startKoin
import platform.Foundation.NSUserDefaults

class KMMIosApplication constructor() {
    fun initApp() {
        startKoin {
            KMMContextProvider.setNSObject(
                NSUserDefaults.standardUserDefaults()
            )
            configCommonModules()
        }
    }

}