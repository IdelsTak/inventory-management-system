package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.controllers.utils.FormattedPriceCell;
import husnain.ims.app.crud.Inventory;
import husnain.ims.app.model.Part;
import husnain.ims.app.model.Product;
import husnain.ims.app.ui.InventoryManagementApp;
import husnain.ims.app.ui.controllers.utils.PropertyRatio;
import husnain.ims.app.ui.controllers.utils.BoundablePropertyRatio;
import husnain.ims.app.ui.controllers.utils.Named;
import husnain.ims.app.ui.controllers.utils.PlaceholderLabel;
import husnain.ims.app.ui.controllers.utils.Search;
import husnain.ims.app.ui.controllers.utils.SearchableList;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class MainFormController {

    private static final Logger LOG = Logger.getLogger(MainFormController.class.getName());
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
    private TextField searchProductsTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productInvLevelColumn;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
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

    }

    @FXML
    void addPart(ActionEvent event) {
        try {
            this.showPartDialog(null);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void modifyPart(ActionEvent event) {
        var selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (Objects.isNull(selectedPart)) {
            var alert = new Alert(Alert.AlertType.ERROR, null);

            alert.setHeaderText("Can't modify. No part is selected.");
            alert.show();
        } else {
            try {
                this.showPartDialog(selectedPart);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void deletePart(ActionEvent event) {
        var selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (Objects.isNull(selectedPart)) {
            var alert = new Alert(Alert.AlertType.ERROR, null);

            alert.setHeaderText("Can't delete. No part is selected.");
            alert.show();
        } else {
            var yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.NO);
            var noBtn = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);

            var alert = new Alert(Alert.AlertType.CONFIRMATION, null, yesBtn, noBtn);

            alert.setTitle("Delete");
            alert.setHeaderText(String.format("Deleting the part \"%s\" is non-reversible. Continue?", selectedPart.getName()));

            alert.showAndWait()
                    .filter(btn -> Objects.equals(btn, yesBtn))
                    .ifPresent(btn -> Inventory.deletePart(selectedPart));
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        try {
            this.showProductDialog(Named.DialogType.ADD);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void modifyProduct(ActionEvent event) {
        try {
            this.showProductDialog(Named.DialogType.MODIFY);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void exitApplication(ActionEvent event) {
        this.showExitDialog(event);
    }

    private void initTablePlaceholders() {
        partsTable.setPlaceholder(new PlaceholderLabel("<No parts available>"));
        productsTable.setPlaceholder(new PlaceholderLabel("<No products available>"));
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

        productsTable.widthProperty().addListener(
                new BoundablePropertyRatio(
                        productsTable.widthProperty(),
                        List.of(new PropertyRatio(productIdColumn.maxWidthProperty(), 0.11),
                                new PropertyRatio(productNameColumn.maxWidthProperty(), 0.38),
                                new PropertyRatio(productInvLevelColumn.maxWidthProperty(), 0.22),
                                new PropertyRatio(productPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );
    }

    private void showExitDialog(ActionEvent event) {
        var alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);

        alert.setTitle("Exit");
        alert.setHeaderText("Continue managing inventory?");

        alert.showAndWait()
                .filter(response -> Objects.equals(response, ButtonType.NO))
                .map(btnType -> this.doExit(event));
    }

    private Void doExit(ActionEvent event) {
        var source = (Node) event.getSource();
        var stage = (Stage) source.getScene().getWindow();
        //Close the application
        stage.close();
        //There's nothing to return
        return null;
    }

    private void showProductDialog(Named.DialogType type) throws IOException {
        var url = InventoryManagementApp.class.getResource("ProductForm.fxml");
        var loader = new FXMLLoader(url);

        loader.setController(new ProductFormController());

        var dlg = (DialogPane) loader.load();
        var alert = new Alert(Alert.AlertType.NONE);
        var saveBtn = new ButtonType("Save", ButtonBar.ButtonData.YES);

        dlg.getButtonTypes().setAll(saveBtn, ButtonType.CANCEL);
        alert.setDialogPane(dlg);

        alert.show();
    }

    private void showPartDialog(Part selectedPart) throws IOException {
        var index = Inventory.getAllParts().indexOf(selectedPart);
        var add = Objects.isNull(selectedPart);
        var url = InventoryManagementApp.class.getResource("PartForm.fxml");
        var loader = new FXMLLoader(url);
        var controller = add ? new PartFormController() : new PartFormController(selectedPart);

        loader.setController(controller);

        var dlg = (DialogPane) loader.load();
        var alert = new Alert(Alert.AlertType.NONE);
        var saveBtn = new ButtonType("Save", ButtonBar.ButtonData.YES);

        dlg.getButtonTypes().setAll(saveBtn, ButtonType.CANCEL);
        alert.setDialogPane(dlg);

        var saveButton = (Button) dlg.lookupButton(saveBtn);

        saveButton.addEventFilter(ActionEvent.ACTION, handler -> {
            if (!controller.getInputErrors().isEmpty()) {
                handler.consume();
                controller.updateErrorText();
            } else {
                if (add) {
                    Inventory.addPart(controller.getPart());
                } else {
                    Inventory.updatePart(index, controller.getPart());
                }
            }
        });

        alert.show();
    }

}
