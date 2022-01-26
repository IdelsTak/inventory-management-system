package husnain.ims.app.ui.controllers.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.binding.NumberExpressionBase;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Husnain Arif
 */
public class BoundablePropertyRatio implements ChangeListener<Number> {

    private final NumberExpressionBase base;
    private final List<PropertyRatio> propRatios;

    public BoundablePropertyRatio(NumberExpressionBase base, Collection<PropertyRatio> propRatios) {
        this.base = base;
        this.propRatios = new ArrayList<>(propRatios);
    }

    @Override
    public void changed(ObservableValue<? extends Number> obs, Number ov, Number nv) {
        propRatios.forEach(propRatio -> this.bind(propRatio.property(), propRatio.ratio()));
    }

    private void bind(Property colProp, double ratio) {
        if (colProp.isBound()) {
            colProp.unbind();
        }
        colProp.bind(base.multiply(ratio));
    }

}
