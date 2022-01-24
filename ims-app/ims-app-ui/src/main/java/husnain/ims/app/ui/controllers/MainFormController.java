package husnain.ims.app.ui.controllers;

import java.util.stream.Stream;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class MainFormController {

    @FXML
    private TextField prodIdTextField;
    @FXML
    private TableColumn<?, ?> assocInvLevelColumn;
    @FXML
    private TableColumn<?, ?> assocPartIdColumn;
    @FXML
    private TableColumn<?, ?> assocPartNameColumn;
    @FXML
    private TableView<?> assocPartsTable;
    @FXML
    private TableColumn<?, ?> assocPriceColumn;
    @FXML
    private TableColumn<?, ?> invLevelColumn;
    @FXML
    private TableColumn<?, ?> partIdColumn;
    @FXML
    private TableColumn<?, ?> partNameColumn;
    @FXML
    private TableView<?> partsTable;
    @FXML
    private TableColumn<?, ?> priceColumn;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        this.initFocus();
        this.initTablePlaceholders();
        this.setupColumnWidths();
    }

    private void initFocus() {
        prodIdTextField.skinProperty().addListener((o) -> prodIdTextField.requestFocus());
    }

    private void initTablePlaceholders() {
        partsTable.setPlaceholder(this.createPlaceholder("<No parts available>"));
        assocPartsTable.setPlaceholder(this.createPlaceholder("<No associated parts available>"));
    }

    private void setupColumnWidths() {
        partsTable.widthProperty().addListener((ov, t, t1) -> {
            this.initColumnBinding(
                    partsTable.widthProperty(),
                    Stream.of(
                            new Pair(partIdColumn.maxWidthProperty(), 0.12),
                            new Pair(partNameColumn.maxWidthProperty(), 0.38),
                            new Pair(invLevelColumn.maxWidthProperty(), 0.22),
                            new Pair(priceColumn.maxWidthProperty(), 0.28)
                    )
            );
        });
        assocPartsTable.widthProperty().addListener((ov, t, t1) -> {
            this.initColumnBinding(
                    assocPartsTable.widthProperty(),
                    Stream.of(
                            new Pair(assocPartIdColumn.maxWidthProperty(), 0.12),
                            new Pair(assocPartNameColumn.maxWidthProperty(), 0.38),
                            new Pair(assocInvLevelColumn.maxWidthProperty(), 0.22),
                            new Pair(assocPriceColumn.maxWidthProperty(), 0.28)
                    )
            );
        });
    }

    private Label createPlaceholder(String details) {
        var lbl = new Label(details);
        //Give the text a pale shade of gray
        //Then, increase the text size by a little bit
        lbl.setStyle("-fx-text-fill: #D3D3D3; -fx-font-size: 1.2em");

        return lbl;
    }

    private void initColumnBinding(DoubleExpression tableProp, Stream<Pair<Property, Double>> colVals) {
        colVals.forEach(colVal -> this.bindColumnWidth(tableProp, colVal.getKey(), colVal.getValue()));
    }

    private void bindColumnWidth(DoubleExpression tableProp, Property colProp, double ratio) {
        if (colProp.isBound()) {
            colProp.unbind();
        }

        colProp.bind(tableProp.multiply(ratio));
    }

}
