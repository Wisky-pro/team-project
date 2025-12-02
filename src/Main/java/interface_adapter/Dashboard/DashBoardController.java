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
        // There is NO account button in DashboardView,
        // so no listeners for account info are added here.
    }
}
