//
//  EventCardView.swift
//  BusLoader
//
//  Created by James Gleadell on 12/29/22.
//

import SwiftUI

struct EventCardView: View {
    let event: Event
    var body: some View {
        VStack(alignment: .leading){
            Text(event.name!)
                .font(.headline)
                .accessibilityAddTraits(.isHeader)
            Spacer()
            Text(event.dateTime!.formatted(date: .long , time: .shortened))
            .font(.caption)
        
        }
        .padding()
    }
}

struct EventCardView_Previews: PreviewProvider {
    static var event = Event.defaultEvent
    static var previews: some View {
        EventCardView(event: event)

    }
}
