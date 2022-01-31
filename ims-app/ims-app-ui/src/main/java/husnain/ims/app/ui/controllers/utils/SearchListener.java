package husnain.ims.app.ui.controllers.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author Husnain Arif
 */
public class SearchListener<T> implements ChangeListener<String> {

    private final SearchableList<T> searchableList;
    private final TableView<T> table;

    public SearchListener(SearchableList<T> searchableList, TableView<T> table) {
        this.searchableList = searchableList;
        this.table = table;
    }

    @Override
    public void changed(ObservableValue<? extends String> ov, String oldText, String newText) {
        var filtered = searchableList.getFiltered(newText);

        if (filtered.isEmpty()) {
            var alert = new Alert(Alert.AlertType.WARNING, null);

            alert.setHeaderText("No result found for query \"%s\"".formatted(newText));
            alert.show();

            alert.setOnHidden(eh -> table.requestFocus());
        } else {
            table.setItems(filtered);
        }
    }

}
