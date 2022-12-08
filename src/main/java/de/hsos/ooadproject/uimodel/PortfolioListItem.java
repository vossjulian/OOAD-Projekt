package de.hsos.ooadproject.uimodel;

import de.hsos.ooadproject.MainApp;
import de.hsos.ooadproject.datamodel.Posten;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * PortfolioListItem stellt ein Listenelement im Portfolio dar.
 * verantwortlich: Julian Voß
 * mitwirkend: Patrick Felschen
 */
public class PortfolioListItem extends ListCell<Posten> {
  @FXML
  private Label nameLabel, symbolLabel, countValue, latestPriceValue, amountInEURValue;
  @FXML
  private GridPane gridPane;
  private FXMLLoader loader;

  /**
   * Legt Werte des Listenlements fest
   *
   * @param posten The new item for the cell.
   * @param empty  whether or not this cell represents data from the list. If it
   *               is empty, then it does not represent any domain data, but is a cell
   *               being used to render an "empty" row.
   */
  @Override
  protected void updateItem(Posten posten, boolean empty) {
    super.updateItem(posten, empty);

    // Laden des Views
    if (empty || posten == null) {
      setText(null);
      setGraphic(null);
      return;
    }

    this.loadView();
    this.bindLabels(posten);

    setText(null);
    setGraphic(gridPane);
  }

  /**
   * Auf Änderungen der Aktie reagieren und Labels neu setzen
   *
   * @param posten aktueller Posten der angezeigt werden soll
   */
  void bindLabels(Posten posten) {
    nameLabel.textProperty().bind(Bindings.convert(posten.getStock().nameProperty()));
    symbolLabel.textProperty().bind(Bindings.convert(posten.getStock().symbolProperty()));
    countValue.textProperty().bind(Bindings.convert(posten.numberProperty()));
    amountInEURValue.textProperty().bind(Bindings.convert(posten.askValueSumProperty()).concat(" EUR"));
    latestPriceValue.textProperty().bind(Bindings.convert(posten.getStock().askProperty()).concat(" EUR"));
  }

  /**
   * Lädt View und setzt Controller
   */
  void loadView() {
    if (loader == null) {
      loader = new FXMLLoader(MainApp.class.getResource("portfolio-list-item.fxml"));
      loader.setController(this);
      try {
        loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
