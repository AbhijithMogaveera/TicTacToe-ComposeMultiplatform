//
//  LoginScreen.swift
//  iosApp
//
//  Created by Abhijith Mogaveera on 10/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LoginScreen: View {
    
    let vm : IosViewModelAuth = IosViewModelAuth()
    let greet = Greeting().greet()
    @State private var email: String = ""
    @State private var password: String = ""

    
    var body: some View {
        ZStack{
            
            Color(hex:"05445E").ignoresSafeArea()
            VStack {
                TextField("Email", text: $email)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                SecureField("Password", text: $password)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                HStack {
                    Spacer()
                    NavigationLink(destination: RegistrationScreen(), label: {
                        Text("New here..?").padding()
                        
                    })
                }
                PrimaryButton(action: {
                    print("button clicked lol")
                    vm.login(
                        userName: email,
                        password: password
                    ).subscribe(
                        onCollect: { res in
                            print("--------------------")
                            print(res ?? "hehe lol")
                            print("--------------------")
                        }
                    )
                    
                }, text: "Login")
                .padding(.top, 20)
            }
            .navigationTitle("Login")
            .navigationBarBackButtonHidden(false)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    HStack {
                        Button(action: {
                        }) {
                            Image(systemName: "chevron.left")
                                .foregroundColor(.white)
                        }
                        Text("Home").foregroundStyle(.white)
                    }
                }
            }
        }
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView{
            LoginScreen()
        }
    }
}
