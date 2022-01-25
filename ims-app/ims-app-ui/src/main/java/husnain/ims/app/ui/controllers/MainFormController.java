package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.InventoryManagementApp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    }

    @FXML
    void addProduct(ActionEvent event) {
        try {
            this.showProductDialog();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void exitApplication(ActionEvent event) {
        this.showExitDialog(event);
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

    private void showProductDialog() throws IOException {
        var url = InventoryManagementApp.class.getResource("ProductForm.fxml");
        var loader = new FXMLLoader(url);

        DialogPane dlg = loader.load();

        loader.setController(new ProductFormController());
        
        var alert = new Alert(Alert.AlertType.NONE);
        var saveBtn = new ButtonType("Save", ButtonBar.ButtonData.YES);
        
        dlg.getButtonTypes().setAll(saveBtn, ButtonType.CANCEL);
        alert.setDialogPane(dlg);
        
        alert.showAndWait();
    }

}
