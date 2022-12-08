package de.hsos.ooadproject.datamodel;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Depot stellt die Werte eines Portfolios dar.
 * verantwortlich: Janek Büscher
 * mitwirkend: Patrick Felschen
 */
public class Depot {
  private final ObservableList<Posten> posten = FXCollections.observableArrayList();
  private final FloatProperty askSum = new SimpleFloatProperty(this, "ask");

  public Depot() {
    this.setAskSum(0);
  }

  public double getAskSum() {
    return askSum.get();
  }

  public void setAskSum(float askSum) {
    this.askSum.set(askSum);
  }

  /**
   * Berechnet neue Summe aus Ask Werten
   */
  private void updateAskSum() {
    float newAsk = 0;
    for (Posten p : posten) {
      newAsk += p.askValueSumProperty().get();
    }
    this.askSumProperty().set(newAsk);
  }

  public FloatProperty askSumProperty() {
    return askSum;
  }

  /**
   * Sucht aus den verfügbaren Posten nach einer Aktie.
   *
   * @param stock Aktie, nach welcher in den Posten gesucht werden soll.
   * @return Falls Aktie in Posten gefunden wurde, wird der Posten zurückgegeben.
   */
  public Posten getPosten(Stock stock) {
    for (Posten p : posten) {
      if (p.getStock().equals(stock)) {
        return p;
      }
    }
    return new Posten(new Stock("", ""), 0);
  }

  public List<Posten> getAllPosten() {
    return posten;
  }

  /**
   * Legt einen neuen Posten an, falls noch kein Posten zu der Aktie existiert, erhöht andernfalls die Anzahl der Aktien im Posten.
   *
   * @param stock  Hinzuzufügende Aktie.
   * @param number Anzahl der Aktien.
   */
  public void addPosten(Stock stock, int number) {
    for (Posten p : this.posten) {
      // Posten existiert, Aktienanzahl erhöhen
      if (p.getStock().equals(stock)) {
        p.setNumber(p.getNumber() + number);
        return;
      }
    }
    // Posten existiert nicht, Posten neu anlegen
    Posten newPosten = new Posten(stock, number);
    // Stock Ask Änderungen sollen zum Neuberechnen des Gesamt Asks führen
    newPosten.getStock().askProperty().addListener((obs, o, n) -> {
      this.updateAskSum();
    });
    this.posten.add(new Posten(stock, number));
  }

  /**
   * Entfernt oder verringert die Anzahl an Aktien in einem Posten.
   *
   * @param stock  Zu entfernende oder verringernde Aktie.
   * @param number Anzahl der Aktien.
   */
  public void removePosten(Stock stock, int number) {
    for (Posten p : this.posten) {
      if (p.getStock().equals(stock)) {
        // Posten entfernen
        if (p.getNumber() == number) {
          this.posten.remove(p);
        }
        // Aktienanzahl zu gering
        if (p.getNumber() < number) {
          System.out.println("Anzahl der Aktien zu gering!");
          return;
        }
        // Aktienanzahl verringern
        p.setNumber(p.getNumber() - number);
        break;
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Depot depot = (Depot) o;

    return posten.equals(depot.posten);
  }

  @Override
  public int hashCode() {
    return posten.hashCode();
  }
}
