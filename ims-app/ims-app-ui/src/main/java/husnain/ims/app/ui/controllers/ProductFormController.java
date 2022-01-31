package husnain.ims.app.ui.controllers;

import husnain.ims.app.crud.Inventory;
import husnain.ims.app.model.Part;
import husnain.ims.app.model.Product;
import husnain.ims.app.ui.controllers.utils.PropertyRatio;
import husnain.ims.app.ui.controllers.utils.BoundablePropertyRatio;
import husnain.ims.app.ui.controllers.utils.FormattedPriceCell;
import husnain.ims.app.ui.controllers.utils.InputError;
import husnain.ims.app.ui.controllers.utils.Named;
import husnain.ims.app.ui.controllers.utils.PlaceholderLabel;
import husnain.ims.app.ui.controllers.utils.Search;
import husnain.ims.app.ui.controllers.utils.SearchableList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class ProductFormController {

    private final Named.DialogType type;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField prodIdTextField;
    @FXML
    private TextField searchPartsTextField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
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
    private final ObservableSet<InputError> inputErrors;
    private final Product product;

    public ProductFormController() {
        this(Named.DialogType.ADD, null);
    }

    public ProductFormController(Product product) {
        this(Named.DialogType.MODIFY, Objects.requireNonNull(product, "Product to modify should not be null"));
    }
    
    private ProductFormController(Named.DialogType type, Product product) {
        this.type = type;
        this.product = product;
        this.inputErrors = FXCollections.observableSet(new LinkedHashSet<>());
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    void initialize() {
        this.initTitle(type.toString());
        this.initFocus();
        this.initTablePlaceholders();
        this.setupColumnWidths();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partPriceColumn.setCellFactory(callBck -> new FormattedPriceCell());

        partsTable.setItems(Inventory.getAllParts());

        searchPartsTextField.textProperty().addListener((obs, oldText, newText) -> {
            var sl = new SearchableList<Part>(
                    Inventory.getAllParts(),
                    newText,
                    Function.identity(),
                    new Search<>(Inventory::lookupPart, Inventory::lookupPart)
            );
            var filtered = sl.getFiltered();

            if (filtered.isEmpty()) {
                var alert = new Alert(Alert.AlertType.WARNING, null);

                alert.setHeaderText("No part found for query: \"%s\"".formatted(searchPartsTextField.getText()));
                alert.show();

                searchPartsTextField.setText(null);
                partsTable.requestFocus();
            } else {
                partsTable.setItems(filtered);
            }
        });
        
        if (Objects.isNull(product)) {
            prodIdTextField.setText("Auto Gen - Disabled");
        } 
    }

    private void initTitle(String typeName) {
        titleLabel.setText(String.format("%s Product", typeName));
    }

    private void initFocus() {
        prodIdTextField.skinProperty().addListener((o) -> prodIdTextField.requestFocus());
    }

    private void initTablePlaceholders() {
        partsTable.setPlaceholder(new PlaceholderLabel("<No parts available>"));
        assocPartsTable.setPlaceholder(new PlaceholderLabel("<No associated parts available>"));
    }

    private void setupColumnWidths() {
        partsTable.widthProperty().addListener(
                new BoundablePropertyRatio(
                        partsTable.widthProperty(),
                        List.of(new PropertyRatio(partIdColumn.maxWidthProperty(), 0.11),
                                new PropertyRatio(partNameColumn.maxWidthProperty(), 0.38),
                                new PropertyRatio(partInvLevelColumn.maxWidthProperty(), 0.22),
                                new PropertyRatio(partPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );

        assocPartsTable.widthProperty().addListener(
                new BoundablePropertyRatio(
                        assocPartsTable.widthProperty(),
                        List.of(new PropertyRatio(assocPartIdColumn.maxWidthProperty(), 0.11),
                                new PropertyRatio(assocPartNameColumn.maxWidthProperty(), 0.38),
                                new PropertyRatio(assocInvLevelColumn.maxWidthProperty(), 0.22),
                                new PropertyRatio(assocPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );
    }

}
