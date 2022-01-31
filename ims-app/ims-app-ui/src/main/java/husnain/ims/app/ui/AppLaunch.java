package husnain.ims.app.ui;

import husnain.ims.app.sample.data.Generate;

/**
 * Starts the application.
 *
 * @author Husnain Arif
 */
public class AppLaunch {

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        new Generate().sampleData();
        
        InventoryManagementApp.main(args);
    }
}
