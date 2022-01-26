package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.controllers.utils.ErrorTracking;
import husnain.ims.app.ui.controllers.utils.ErrorPseudoClassState;
import husnain.ims.app.crud.utils.IdSequence;
import husnain.ims.app.model.InHouse;
import husnain.ims.app.model.OutSourced;
import husnain.ims.app.model.Part;
import husnain.ims.app.ui.controllers.utils.InputError;
import husnain.ims.app.ui.controllers.utils.Named;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
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
    private InvalidationListener listener;
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
        var name = this.getStringOrDefault(nameTextField.getText());
        var price = this.getDoubleOrDefault(priceTextField.getText());
        var stock = this.getIntOrDefault(stockTextField.getText());
        var minStock = this.getIntOrDefault(minStockTextField.getText());
        var maxStock = this.getIntOrDefault(maxStockTextField.getText());

        if (Objects.isNull(part)) {
            var id = IdSequence.getInstance().next();

            if (inhouseRadioButton.isSelected()) {
                var inhouse = new InHouse(id, name, price, stock, minStock, maxStock);
                var machineId = this.getIntOrDefault(nameOrMachineIdTextField.getText());

                inhouse.setMachineId(machineId);

                modifiedPart = inhouse;
            } else {
                var outSourced = new OutSourced(id, name, price, stock, minStock, maxStock);

                outSourced.setCompanyName(this.getStringOrDefault(nameOrMachineIdTextField.getText()));

                modifiedPart = outSourced;
            }
        } else {
            if ((part instanceof InHouse) && outsourcedRadioButton.isSelected()) {
                var outSourced = new OutSourced(part.getId(), name, price, stock, minStock, maxStock);

                outSourced.setCompanyName(this.getStringOrDefault(nameOrMachineIdTextField.getText()));

                modifiedPart = outSourced;
            } else if ((part instanceof OutSourced) && inhouseRadioButton.isSelected()) {
                var inhouse = new InHouse(part.getId(), name, price, stock, minStock, maxStock);
                var machineId = this.getIntOrDefault(nameOrMachineIdTextField.getText());

                inhouse.setMachineId(machineId);

                modifiedPart = inhouse;
            } else {
                part.setName(name);
                part.setStock(stock);
                part.setPrice(price);
                part.setMax(maxStock);
                part.setMin(minStock);

                if (part instanceof InHouse inHouse) {
                    inHouse.setMachineId(this.getIntOrDefault(nameOrMachineIdTextField.getText()));
                } else {
                    ((OutSourced) part).setCompanyName(this.getStringOrDefault(nameOrMachineIdTextField.getText()));
                }

                modifiedPart = part;
            }
        }

        return modifiedPart;
    }

    private String getStringOrDefault(String text) {
        return Objects.isNull(text) || text.isBlank() ? "n/a" : text;
    }

    private int getIntOrDefault(String text) {
        int parsed = 0;

        try {
            parsed = Integer.parseInt(text);
        } catch (NumberFormatException exc) {
            LOG.log(Level.FINE, null, exc);
        }

        return parsed;
    }

    private double getDoubleOrDefault(String text) {
        double parsed = 0;

        try {
            parsed = Double.parseDouble(text);
        } catch (NumberFormatException exc) {
            LOG.log(Level.FINE, null, exc);
        }

        return parsed;
    }

    public Set<InputError> getInputErrors() {
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

    @FXML
    void initialize() {
        this.initTitle();

        companyOrMachineIdLabel.textProperty().bind(this.createFieldForProductType());

        this.initErrorListening(stockTextField, "\\d+");
        this.initErrorListening(priceTextField, "\\d+|\\d+\\.\\d+");
        this.initErrorListening(maxStockTextField, "\\d+");
        this.initErrorListening(minStockTextField, "\\d+");

        productTypeToggleGrp.selectedToggleProperty().addListener((obs, ov, nv) -> {
            nameOrMachineIdTextField.setText("");

            if (Objects.nonNull(listener)) {
                nameOrMachineIdTextField.textProperty().removeListener(listener);
            }

            listener = Objects.equals(nv, inhouseRadioButton)
                    ? this.initErrorListening(nameOrMachineIdTextField, "\\d+")
                    : null;
        });

        stockTextField.getPseudoClassStates().addListener(new ErrorTracking("Invalid inv", "value should be a number", inputErrors));
        priceTextField.getPseudoClassStates().addListener(new ErrorTracking("Invalid price", "value should be a number", inputErrors));
        maxStockTextField.getPseudoClassStates().addListener(new ErrorTracking("Invalid max stock", "value should be a number", inputErrors));
        minStockTextField.getPseudoClassStates().addListener(new ErrorTracking("Invalid min stock", "value should be a number", inputErrors));
        nameOrMachineIdTextField.getPseudoClassStates().addListener(new ErrorTracking("Invalid machine id", "value should be a number", inputErrors));

        inputErrors.addListener((SetChangeListener.Change<? extends InputError> change) -> {
            if (change.wasRemoved()) {
                this.updateErrorText();
            }
        });

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

    private InvalidationListener initErrorListening(TextInputControl textInput, String regex) {
        var invListener = new ErrorPseudoClassState(textInput, regex);

        textInput.textProperty().addListener(invListener);

        return invListener;
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
