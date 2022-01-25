package husnain.ims.app.crud;

import husnain.ims.app.model.Part;
import husnain.ims.app.model.Product;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Husnain Arif
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        return allParts.stream().filter(part -> Objects.equals(part.getId(), partId)).findFirst().orElseThrow();
    }

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
