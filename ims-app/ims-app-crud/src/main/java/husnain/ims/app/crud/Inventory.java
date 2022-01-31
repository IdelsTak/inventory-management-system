package husnain.ims.app.crud;

import husnain.ims.app.model.Part;
import husnain.ims.app.model.Product;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class that offers create, read, update, and delete (CRUD) routines on
 * {@link Part} and {@link Product} caches.
 * <p>
 * The class doesn't use any DBMS implementations. It uses instances of
 * {@link ObservableList} instead.
 *
 * @author Husnain Arif
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Attaches a {@link Part} object to an {@link ObservableList} of
     * {@code Part} objects.
     * <p>
     * Assumes, without verification, that the supplied {@code Part} is non
     * null.
     *
     * @param part a {@code Part} instance to append to the cache of
     *             {@code Part} objects.
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Attaches a {@link Product} object to an {@link ObservableList} of
     * {@code Product} objects.
     * <p>
     * Assumes, without verification, that the supplied {@code Product} is non
     * null.
     *
     * @param newProduct a {@code Product} instance to append to the cache of
     *                   {@code Product} objects.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches the cache of {@code Part} objects for a {@code Part} instance
     * with the specified {@code id}.
     * <p>
     * If it finds it, it returns it -- otherwise, it throws a
     * {@link NoSuchElementException}.
     *
     * @param partId the {@code int} value representing the {@link Part#id} of a
     *               {@code Part} that may be held in the cache of {@code Part}
     *               objects.
     *
     * @return a {@code Part} instance, held in cache, with an {@link Part#id}
     *         matching the one supplied.
     *
     * @throws NoSuchElementException if no {@code Part} with the supplied
     *                                {@code id} was found.
     */
    public static Part lookupPart(int partId) {
        return allParts.stream().filter(part -> Objects.equals(part.getId(), partId)).findFirst().orElseThrow();
    }

    /**
     * Searches the cache of {@code Product} objects for a {@code Part} instance
     * with the specified {@code id}.
     * <p>
     * If it finds it, it returns it -- otherwise, it throws a
     * {@link NoSuchElementException}.
     *
     * @param productId the {@code int} value representing the
     *                  {@link Product#id} of a {@code Product} that may be held
     *                  in the cache of {@code Product} objects.
     *
     * @return a {@code Product} instance, held in cache, with an
     *         {@link Product#id} matching the one supplied.
     *
     * @throws NoSuchElementException if no {@code Product} with the supplied
     *                                {@link Product#id} was found.
     */
    public static Product lookupProduct(int productId) {
        return allProducts.stream().filter(product -> Objects.equals(product.getId(), productId)).findFirst().orElseThrow();
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.stream()
                .filter(part -> part.getName().toLowerCase().contains(partName.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(productName.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
