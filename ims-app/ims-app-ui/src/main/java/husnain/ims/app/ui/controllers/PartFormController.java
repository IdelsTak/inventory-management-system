package husnain.ims.app.ui.controllers;

import husnain.ims.app.model.InHouse;
import husnain.ims.app.model.OutSourced;
import husnain.ims.app.model.Part;
import husnain.ims.app.ui.controllers.utils.Named;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
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
    private RadioButton inhouseRadioButton;
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
    @FXML
    private Label companyOrMachineIdTextField;
    private final Part part;

    public PartFormController() {
        this(Named.DialogType.ADD, null);
    }

    public PartFormController(Part part) {
        this(Named.DialogType.MODIFY, part);
    }

    public PartFormController(Named.DialogType type, Part part) {
        this.type = type;
        this.part = part;
    }

    public Part getPart() {
        var name = nameTextField.getText();
        var price = Double.parseDouble(priceTextField.getText());
        var stock = Integer.parseInt(stockTextField.getText());
        var minStock = Integer.parseInt(minStockTextField.getText());
        var maxStock = Integer.parseInt(maxStockTextField.getText());

        if (inhouseRadioButton.isSelected()) {
            new InHouse(0, name, price, stock, minStock, maxStock);
        } else {
            new OutSourced(0, name, price, stock, minStock, maxStock);
        }
        return part;
    }

    @FXML
    void initialize() {
        this.initTitle();

        companyOrMachineIdTextField.textProperty().bind(this.createFieldForProductType());
    }

    private void initTitle() {
        titleLabel.setText(String.format("%s Part", type.toString()));
    }

    private StringBinding createFieldForProductType() {
        return Bindings.createStringBinding(
                () -> {
                    var inhouseSelected = Objects.equals(
                            inhouseRadioButton,
                            productTypeToggleGrp.getSelectedToggle()
                    );
                    return inhouseSelected ? "Machine ID" : "Company Name";
                },
                productTypeToggleGrp.selectedToggleProperty()
        );
    }

}
