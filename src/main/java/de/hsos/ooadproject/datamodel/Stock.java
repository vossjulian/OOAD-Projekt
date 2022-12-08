package de.hsos.ooadproject.datamodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Stock stellt die Werte einer Aktie dar.
 * verantwortlich: Julian Voß
 */
public class Stock {
  private final StringProperty name = new SimpleStringProperty(this, "name");
  private final StringProperty symbol = new SimpleStringProperty(this, "symbol");
  private final FloatProperty vortag = new SimpleFloatProperty(this, "vortag");
  private final FloatProperty bid = new SimpleFloatProperty(this, "bid");
  private final FloatProperty ask = new SimpleFloatProperty(this, "ask");
  private final FloatProperty percent = new SimpleFloatProperty(this, "percent");
  private final FloatProperty plusMinus = new SimpleFloatProperty(this, "plusMinus");
  private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>(this, "dateTime");
  private final ObservableList<HistoryPoint> history = FXCollections.observableArrayList();

  /**
   * Erzeugt ein Objekt einer Aktie. Parameter werden in Properties umgewandelt, welche das Koppel an andere Objekte ermöglichen.
   *
   * @param name      Name der Aktie.
   * @param symbol    Symbol der Aktie. Aktie lässt sich darüber eindeutig identifizieren.
   * @param vortag    Preis der Aktie vom Vortag.
   * @param bid       Angebotspreis der Aktie.
   * @param ask       Nachfragepreis der Aktie.
   * @param percent   Prozentuale Preisänderung der Aktie.
   * @param plusMinus Wert der Preisänderung.
   * @param dateTime  Datum der letzten Aktualisierung.
   */
  public Stock(String name, String symbol, float vortag, float bid, float ask, float percent, float plusMinus, LocalDateTime dateTime) {
    this.setName(name);
    this.setSymbol(symbol);
    this.setVortag(vortag);
    this.setBid(bid);
    this.setAsk(ask);
    this.setPercent(percent);
    this.setPlusMinus(plusMinus);
    this.setDateTime(dateTime);
  }

  public Stock(String name, String symbol) {
    this.setName(name);
    this.setSymbol(symbol);
    this.setVortag(1);
    this.setBid(1);
    this.setAsk(1);
    this.setPercent(1);
    this.setPlusMinus(1);
    this.setDateTime(LocalDateTime.now());
  }

  public LocalDateTime getDateTime() {
    return dateTime.get();
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime.set(dateTime);
  }

  public ObjectProperty<LocalDateTime> dateTimeProperty() {
    return dateTime;
  }

  public ObservableList<HistoryPoint> getHistory() {
    return history;
  }

  public void setHistory(List<HistoryPoint> history) {
    this.history.clear();
    this.history.addAll(history);
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty symbolProperty() {
    return symbol;
  }

  public FloatProperty vortagProperty() {
    return vortag;
  }

  public FloatProperty bidProperty() {
    return bid;
  }

  public FloatProperty askProperty() {
    return ask;
  }

  public FloatProperty percentProperty() {
    return percent;
  }

  public FloatProperty plusMinusProperty() {
    return plusMinus;
  }

  public String getName() {
    return this.name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getSymbol() {
    return this.symbol.get();
  }

  public void setSymbol(String symbol) {
    this.symbol.set(symbol);
  }

  public float getVortag() {
    return this.vortag.get();
  }

  public void setVortag(float vortag) {
    this.vortag.set(vortag);
  }

  public float getBid() {
    return this.bid.get();
  }

  public void setBid(float bid) {
    this.bid.set(bid);
  }

  public float getAsk() {
    return this.ask.get();
  }

  public void setAsk(float ask) {
    this.ask.set(ask);
  }

  public float getPercent() {
    return this.percent.get();
  }

  public void setPercent(float percent) {
    this.percent.set(percent);
  }

  public float getPlusMinus() {
    return this.plusMinus.get();
  }

  public void setPlusMinus(float plusMinus) {
    this.plusMinus.set(plusMinus);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Stock stock = (Stock) o;

    if (!name.equals(stock.name)) return false;
    if (!symbol.equals(stock.symbol)) return false;
    if (!vortag.equals(stock.vortag)) return false;
    if (!bid.equals(stock.bid)) return false;
    if (!ask.equals(stock.ask)) return false;
    if (!percent.equals(stock.percent)) return false;
    if (!plusMinus.equals(stock.plusMinus)) return false;
    return dateTime.equals(stock.dateTime);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + symbol.hashCode();
    result = 31 * result + vortag.hashCode();
    result = 31 * result + bid.hashCode();
    result = 31 * result + ask.hashCode();
    result = 31 * result + percent.hashCode();
    result = 31 * result + plusMinus.hashCode();
    result = 31 * result + dateTime.hashCode();
    return result;
  }
}
