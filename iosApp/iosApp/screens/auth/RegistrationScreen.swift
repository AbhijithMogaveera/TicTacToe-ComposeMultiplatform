import shared
import SwiftUI

struct RegistrationScreen: View {
    let vm : IosViewModelAuth = IosViewModelAuth()
    let greet = Greeting().greet()
    @State private var email: String = ""
    @State private var password: String = ""
    var body: some View {
        ZStack{
            Color(hex:"05445E").ignoresSafeArea()
            VStack {
                InputField(hint: "email", text:email)
                SecureField("Password", text: $password)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                PrimaryButton (action: {
                    vm.register(
                        userName: email, 
                        password:password
                    ).subscribe(
                        onCollect: { res in
                            
                        }
                    )
                }, text:"Register & Login")
            }
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
