import SwiftUI
import shared

@main
struct iOSApp: App {
    init(){
        KMMIosApplication().doInitApp()
    }
	var body: some Scene {
		WindowGroup {
            NavigationView{
                LoginScreen()
            }
		}
	}
}
