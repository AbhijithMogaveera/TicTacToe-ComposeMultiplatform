//
//  InputField.swift
//  iosApp
//
//  Created by Abhijith Mogaveera on 10/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct InputField:View{
    
    @State var hint: String
    @State var text: String
    
    var body:some View{
        TextField(hint, text: $text)
            .padding(10)
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .padding(10)
    }
}

struct InputFieldPreview:PreviewProvider{
    static var previews: some View{
        InputField(hint:"email",text:"")
    }
}
