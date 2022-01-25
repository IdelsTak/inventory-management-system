package husnain.ims.app.ui.controllers.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

/**
 *
 * @author Husnain Arif
 */
public class ColumnWidthTweak implements ChangeListener<Number> {
    
    private final DoubleExpression prop;
    private final List<Pair<Property, Double>> colVals;

    public ColumnWidthTweak(DoubleExpression prop, Collection<Pair<Property, Double>> colVals) {
        this.prop = prop;
        this.colVals = new ArrayList<>(colVals);
    }

    @Override
    public void changed(ObservableValue<? extends Number> obs, Number ov, Number nv) {
        colVals.forEach(colVal -> this.bindColumnWidth(colVal.getKey(), colVal.getValue()));
    }

    private void bindColumnWidth(Property colProp, double ratio) {
        if (colProp.isBound()) {
            colProp.unbind();
        }
        colProp.bind(prop.multiply(ratio));
    }
    
}
