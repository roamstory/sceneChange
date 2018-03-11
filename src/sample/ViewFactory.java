package sample;

import javax.naming.OperationNotSupportedException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewFactory {
	
	public static ViewFactory defaultFactory = new ViewFactory();
	private static boolean mainViewInitialized = false;

	private final String CUSTOMER_SERACH = "CustomerSearch.fxml";

	
	private CustomerSearchController customerSearchController;
	

	public Scene getMainScene() throws OperationNotSupportedException{
		customerSearchController = new CustomerSearchController();
		mainViewInitialized = true;
		return initializeScene(CUSTOMER_SERACH, customerSearchController);
		
	}
	
	private Scene initializeScene(String fxmlPath, CustomerSearchController controller){
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(controller);
			parent = loader.load();
		} catch (Exception e) {
			return null;
		}
		
		scene = new Scene(parent);
		return scene;
	}
	
	
	
	
	
	
	
	
	

}
