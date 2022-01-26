package husnain.ims.app.ui.controllers.utils;

import husnain.ims.app.model.Part;
import java.util.Objects;
import javafx.scene.control.TableCell;

/**
 *
 * @author Husnain Arif
 */
public class FormattedPriceCell extends TableCell<Part, Double> {
    
    @Override
    protected void updateItem(Double price, boolean empty) {
        super.updateItem(price, empty);
        super.setText(Objects.isNull(price) || empty ? null : String.format("%.2f", price));
    }
    
}
