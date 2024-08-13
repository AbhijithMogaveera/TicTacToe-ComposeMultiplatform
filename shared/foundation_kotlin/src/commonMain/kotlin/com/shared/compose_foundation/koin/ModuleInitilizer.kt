package com.shared.compose_foundation.koin

import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

/**
 * Interface for initializing Koin modules in an application.
 *
 * Implement this interface to configure Koin modules and perform additional actions
 * after Koin has been configured. This allows for custom setup logic during the
 * initialization phase of Koin in your application.
 *
 * Example usage:
 * ```
 * class MyModuleInitializer : ModuleInitializer {
 *     override fun configKoinModules(koinApplication: KoinApplication) {
 *         // Define and configure Koin modules here
 *         koinApplication.modules(myModule)
 *     }
 *
 *     override fun onKoinConfigurationFinish() {
 *         // Perform actions after Koin configuration is complete
 *     }
 * }
 * ```
 */
interface ModuleInitilizer: KoinComponent {
    /**
     * Configures Koin modules.
     *
     * @param koinApplication The KoinApplication instance used to define and configure modules.
     *
     * Implement this method to specify which Koin modules should be loaded into the application.
     */
    fun configKoinModules(koinApplication: KoinApplication)

    /**
     * Called after Koin configuration is finished.
     *
     * Override this method to perform any additional setup or initialization tasks
     * that need to occur after Koin modules have been configured.
     *
     * This method has a default implementation that does nothing, so it is optional
     * to override it in your implementation.
     */
    fun onKoinConfigurationFinish(){}
}