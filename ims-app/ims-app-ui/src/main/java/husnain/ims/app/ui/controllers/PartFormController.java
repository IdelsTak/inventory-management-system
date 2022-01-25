package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.controllers.utils.Named;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Husnain Arif
 */
public class PartFormController {

    private final Named.DialogType type;
    @FXML
    private Label titleLabel;

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
        titleLabel.setText(String.format("%s Product", type.toString()));
    }
}
