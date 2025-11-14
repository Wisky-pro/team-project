import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder appBuilder = new AppBuilder();

            appBuilder.addLoginUseCase(); // initializes controller, interactor, presenter, ViewModel

            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(appBuilder.getLoginView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
