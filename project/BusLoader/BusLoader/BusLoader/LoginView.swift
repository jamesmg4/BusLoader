//
//  ContentView.swift
//  BusLoader
//
//  Created by James Gleadell on 11/26/22.
//

import SwiftUI

struct LoginView: View {
    @EnvironmentObject var coordinator: AppCoordinator
    @EnvironmentObject var fetcher: DataFetcher
    
    @State var testCaptain: Student = .defaultStudent
    
    var body: some View {
        VStack {
            Picker("Select Bus Captain", selection: $testCaptain) {
                ForEach(fetcher.busCaptainData.students) { student in
                    Text(student.firstName! + " " + student.lastName!).tag(student)
                }
            }
            .pickerStyle(.menu)
            Button("Show Events"){
                fetcher.busCaptain = testCaptain
                coordinator.screen = .eventList
            }
                
        }
        .task {
            try? await fetcher.fetchBusCaptainData()
        }
        .padding()
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
