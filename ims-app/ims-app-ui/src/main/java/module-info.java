
module ims.ui {
    requires ims.crud;
    requires ims.model;
    requires java.logging;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.base;
    //These exports are here so that the JavaDoc
    //generation will be able to output the JavaDoc
    //for the classes in these packages
    exports husnain.ims.app.ui;
    exports husnain.ims.app.ui.controllers;
    exports husnain.ims.app.ui.controllers.utils;
}
