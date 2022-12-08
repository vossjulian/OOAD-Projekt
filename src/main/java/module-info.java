module de.hsos.ooadproject {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.material2;
  requires eu.hansolo.tilesfx;
  requires MaterialFX;

  opens de.hsos.ooadproject to javafx.fxml;
  exports de.hsos.ooadproject;
  exports de.hsos.ooadproject.controller;
  opens de.hsos.ooadproject.controller to javafx.fxml;
  exports de.hsos.ooadproject.datamodel;
  opens de.hsos.ooadproject.datamodel to javafx.fxml;
  exports de.hsos.ooadproject.uimodel;
  opens de.hsos.ooadproject.uimodel to javafx.fxml;
  exports de.hsos.ooadproject.api;
  opens de.hsos.ooadproject.api to javafx.fxml;
  exports de.hsos.ooadproject.interfaces;
  opens de.hsos.ooadproject.interfaces to javafx.fxml;
}