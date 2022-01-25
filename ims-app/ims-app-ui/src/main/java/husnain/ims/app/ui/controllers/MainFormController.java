package husnain.ims.app.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class MainFormController {

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
    void exitApplication(ActionEvent event) {
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

}
