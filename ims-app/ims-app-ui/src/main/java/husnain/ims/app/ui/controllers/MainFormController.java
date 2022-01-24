package husnain.ims.app.ui.controllers;

import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class MainFormController {

    private static final Logger LOG = Logger.getLogger(MainFormController.class.getName());
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

    private void initColumnBinding(DoubleExpression widthProp, Stream<Pair<Property, Double>> pairs) {
        pairs.forEach(p -> this.bindColumnWidth(widthProp, p.getKey(), p.getValue()));
    }

    private void bindColumnWidth(DoubleExpression tableProp, Property colProp, double percent) {
        if (colProp.isBound()) {
            colProp.unbind();
        }

        colProp.bind(tableProp.multiply(percent));
    }

}
