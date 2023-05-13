package Controller;

import Repository.PlayerRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    @FXML
    private StackedBarChart<String, Integer> stackedBar;

    public void addToChart(){
        final XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
        series1.setName("Penalties");
        final XYChart.Series<String,Integer> series2 = new XYChart.Series<>();
        series2.setName("Non-Penalty-Goals");
        try {
            PlayerRepository.getTopScorers(series1,series2);
            stackedBar.getData().addAll(series2,series1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addToChart();
    }
}
