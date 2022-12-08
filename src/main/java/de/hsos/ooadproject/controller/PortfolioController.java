package de.hsos.ooadproject.controller;

import de.hsos.ooadproject.Router;
import de.hsos.ooadproject.api.UserManager;
import de.hsos.ooadproject.datamodel.Depot;
import de.hsos.ooadproject.datamodel.Posten;
import de.hsos.ooadproject.datamodel.Stock;
import de.hsos.ooadproject.interfaces.Routable;
import de.hsos.ooadproject.uimodel.PortfolioListItem;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * PortfolioController implementiert die Logik der Portfolio-List und des Diagramms.
 * verantwortlich: Julian Voß
 * mitwirkend: Patrick Felschen, Janek Büscher
 */
public class PortfolioController extends Routable implements Initializable {
  @FXML
  private Label portfolioValue;
  @FXML
  private PieChart chart;
  @FXML
  private ListView<Posten> portfolioList;
  private ObservableList<PieChart.Data> chartData;
  private ObservableList<Posten> listData;
  private Depot depot;

  /**
   * Beim Aufrufen des Controllers werden Aktienwerte aus den Posten dargestellt.
   *
   * @param location  The location used to resolve relative paths for the root object, or
   *                  {@code null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   *                  the root object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.depot = UserManager.getInstance().getDepot();
    this.listData = FXCollections.observableArrayList(this.depot.getAllPosten());
    this.chartData = FXCollections.observableArrayList();
    this.initializeList();
    this.initializeChart();
  }

  /**
   * Scheibendiagramm mit Daten füllen
   */
  private void initializeChart() {
    for (Posten p : listData) {
      // Diagrammdaten erstellen
      PieChart.Data data = new PieChart.Data(null, 0);
      data.setName(p.getStock().getName());
      data.pieValueProperty().bind(p.askValueSumProperty());
      this.chartData.add(data);
    }
    this.chart.setData(chartData);
    this.chart.setAnimated(true);

    //Gesamtwert (in Euro) des Depots
    portfolioValue.textProperty().bind(Bindings.convert(this.depot.askSumProperty()));
  }

  /**
   * Liste mit Daten füllen
   */
  private void initializeList() {
    // Neuer Eintrag einer Liste, Zelle ist Element von Klasse PortfolioListItem
    this.portfolioList.setCellFactory(portfolioListView -> {
      PortfolioListItem item = new PortfolioListItem();
      item.setOnMouseClicked(e -> {
        navigateToStockDetails(item.getItem().getStock());
      });
      return item;
    });
    this.portfolioList.setItems(listData);
  }

  /**
   * Öffnet Detailübersicht der ausgewählten Aktie
   *
   * @param stock Aktie zu der Details angezeigt werden sollen.
   */
  private void navigateToStockDetails(Stock stock) {
    try {
      Router.getInstance().pushRoute("stockDetails", stock);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
