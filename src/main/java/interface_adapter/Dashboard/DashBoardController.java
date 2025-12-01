package interface_adapter.Dashboard;

import view.AccountInfoView;

import interface_adapter.ViewManagerModel;

public class DashBoardController {

    private final DashboardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final AccountInfoView accountInfoView;

    public DashBoardController(DashboardViewModel viewModel,
                               ViewManagerModel viewManagerModel,
                               AccountInfoView accountInfoView) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.accountInfoView = accountInfoView;
        addListeners();
    }

    private void addListeners() {
        // When the Account Info button is clicked, refresh and switch view
        viewModel.getDashboardView().getAccountButton().addActionListener(e -> {
            accountInfoView.refresh();
            viewManagerModel.setActiveView("accountInfo");
        });
    }
}
