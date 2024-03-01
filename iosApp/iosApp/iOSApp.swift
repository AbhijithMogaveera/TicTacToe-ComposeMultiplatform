import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        KMMIosApplication().doInitApp()
        UINavigationBar
            .appearance()
            .titleTextAttributes = [.foregroundColor: UIColor.red]
    }
    
    var body: some Scene {
        WindowGroup {
            NavigationView{
                TicTacToeContentView()
            }
        }
    }
    
}
