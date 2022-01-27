package husnain.ims.app.ui.controllers;

import husnain.ims.app.crud.utils.IdSequence;
import husnain.ims.app.model.InHouse;
import husnain.ims.app.model.OutSourced;
import husnain.ims.app.model.Part;
import husnain.ims.app.ui.controllers.utils.InputError;
import husnain.ims.app.ui.controllers.utils.Named;
import husnain.ims.app.ui.controllers.utils.RegexCheck;
import husnain.ims.app.ui.controllers.utils.StringCheck;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
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
    private Label companyOrMachineIdLabel;
    @FXML
    private Label errorLabel;
    private final Part part;
    private final ObservableSet<InputError> inputErrors;

    public PartFormController() {
        this(Named.DialogType.ADD, null);
    }

    public PartFormController(Part part) {
        this(Named.DialogType.MODIFY, Objects.requireNonNull(part, "Part to modify should not be null"));
    }

    private PartFormController(Named.DialogType type, Part part) {
        this.type = type;
        this.part = part;
        this.inputErrors = FXCollections.observableSet(new LinkedHashSet<>());
    }

    public Part getPart() {
        Part modifiedPart;
        var name = nameTextField.getText();
        var price = Double.parseDouble(priceTextField.getText());
        var stock = Integer.parseInt(stockTextField.getText());
        var minStock = Integer.parseInt(minStockTextField.getText());
        var maxStock = Integer.parseInt(maxStockTextField.getText());

        if (Objects.isNull(part)) {
            var id = IdSequence.getInstance().next();

            if (inhouseRadioButton.isSelected()) {
                var inhouse = new InHouse(id, name, price, stock, minStock, maxStock);
                var machineId = Integer.parseInt(nameOrMachineIdTextField.getText());

                inhouse.setMachineId(machineId);

                modifiedPart = inhouse;
            } else {
                var outSourced = new OutSourced(id, name, price, stock, minStock, maxStock);

                outSourced.setCompanyName(nameOrMachineIdTextField.getText());

                modifiedPart = outSourced;
            }
        } else {
            if ((part instanceof InHouse) && outsourcedRadioButton.isSelected()) {
                var outSourced = new OutSourced(part.getId(), name, price, stock, minStock, maxStock);

                outSourced.setCompanyName(nameOrMachineIdTextField.getText());

                modifiedPart = outSourced;
            } else if ((part instanceof OutSourced) && inhouseRadioButton.isSelected()) {
                var inhouse = new InHouse(part.getId(), name, price, stock, minStock, maxStock);
                var machineId = Integer.parseInt(nameOrMachineIdTextField.getText());

                inhouse.setMachineId(machineId);

                modifiedPart = inhouse;
            } else {
                part.setName(name);
                part.setStock(stock);
                part.setPrice(price);
                part.setMax(maxStock);
                part.setMin(minStock);

                if (part instanceof InHouse inHouse) {
                    inHouse.setMachineId(Integer.parseInt(nameOrMachineIdTextField.getText()));
                } else {
                    ((OutSourced) part).setCompanyName(nameOrMachineIdTextField.getText());
                }

                modifiedPart = part;
            }
        }

        return modifiedPart;
    }
    private static final String NOT_BLANK = "should not be blank";
    private static final String BE_A_NUMBER = "should be a number";
    private final InputError blankNameError = new InputError("Invalid name", NOT_BLANK);
    private final InputError blankStockError = new InputError("Invalid inv", NOT_BLANK);
    private final InputError invalidStockError = new InputError("Invalid inv", BE_A_NUMBER);
    private final InputError blankPriceError = new InputError("Invalid price", NOT_BLANK);
    private final InputError invalidPriceError = new InputError("Invalid price", BE_A_NUMBER);
    private final InputError blankMaxStockError = new InputError("Invalid max", NOT_BLANK);
    private final InputError invalidMaxStockError = new InputError("Invalid max", BE_A_NUMBER);
    private final InputError blankMinStockError = new InputError("Invalid min", NOT_BLANK);
    private final InputError invalidMinStockError = new InputError("Invalid min", BE_A_NUMBER);
    private final InputError blankMachineIdOrCompanyNameError = new InputError("Invalid machine id or company name", NOT_BLANK);
    private final InputError invalidMachineIdError = new InputError("Invalid machine id", BE_A_NUMBER);

    public Set<InputError> getInputErrors() {
        this.updateErrors();

        return Collections.unmodifiableSet(inputErrors);
    }

    public void updateErrorText() {
        var ls = System.lineSeparator();
        var text = inputErrors.stream()
                .map(InputError::toString)
                .collect(Collectors.joining(ls, String.format("%d Error(s)%s", inputErrors.size(), ls), ls));

        LOG.log(Level.INFO, "Error text: {0}", text);

        errorLabel.setText(inputErrors.isEmpty() ? null : text);
    }

    private void updateErrors() {
        this.updateError(nameTextField, new StringCheck(nameTextField.getText()).isNullOrBlank(), blankNameError);
        this.updateError(stockTextField, new StringCheck(stockTextField.getText()).isNullOrBlank(), blankStockError);
        this.updateError(stockTextField, new RegexCheck(stockTextField.getText(), "\\d+").doesntMatch(), invalidStockError);
        this.updateError(priceTextField, new StringCheck(priceTextField.getText()).isNullOrBlank(), blankPriceError);
        this.updateError(priceTextField, new RegexCheck(priceTextField.getText(), "\\d+|\\d+\\.\\d+").doesntMatch(), invalidPriceError);
        this.updateError(maxStockTextField, new StringCheck(maxStockTextField.getText()).isNullOrBlank(), blankMaxStockError);
        this.updateError(maxStockTextField, new RegexCheck(maxStockTextField.getText(), "\\d+").doesntMatch(), invalidMaxStockError);
        this.updateError(minStockTextField, new StringCheck(minStockTextField.getText()).isNullOrBlank(), blankMinStockError);
        this.updateError(minStockTextField, new RegexCheck(minStockTextField.getText(), "\\d+").doesntMatch(), invalidMinStockError);

        if (inhouseRadioButton.isSelected()) {
            this.updateError(nameOrMachineIdTextField, new StringCheck(nameOrMachineIdTextField.getText()).isNullOrBlank(), blankMachineIdOrCompanyNameError);
            this.updateError(nameOrMachineIdTextField, new RegexCheck(nameOrMachineIdTextField.getText(), "\\d+").doesntMatch(), invalidMachineIdError);
        } else {
            this.updateError(nameOrMachineIdTextField, false, invalidMachineIdError);
            this.updateError(nameOrMachineIdTextField, new StringCheck(nameOrMachineIdTextField.getText()).isNullOrBlank(), blankMachineIdOrCompanyNameError);
        }
    }

    private void updateError(TextInputControl textInput, boolean invalid, InputError error) {
        var styleClass = textInput.getStyleClass();

        if (invalid) {
            inputErrors.add(error);
            if (!styleClass.contains("input-error")) {
                styleClass.add("input-error");
            }
        } else {
            inputErrors.remove(error);
            styleClass.setAll(styleClass.stream().filter(sc -> !Objects.equals(sc, "input-error")).toArray(String[]::new));
        }
    }

    @FXML
    void initialize() {
        this.initTitle();

        companyOrMachineIdLabel.textProperty().bind(this.createFieldForProductType());

        if (Objects.isNull(part)) {
            inhouseRadioButton.setSelected(true);
            idTextField.setText("Auto Gen - Disabled");
        } else {
            var inhousePart = part instanceof InHouse;

            productTypeToggleGrp.selectToggle(inhousePart ? inhouseRadioButton : outsourcedRadioButton);

            idTextField.setText(Integer.toString(part.getId()));
            nameTextField.setText(part.getName());
            stockTextField.setText(Integer.toString(part.getStock()));
            priceTextField.setText(Double.toString(part.getPrice()));
            maxStockTextField.setText(Integer.toString(part.getMax()));
            minStockTextField.setText(Integer.toString(part.getMin()));
            nameOrMachineIdTextField.setText(
                    inhousePart
                            ? Integer.toString(((InHouse) part).getMachineId())
                            : ((OutSourced) part).getCompanyName()
            );
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
