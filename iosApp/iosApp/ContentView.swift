import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
	var body: some View {
        NavigationView {
                    listView()
                    .navigationBarTitle("Galaxy Images")
                    .navigationBarItems(trailing:
                        Button("Load") {
                            self.viewModel.loadImages(query: "galaxy",page: "0")
                    })
                }
	}
    
    private func listView() -> AnyView {
            switch viewModel.states {
            case .empty:
                return AnyView(Spacer())
            case .loading:
                return AnyView(Text("Loading...").multilineTextAlignment(.center))
            case .result(let response):
                return
                    AnyView(Text(response[0].url).multilineTextAlignment(.center))
//                    AnyView(List(response) {
//                        data in ItemRow(photoData: data)
//                })
            case .error(let description):
                return AnyView(Text(description).multilineTextAlignment(.center))
            }
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
        let networkProvider: UseCaseProvider
        @Published var states = States.empty
        
        init(networkProvider: UseCaseProvider) {
            self.networkProvider = UseCaseProvider.init()
        }

        func loadImages(query: String, page: String) {
            self.states = .loading
            networkProvider.networkUseCase.getNetworkData(query: query, page: page, completionHandler: { response, error in
                if let response = response {
                    if (response.isSuccess) {
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
