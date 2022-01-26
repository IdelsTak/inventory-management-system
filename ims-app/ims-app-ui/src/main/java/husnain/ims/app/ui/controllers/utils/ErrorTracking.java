package husnain.ims.app.ui.controllers.utils;

import java.util.Objects;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.css.PseudoClass;

/**
 *
 * @author Husnain Arif
 */
public class ErrorTracking implements SetChangeListener<PseudoClass> {

    private final String title;
    private final String errorDetail;
    private final ObservableSet<InputError> errors;

    public ErrorTracking(String errorTitle, String errorDetail, ObservableSet<InputError> errors) {
        this.title = errorTitle;
        this.errorDetail = errorDetail;
        this.errors = errors;
    }

    @Override
    public void onChanged(SetChangeListener.Change<? extends PseudoClass> change) {
        boolean erroneous = change.getSet().stream().anyMatch(pseudo -> Objects.equals("error", pseudo.getPseudoClassName()));
        var inputError = new InputError(title, errorDetail);

        if (erroneous) {
            errors.add(inputError);
        } else if (errors.contains(inputError)) {
            errors.remove(inputError);
        }
    }

}
