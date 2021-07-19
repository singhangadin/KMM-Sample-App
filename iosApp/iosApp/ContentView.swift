import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
	var body: some View {
        NavigationView {
            listView()
                .navigationBarTitle("Galaxy Images")
                .navigationBarItems(trailing: Button("Load") {
                    self.viewModel.loadNextImage(query: "galaxy", firstLoad: true)
                })
        }
	}
    
    private func listView() -> AnyView {
        switch viewModel.states {
            case .empty:
                return AnyView(Spacer())
            case .loading:
                return AnyView(Text("Loading...").multilineTextAlignment(.center))
            case .result(let photos):
                return AnyView(List {
                    ForEach(photos, id: \.self) { photo in
                        ItemRow(photoData: photo)
                    }
                })
            case .error(let description):
                return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}

extension ContentView {
    enum States {
        case empty
        case loading
        case result([Hits])
        case error(String)
    }

    class ViewModel: ObservableObject {
        var currentPage = 1
        
        let networkProvider: UseCaseProvider
        @Published var states = States.empty
        
        init(networkProvider: UseCaseProvider) {
            self.networkProvider = UseCaseProvider.init()
        }

        func loadNextImage(query: String, firstLoad: Bool) {
            if (firstLoad) {
                currentPage = 1
            }
            
            self.states = .loading
            networkProvider.pixabayImagesUseCase.getPixadayImages(query: query, page: String(currentPage), completionHandler: { response, error in
                if let response = response {
                    if (response.isSuccess) {
                        self.currentPage += 1
                        self.states = .result(response.data?.hits ?? [])
                    } else {
                        self.states = .error(response.exception?.message ?? "")
                    }
                } else {
                    self.states = .error(error?.localizedDescription ?? "error")
                }
            })
        }
    }
}
