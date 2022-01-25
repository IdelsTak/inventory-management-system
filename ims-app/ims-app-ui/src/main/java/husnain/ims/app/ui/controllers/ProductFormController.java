package husnain.ims.app.ui.controllers;

import husnain.ims.app.ui.controllers.utils.ColumnWidthTweak;
import husnain.ims.app.ui.controllers.utils.Named.DialogType;
import husnain.ims.app.ui.controllers.utils.PlaceholderLabel;
import java.util.List;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Husnain Arif
 */
public class ProductFormController {

    private final DialogType type;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField prodIdTextField;
    @FXML
    private TableColumn<?, ?> assocInvLevelColumn;
    @FXML
    private TableColumn<?, ?> assocPartIdColumn;
    @FXML
    private TableColumn<?, ?> assocPartNameColumn;
    @FXML
    private TableView<?> assocPartsTable;
    @FXML
    private TableColumn<?, ?> assocPriceColumn;
    @FXML
    private TableColumn<?, ?> invLevelColumn;
    @FXML
    private TableColumn<?, ?> partIdColumn;
    @FXML
    private TableColumn<?, ?> partNameColumn;
    @FXML
    private TableView<?> partsTable;
    @FXML
    private TableColumn<?, ?> priceColumn;

    public ProductFormController() {
        this(DialogType.ADD);
    }

    public ProductFormController(DialogType type) {
        this.type = type;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    void initialize() {
        this.initTitle();
        this.initFocus();
        this.initTablePlaceholders();
        this.setupColumnWidths();
    }

    private void initTitle() {
        titleLabel.setText(String.format("%s Product", type.toString()));
    }

    private void initFocus() {
        prodIdTextField.skinProperty().addListener((o) -> prodIdTextField.requestFocus());
    }

    private void initTablePlaceholders() {
        partsTable.setPlaceholder(new PlaceholderLabel("<No parts available>"));
        assocPartsTable.setPlaceholder(new PlaceholderLabel("<No associated parts available>"));
    }

    private void setupColumnWidths() {
        partsTable.widthProperty().addListener(new ColumnWidthTweak(
                        partsTable.widthProperty(),
                        List.of(
                                new Pair(partIdColumn.maxWidthProperty(), 0.12),
                                new Pair(partNameColumn.maxWidthProperty(), 0.38),
                                new Pair(invLevelColumn.maxWidthProperty(), 0.22),
                                new Pair(priceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );

        assocPartsTable.widthProperty().addListener(new ColumnWidthTweak(
                        assocPartsTable.widthProperty(),
                        List.of(
                                new Pair(assocPartIdColumn.maxWidthProperty(), 0.12),
                                new Pair(assocPartNameColumn.maxWidthProperty(), 0.38),
                                new Pair(assocInvLevelColumn.maxWidthProperty(), 0.22),
                                new Pair(assocPriceColumn.maxWidthProperty(), 0.28)
                        )
                )
        );
    }

}
