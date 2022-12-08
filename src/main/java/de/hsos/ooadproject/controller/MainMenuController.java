package de.hsos.ooadproject.controller;

import de.hsos.ooadproject.Router;
import de.hsos.ooadproject.interfaces.Routable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * MainMenuController implementiert die Logik der Navigationsleiste
 * und ermöglicht das Wechseln der Szenen.
 * verantwortlich: Julian Voß
 * mitwirkend: Patrick Felschen
 */
public class MainMenuController extends Routable {
  @FXML
  private void showWatchList(ActionEvent e) throws IOException {
    Router.getInstance().pushRoute("watchList");
  }

  @FXML
  private void showStockList(ActionEvent e) throws IOException {
    Router.getInstance().pushRoute("stockList");
  }

  @FXML
  private void showPortfolio(ActionEvent e) throws IOException {
    Router.getInstance().pushRoute("portfolio");
  }

}
