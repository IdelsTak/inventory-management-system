package husnain.ims.app.ui.controllers.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.css.PseudoClass;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Husnain Arif
 */
public class ErrorPseudoClassState implements InvalidationListener {

    private final TextInputControl textInput;
    private final String regex;

    public ErrorPseudoClassState(TextInputControl textInput, String regex) {
        this.textInput = textInput;
        this.regex = regex;
    }

    @Override
    public void invalidated(Observable e) {
        var text = textInput.getText();

        textInput.pseudoClassStateChanged(
                PseudoClass.getPseudoClass("error"),
                !text.isEmpty() && !text.matches(regex)
        );
    }

}
