package interface_adapter.Dashboard;

import view.DashboardView;
import view.AccountInfoView;
import interface_adapter.ViewManagerModel;

public class DashBoardController {

    private final DashboardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardView dashboardView;

    public DashBoardController(DashboardViewModel viewModel,
                               ViewManagerModel viewManagerModel,
                               DashboardView dashboardView) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardView = dashboardView;
        addListeners();
    }

    private void addListeners() {
        // There is no account button so we don't need to put any listeners for account info.
    }
}
