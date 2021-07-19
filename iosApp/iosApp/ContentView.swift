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
                        ItemRow(photoData: photo).onAppear {
                            self.getNextPageIfNecessary(encounteredIndex: photos.firstIndex(of: photo)!, rows: photos)
                        }
                    }
                })
            case .error(let description):
                return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
    
    private func getNextPageIfNecessary(encounteredIndex: Int, rows: [Photos]) {
        guard encounteredIndex == rows.count - 1 else { return }
        self.viewModel.loadNextImage(query: "galaxy", firstLoad: false)
//        rows.append(contentsOf: Array(repeating: "Photos", count: 20))
        
    }
}

extension ContentView {
    enum States {
        case empty
        case loading
        case result([Photos])
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
            networkProvider.pexelImagesUseCase.getPexelImages(query: query, page: String(currentPage), completionHandler: { response, error in
                if let response = response {
                    if (response.isSuccess) {
                        self.currentPage += 1
                        self.states = .result(response.data?.photos ?? [])
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
