package interface_adapter.Dashboard;

import interface_adapter.ViewManagerModel;

public class DashBoardController {

    private final DashboardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DashBoardController(DashboardViewModel viewModel,
                               ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;

    }
}