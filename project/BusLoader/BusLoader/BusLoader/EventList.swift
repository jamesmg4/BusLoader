//
//  EventList.swift
//  BusLoader
//
//  Created by James Gleadell on 12/28/22.
//


import SwiftUI

struct EventTableRowView: View {
    var event: Event
    var body: some View {
        HStack {
            Text(event.name!)
            Spacer()
            Text(event.dateTime!.formatted(date: .long , time: .shortened))
        }
    }
}

struct EventList: View {
    @EnvironmentObject var coordinator: AppCoordinator
    @EnvironmentObject var fetcher: DataFetcher
    
    var body: some View {
            List{
                ForEach(fetcher.eventData.events) { event in
                    NavigationLink(destination: EventView(event: event).environmentObject(fetcher))  {
                        EventCardView(event: event)
                    }
                    .listRowBackground(event.themeColor())
                    .navigationTitle("Events")
                }
                
            }
        .task {
            try? await fetcher.fetchEventData()
        }
        .refreshable {
            try? await fetcher.fetchEventData()
        }
    }
}

struct EventList_Previews: PreviewProvider {
    static var previews: some View {
        StudentList().environmentObject(DataFetcher())
    }
}

