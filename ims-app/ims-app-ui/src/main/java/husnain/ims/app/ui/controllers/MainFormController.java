package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.InventoryManagementApp;
import husnain.ims.app.ui.controllers.utils.ColumnWidthTweak;
import husnain.ims.app.ui.controllers.utils.Named;
import husnain.ims.app.ui.controllers.utils.PlaceholderLabel;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class MainFormController {

    private static final Logger LOG = Logger.getLogger(MainFormController.class.getName());
    @FXML
    private TableView<?> partsTable;
    @FXML
    private TableColumn<?, ?> partIdColumn;
    @FXML
    private TableColumn<?, ?> partInvLevelColumn;
    @FXML
    private TableColumn<?, ?> partNameColumn;
    @FXML
    private TableColumn<?, ?> partPriceColumn;
    @FXML
    private TableView<?> productsTable;
    @FXML
    private TableColumn<?, ?> productIdColumn;
    @FXML
    private TableColumn<?, ?> productInvLevelColumn;
    @FXML
    private TableColumn<?, ?> productNameColumn;
    @FXML
    private TableColumn<?, ?> productPriceColumn;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        this.initTablePlaceholders();
        this.setupColumnWidths();
    }

    @FXML
    void addPart(ActionEvent event) {
        try {
            this.showPartDialog(Named.DialogType.ADD);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void modifyPart(ActionEvent event) {
        try {
            this.showPartDialog(Named.DialogType.MODIFY);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
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
        partsTable.widthProperty().addListener(new ColumnWidthTweak(
                        partsTable.widthProperty(),
                        List.of(
                                new Pair(partIdColumn.maxWidthProperty(), 0.12),
                                new Pair(partNameColumn.maxWidthProperty(), 0.38),
                                new Pair(partInvLevelColumn.maxWidthProperty(), 0.22),
                                new Pair(partPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );

        productsTable.widthProperty().addListener(new ColumnWidthTweak(
                        productsTable.widthProperty(),
                        List.of(
                                new Pair(productIdColumn.maxWidthProperty(), 0.12),
                                new Pair(productNameColumn.maxWidthProperty(), 0.38),
                                new Pair(productInvLevelColumn.maxWidthProperty(), 0.22),
                                new Pair(productPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );
    }

    private void showExitDialog(ActionEvent event) {
        var alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);

        alert.setTitle("Exit");
        alert.setHeaderText("Continue managing inventory?");

        alert.showAndWait()
                .filter(response -> response == ButtonType.NO)
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

        loader.setController(new ProductFormController(type));

        DialogPane dlg = loader.load();

        var alert = new Alert(Alert.AlertType.NONE);
        var saveBtn = new ButtonType("Save", ButtonBar.ButtonData.YES);

        dlg.getButtonTypes().setAll(saveBtn, ButtonType.CANCEL);
        alert.setDialogPane(dlg);

        alert.showAndWait();
    }

    private void showPartDialog(Named.DialogType type) throws IOException {
        var url = InventoryManagementApp.class.getResource("PartForm.fxml");
        var loader = new FXMLLoader(url);

        loader.setController(new PartFormController(type));

        DialogPane dlg = loader.load();

        var alert = new Alert(Alert.AlertType.NONE);
        var saveBtn = new ButtonType("Save", ButtonBar.ButtonData.YES);

        dlg.getButtonTypes().setAll(saveBtn, ButtonType.CANCEL);
        alert.setDialogPane(dlg);

        alert.showAndWait();
    }

}
