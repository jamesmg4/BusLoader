//
//  EventScreen.swift
//  BusLoader
//
//  Created by James Gleadell on 12/24/22.
//

import SwiftUI

struct EventView: View {
    
    @EnvironmentObject var fetcher: DataFetcher
    
    var event: Event
    
    var body: some View {
        List{
            Section(header: Text("Event Info")) {
                HStack{
                    Label("When", systemImage: "timer")
                    Spacer()
                    Text(event.dateTime!.formatted(date: .long , time: .shortened))
                }
                HStack{
                        NavigationLink(destination: NotesView(event: event, eventNotes: event.notes!))
                    {
                        Label("Notes", systemImage: "envelope.open")
                    }
                }
            }
            Section(header: Text("Attendees")){
                    ForEach(fetcher.attendeeData.attendees) { attendee in
                        Label(attendee.student!.firstName! + " " + attendee.student!.lastName!, systemImage: "person")
                            .swipeActions(edge: .trailing) {
                                Button {
                                    let result = fetcher.setAttendeeStateOnBus(onBus: true, attendee: attendee, event: event)
                                } label: {
                                    Label("Present", systemImage: "envelope.open")
                                    
                                }
                                .tint(.blue)
                            }
                            .foregroundColor(fetcher.attendeeLabelColor(attendee: attendee))
                    }
                
            }
        }
        .task {
            try? await fetcher.fetchAttendeeData(event: event)
        }
        .navigationTitle(event.name!)
        .refreshable {
            try? await fetcher.fetchAttendeeData(event: event)
        }
    }
}

struct EventView_Previews: PreviewProvider {
    static var previews: some View {
        EventView(event: Event.defaultEvent)
    }
}
