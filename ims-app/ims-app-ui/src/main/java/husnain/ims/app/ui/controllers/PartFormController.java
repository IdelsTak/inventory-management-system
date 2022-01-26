package husnain.ims.app.ui.controllers;

import husnain.ims.app.crud.utils.IdSequence;
import husnain.ims.app.model.InHouse;
import husnain.ims.app.model.OutSourced;
import husnain.ims.app.model.Part;
import husnain.ims.app.ui.controllers.utils.Named;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.css.PseudoClass;
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

    private static final Logger LOG = Logger.getLogger(PartFormController.class.getName());
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
    private Label stockErrorLabel;
    @FXML
    private Label companyOrMachineIdLabel;
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
        var id = IdSequence.getInstance().next();
        var name = nameTextField.getText();
        var price = Double.parseDouble(priceTextField.getText());
        var stock = Integer.parseInt(stockTextField.getText());
        var minStock = Integer.parseInt(minStockTextField.getText());
        var maxStock = Integer.parseInt(maxStockTextField.getText());

        Part val;

        if (inhouseRadioButton.isSelected()) {
            var inhouse = new InHouse(id, name, price, stock, minStock, maxStock);
            var machineId = Integer.parseInt(nameOrMachineIdTextField.getText());

            inhouse.setMachineId(machineId);

            val = inhouse;
        } else {
            var outSourced = new OutSourced(id, name, price, stock, minStock, maxStock);

            outSourced.setCompanyName(nameOrMachineIdTextField.getText());

            val = outSourced;
        }

        return val;
    }

    @FXML
    void initialize() {
        this.initTitle();

        companyOrMachineIdLabel.textProperty().bind(this.createFieldForProductType());

        var errorPseudoClass = PseudoClass.getPseudoClass("error");

        stockTextField.textProperty().addListener(event -> {
            stockTextField.pseudoClassStateChanged(
                    errorPseudoClass,
                    !stockTextField.getText().isEmpty() && !stockTextField.getText().matches("\\d+")
            );
        });

        stockTextField.getPseudoClassStates().addListener((SetChangeListener.Change<? extends PseudoClass> change) -> {
            stockErrorLabel.setText(change.getSet().contains(errorPseudoClass) ? "Invalid stock value" : "");
        });

        if (Objects.isNull(part)) {
            idTextField.setText("Auto Gen - Disabled");
        } else {

        }
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
