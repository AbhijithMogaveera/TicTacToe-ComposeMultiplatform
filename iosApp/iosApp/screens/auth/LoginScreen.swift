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
        VStack {
            TextField("Email", text: $email)
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
            Button(action:{
                if(true){
                }
            }){
                Text("click me")
            }
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
                            .foregroundColor(.blue)
                    }
                    Text("Home")
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
