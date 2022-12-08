package de.hsos.ooadproject.api;

import de.hsos.ooadproject.datamodel.Depot;

import java.util.ArrayList;
import java.util.List;

/**
 * UserManager stellt die Werte eines Nutzers dar.
 * verantwortlich: Patrick Felschen
 */
public class UserManager {
  private static final List<String> watchListStockIds = new ArrayList<>();
  private static final Depot depot = new Depot();
  private static UserManager singleInstance = null;

  /**
   * User mit Beispielwerten füllen
   */
  private UserManager() {
    watchListStockIds.addAll(List.of("DE0005545503", "DE0005408116", "DE0007471377", "DE0006569403"));
    depot.addPosten(StockManager.getInstance().getStockList().get(0), 115);
    depot.addPosten(StockManager.getInstance().getStockList().get(2), 88);
    depot.addPosten(StockManager.getInstance().getStockList().get(3), 9);
    depot.addPosten(StockManager.getInstance().getStockList().get(4), 78);
    depot.addPosten(StockManager.getInstance().getStockList().get(5), 61);
    depot.addPosten(StockManager.getInstance().getStockList().get(6), 54);
  }

  /**
   * Zur Umsetzung des Singleton Patterns.
   *
   * @return Instanz des UserManagers.
   */
  public static UserManager getInstance() {
    if (singleInstance == null) {
      singleInstance = new UserManager();
    }
    return singleInstance;
  }

  public List<String> getWatchListStockIds() {
    return watchListStockIds;
  }

  /**
   * Fügt ein Aktien-Symbol einmalig einer List hinzu.
   *
   * @param stockId Aktien-Symbol
   */
  public void addStockToWatchList(String stockId) {
    if (watchListStockIds.contains(stockId)) {
      return;
    }
    watchListStockIds.add(stockId);
  }

  /**
   * Entfernt Aktien-Symbol von Liste
   *
   * @param stockId Aktien-Symbol
   */
  public void removeStockFromWatchList(String stockId) {
    watchListStockIds.remove(stockId);
  }

  public Depot getDepot() {
    return depot;
  }

}
