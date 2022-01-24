package husnain.ims.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Husnain Arif
 */
public class App extends Application {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) {
        Scene scene = null;

        try {
            scene = new Scene(this.createParent());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createParent() throws IOException {
        return FXMLLoader.load(this.getClass().getResource("MainForm.fxml"));
    }

}
