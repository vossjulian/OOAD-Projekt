package de.hsos.ooadproject;

import de.hsos.ooadproject.interfaces.Routable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Router ermöglicht das Wechseln zwischen verschiedenen Szenen.
 * verantwortlich: Patrick Felschen
 * mitwirkend: Julian Voß, Janek Büscher
 */
public class Router {
  private static Router singleInstance = null;
  private final Map<String, String> routes;
  private final Stack<String> navStack;
  private final Stack<Popup> popupStack;
  private Stage primaryStage;

  /**
   * Erzeugt einen Router speichert in einer HashMap die einzelnen Routen.
   */
  private Router() {
    this.routes = new HashMap<>();
    this.navStack = new Stack<>();
    this.popupStack = new Stack<>();

    this.routes.put("watchList", "watch-list-view.fxml");
    this.routes.put("stockList", "stock-list-view.fxml");
    this.routes.put("portfolio", "portfolio-view.fxml");
    this.routes.put("stockDetails", "stock-details-view.fxml");
    this.routes.put("stockBuy", "stock-buy-view.fxml");
    this.routes.put("stockSell", "stock-sell-view.fxml");
  }

  /**
   * @param primaryStage Stage als primärer Ausgangspunkt.
   */
  public static void bind(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;
  }

  /**
   * Zur Umsetzung des Singleton Patterns.
   *
   * @return Instanz des Routers.
   */
  public static Router getInstance() {
    if (singleInstance == null) {
      singleInstance = new Router();
    }

    return singleInstance;
  }

  /**
   * Lädt View und Controller einer Route und stellt sicher, dass sich die Fensterdimensionen nicht verändern.
   *
   * @param routeName Der Name der Route. Ist in "routes" mit dem Namen einer FXML-Datei verknüpft.
   * @param data      Daten, welche dem Controller des Views übergeben werden können.
   * @throws IOException
   */
  private void navigate(String routeName, Object data) throws IOException {
    String fxmlfile = this.routes.get(routeName);
    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlfile));
    Parent root = loader.load();

    Routable routable = loader.getController();
    if (routable != null) {
      routable.setData(data);
    }

    double height;
    double width;

    if (primaryStage.getScene() == null) {
      height = 800;
      width = 1000;
    } else {
      height = primaryStage.getScene().getWindow().getHeight();
      width = primaryStage.getScene().getWindow().getWidth();
    }

    Scene scene = new Scene(root);

    this.primaryStage.setScene(scene);

    primaryStage.setHeight(height);
    primaryStage.setWidth(width);

    primaryStage.setMinHeight(400);
    primaryStage.setMinWidth(720);

    this.popAllPopups();
    this.primaryStage.show();
  }

  /**
   * Lädt, falls vorhanden, vorherige Route vom Stack.
   *
   * @param data Daten, welche der Route übergeben werden können.
   * @throws IOException
   */
  public void popRoute(Object data) throws IOException {
    if (navStack.isEmpty()) return;
    navStack.pop();
    String routeName = navStack.peek();
    this.navigate(routeName, data);
  }

  /**
   * Lädt vorherige Route ohne übergebene Daten.
   *
   * @throws IOException
   */
  public void popRoute() throws IOException {
    popRoute(null);
  }

  /**
   * Fügt dem Stack eine Route hinzu. Routen können über den Stack von "oben nach unten" abgearbeitet werden. Ermöglicht so einfach zurück Navigieren.
   *
   * @param routeName Bezeichnung der Route.
   * @param data      Daten, welche der Route übergeben werden können.
   * @throws IOException
   */
  public void pushRoute(String routeName, Object data) throws IOException {
    if (!routes.containsKey(routeName)) return;
    this.navigate(routeName, data);
    this.navStack.push(routeName);
  }

  /**
   * Fügt dem Stack eine Route ohne Daten hinzu.
   *
   * @param routeName Bezeichnung der Route.
   * @throws IOException
   */
  public void pushRoute(String routeName) throws IOException {
    pushRoute(routeName, null);
  }

  /**
   * Öffnet Popup über aktueller Szene
   *
   * @param popupFxml fxml-Datei, welche als View verwendet werden soll
   * @param data      optionale Daten um Popup anzuzeigen
   * @throws IOException wenn fxml-Datei nicht geladen werden konnte
   */
  public void pushPopup(String popupFxml, Object data) throws IOException {
    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(popupFxml));
    Parent root = loader.load();

    Routable sbc = loader.getController();
    sbc.setData(data);

    Popup popup = new Popup();
    popup.getContent().addAll(root);

    // Popup positionieren
    popup.setX(primaryStage.getX() + (primaryStage.getWidth() / 2) - (popup.getWidth() / 2));
    popup.setY(primaryStage.getY() + (primaryStage.getHeight() / 2) - (popup.getHeight() / 2));
    popup.setAutoHide(true);

    popup.show(primaryStage);
    popupStack.push(popup);
  }

  /**
   * Schließt alle offenen Popups
   */
  public void popAllPopups() {
    for (Popup p : popupStack) {
      p.hide();
    }
  }
}
