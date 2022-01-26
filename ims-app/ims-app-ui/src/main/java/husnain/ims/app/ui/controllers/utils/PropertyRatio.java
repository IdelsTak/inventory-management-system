package husnain.ims.app.ui.controllers.utils;

import javafx.beans.property.DoubleProperty;

/**
 *
 * @author Husnain Arif
 */
public record PropertyRatio(DoubleProperty property, Double ratio) {

    public PropertyRatio(DoubleProperty property, Double ratio) {
        this.property = property;
        this.ratio = ratio;
    }

}
