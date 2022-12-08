package de.hsos.ooadproject.controller;

import de.hsos.ooadproject.Router;
import de.hsos.ooadproject.api.UserManager;
import de.hsos.ooadproject.datamodel.Depot;
import de.hsos.ooadproject.datamodel.Stock;
import de.hsos.ooadproject.interfaces.Routable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * verantwortlich: Janek Büscher
 * mitwirkend: Patrick Felschen
 */
public class StockBuyController extends Routable implements Initializable {
  @FXML
  private Label lbStockName, lbBid, lbAmount;
  @FXML
  private Spinner<Integer> spAmount;
  private Depot depot;
  private Stock stock;

  /**
   * Beim Aufrufen werden die Depot-Daten geladen
   *
   * @param location
   * The location used to resolve relative paths for the root object, or
   * {@code null} if the location is not known.
   *
   * @param resources
   * The resources used to localize the root object, or {@code null} if
   * the root object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    UserManager userManager = UserManager.getInstance();
    this.depot = userManager.getDepot();
  }

  /**
   * Lädt die Werte der Aktie und setzt sie in der Oberfläche
   *
   * @param stock
   */
  private void setStock(Stock stock) {
    this.stock = stock;

    // Spinner einstellen
    SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);

    this.spAmount.setValueFactory(valueFactory);

    // Bid Wert mit Anzahl multiplizieren
    NumberBinding total = stock.bidProperty().multiply(
            ReadOnlyIntegerProperty.readOnlyIntegerProperty(
                    spAmount.valueProperty()
            )
    );

    // Labels setzen
    this.lbStockName.setText(stock.getName());
    this.lbAmount.textProperty().bind(Bindings.convert(total));
    this.lbBid.textProperty().bind(Bindings.convert(stock.bidProperty()));
  }

  /**
   * Ändert die Posten im Depot um die gekauften Aktien und schickt den Nutzer zurück aufs Portfolio
   *
   * @param e
   * @throws IOException
   */
  @FXML
  private void buyStock(ActionEvent e) throws IOException {
    this.depot.addPosten(this.stock, this.spAmount.getValue());
    Router.getInstance().pushRoute("portfolio");
  }

  @FXML
  private void cancel(ActionEvent e) {
    Router.getInstance().popAllPopups();
  }

  @Override
  public void setData(Object data) {
    setStock((Stock) data);
  }
}
