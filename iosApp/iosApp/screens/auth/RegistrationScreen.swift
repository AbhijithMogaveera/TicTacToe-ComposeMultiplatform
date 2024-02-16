import shared
import SwiftUI

struct RegistrationScreen: View {
    let greet = Greeting().greet()
    @State private var email: String = ""
    @State private var password: String = ""
    var body: some View {
        VStack {
            InputField(hint: "email", text:email)
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            PrimaryButton (action: {
                
            }, text:"Register & Login")
            
        }
        .navigationTitle("Registration")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            RegistrationScreen()
        }
    }
}
