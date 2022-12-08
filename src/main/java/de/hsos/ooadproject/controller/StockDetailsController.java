package de.hsos.ooadproject.controller;

import de.hsos.ooadproject.Router;
import de.hsos.ooadproject.api.StockManager;
import de.hsos.ooadproject.datamodel.HistoryPoint;
import de.hsos.ooadproject.datamodel.Stock;
import de.hsos.ooadproject.interfaces.Routable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * StockDetailsController stellt Werte einer Aktie in Text- und Diagrammform dar.
 * verantwortlich: Patrick Felschen
 * mitwirkend: Janek Büscher
 */
public class StockDetailsController extends Routable {
  @FXML
  private Label lbStockName, lbSymbol, lbVortag, lbBid, lbAsk, lbPercent, lbTime, lbPlusMinus;
  @FXML
  private LineChart<String, Number> lineChart;
  private XYChart.Series<String, Number> series;
  private Stock stock;

  /**
   * Setzt das aus der Route übergebene Element.
   *
   * @param data Übergebene Aktie.
   */
  @Override
  public void setData(Object data) {
    if (data == null) return;
    initialize((Stock) data);
  }

  /**
   * @param stock Anzuzeigende Aktie.
   */
  private void initialize(Stock stock) {
    this.stock = stock;
    this.series = new XYChart.Series<>();

    this.bindLabels();
    this.createLineChart();

    this.setHistoryLastMonth(null);
  }

  /**
   * Koppeln der Properties an UI-Elemente ermöglicht dynamische Aktualisierung.
   */
  private void bindLabels() {
    lbStockName.textProperty().bind(Bindings.convert(stock.nameProperty()));
    lbSymbol.textProperty().bind(Bindings.convert(stock.symbolProperty()));
    lbVortag.textProperty().bind(Bindings.convert(stock.vortagProperty()));
    lbAsk.textProperty().bind(Bindings.convert(stock.askProperty()));
    lbBid.textProperty().bind(Bindings.convert(stock.bidProperty()));
    lbPercent.textProperty().bind(Bindings.convert(stock.percentProperty()));
    lbPlusMinus.textProperty().bind(Bindings.convert(stock.plusMinusProperty()));
    lbTime.textProperty().bind(Bindings.convert(stock.dateTimeProperty()));
  }

  /**
   * Diagramm-Daten aktualisieren automatisch, wenn dem Preisverlauf ein neuer Preis hinzugefügt wurde.
   */
  public void createLineChart() {
    stock.getHistory().addListener((ListChangeListener<HistoryPoint>) c -> {
      series.getData().clear();
      for (int i = 0; i < stock.getHistory().size(); i++) {
        series.getData().add(new XYChart.Data<>(
                        stock.getHistory().get(i).getDate(),
                        stock.getHistory().get(i).getAsk()
                )
        );
      }

    });
    this.lineChart.setCreateSymbols(false);
    this.lineChart.getData().add(series);
  }

  /**
   * Navigiert einen Screen zurück.
   *
   * @param e Aktion
   * @throws IOException
   */
  @FXML
  private void navigateBack(ActionEvent e) throws IOException {
    Router.getInstance().popRoute();
  }

  /**
   * Öffnet Fenster zum Kaufen von Aktie.
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void stockBuy(ActionEvent actionEvent) throws IOException {
    Router.getInstance().pushPopup("stock-buy-view.fxml", this.stock);
  }

  /**
   * Öffnet Fenster zum Verkaufen von Aktie.
   *
   * @param actionEvent
   * @throws IOException
   */
  @FXML
  private void stockSell(ActionEvent actionEvent) throws IOException {
    Router.getInstance().pushPopup("stock-sell-view.fxml", this.stock);
    //Router.getInstance().pushRoute("stockSell", this.stock);
  }

  /**
   * Zeigt im Diagramm Preisverlauf der letzten Woche an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastWeek(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("1 Woche");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.HALF_DAYS);
  }

  /**
   * Zeigt im Diagramm Preisverlauf des letzten Monats an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastMonth(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("1 Monat");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.DAYS);
  }

  /**
   * Zeigt im Diagramm Preisverlauf der letzten sechs Monate an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastSixMonth(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("6 Monate");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.DAYS);
  }

  /**
   * Zeigt im Diagramm Preisverlauf des letzten Jahres an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastYear(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("1 Jahr");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.DAYS);
  }

  /**
   * Zeigt im Diagramm Preisverlauf der letzten drei Jahre an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastThreeYear(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(3, ChronoUnit.YEARS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("3 Jahre");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.DAYS);
  }

  /**
   * Zeigt im Diagramm Preisverlauf der letzten fünf Jahre an.
   *
   * @param e
   */
  @FXML
  private void setHistoryLastFiveYear(ActionEvent e) {
    LocalDateTime start = LocalDateTime.now().minus(5, ChronoUnit.YEARS);
    LocalDateTime end = LocalDateTime.now();
    this.series.setName("5 Jahre");
    StockManager.getInstance().setStockHistory(stock, start, end, ChronoUnit.DAYS);
  }

}
