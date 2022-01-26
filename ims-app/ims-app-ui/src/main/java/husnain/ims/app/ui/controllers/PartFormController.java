package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.controllers.utils.Named;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Husnain Arif
 */
public class PartFormController {

    private final Named.DialogType type;
    @FXML
    private Label titleLabel;
    @FXML
    private RadioButton InhouseRadioButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField maxStockTextField;
    @FXML
    private TextField minStockTextField;
    @FXML
    private TextField nameOrMachineIdTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField priceTextField;
    @FXML
    private ToggleGroup productTypeToggleGrp;
    @FXML
    private TextField stockTextField;

    public PartFormController() {
        this(Named.DialogType.ADD);
    }

    public PartFormController(Named.DialogType type) {
        this.type = type;
    }

    @FXML
    void initialize() {
        this.initTitle();
    }

    private void initTitle() {
        titleLabel.setText(String.format("%s Part", type.toString()));
    }
}
