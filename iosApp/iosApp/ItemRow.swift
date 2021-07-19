//
//  ItemRow.swift
//  iosApp
//
//  Created by Angad Singh on 08/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import Kingfisher

struct ItemRow: View {
    let photoData: Hits

    var body: some View {
        HStack(spacing: 10.0) {
            KFImage(URL(string: photoData.previewURL))
                .resizable()
                .frame(width: 50, height: 50, alignment: .leading)
                .clipShape(RoundedRectangle(cornerRadius: 8))
            
            Text("Photo by \(photoData.user)").frame(alignment: .topLeading)
        }
    }
}
