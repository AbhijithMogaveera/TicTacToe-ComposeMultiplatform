//
//  PrimaryButton.swift
//  iosApp
//
//  Created by Abhijith Mogaveera on 10/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct PrimaryButton : View{
    var action: () -> Void
    var text:String
    var body: some View {
        Button(
            action:action
        ){
            Text(text)
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.blue)
                .foregroundColor(.white)
                .cornerRadius(8)
        }.padding()
    }
}

struct PrimaryButtonPreview:PreviewProvider{
    static var previews: some View{
        PrimaryButton(action: {}, text: "Register")
    }
}
