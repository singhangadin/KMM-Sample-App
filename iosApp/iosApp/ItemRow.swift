//
//  ItemRow.swift
//  iosApp
//
//  Created by Angad Singh on 08/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ItemRow: View {
    var photoData: Photos

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Image Id: \(photoData.id)")
                Text("Image Url: \(String(photoData.url))")
            }
            Spacer()
        }
    }
}
