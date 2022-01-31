package husnain.ims.app.ui.controllers.utils;

import java.util.function.Function;
import javafx.collections.ObservableList;

/**
 *
 * @author Husnain Arif
 */
public class SearchableList<T> {

    private final ObservableList<T> unfiltered;
    private final String query;
    private final Function<ObservableList<T>, ObservableList<T>> identity;
    private final Function<String, ObservableList<T>> search;

    public SearchableList(ObservableList<T> unfiltered, String query, Function<ObservableList<T>, ObservableList<T>> identity, Function<String, ObservableList<T>> search) {
        this.unfiltered = unfiltered;
        this.query = query;
        this.identity = identity;
        this.search = search;
    }

    public ObservableList<T> getFiltered() {
        return new StringCheck(query).isNullOrBlank() ? identity.apply(unfiltered) : search.apply(query);
    }
}
