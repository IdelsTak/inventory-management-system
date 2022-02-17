package husnain.ims.app.ui.controllers.utils;

import java.util.Objects;

/**
 * FUTURE ENHANCEMENT: none.
 *
 * @author Hiram K.
 */
public class StringCheck {

    private final String text;

    public StringCheck(String text) {
        this.text = text;
    }

    public boolean isNullOrBlank() {
        return Objects.isNull(text) || text.isBlank();
    }
}
