import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Database database = Database.getInstance();
	String foodID = "";
	
		Stage window;
		//loginScene
		Scene loginScene;
		VBox loginVB;
		GridPane loginGP;
		HBox loginHB;
		Label loginLbl, emailLbl, passLbl;
		TextField emailTF;
		PasswordField pf;
		Button loginBtn, signupBtn;
		
		//mainScene
		Scene mainScene;
		VBox contentVB;
		GridPane contentGP;
		HBox contentHB;
		
		TableView<Food> foodTV;
		Vector<Food> foodV;
		Label foodIDLbl, foodNameLbl, foodDescLbl, priceLbl, qtyLbl;
		TextField foodIDTF, foodnameTF, foodDescTF, priceTF, qtyTF;
		Button insert, update, delete;
		
		
		

		
		public void setfoodTable () {
			TableColumn<Food, Integer> idCol = new TableColumn<Food, Integer>("ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("foodID"));
			
			TableColumn<Food, String> nameCol = new TableColumn<Food, String>("Name");
			nameCol.setCellValueFactory(new PropertyValueFactory<>("foodname"));
			
			TableColumn<Food, String> descCol = new TableColumn<Food, String>("Desc");
			descCol.setCellValueFactory(new PropertyValueFactory<>("foodDescription"));
			
			TableColumn<Food, Integer> priceCol = new TableColumn<Food, Integer>("Price");
			priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			TableColumn<Food, Integer> qtyCol = new TableColumn<Food, Integer>("Qty");
			qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			
			
			
			foodTV.getColumns().addAll(idCol, nameCol, descCol, priceCol, qtyCol);
			foodTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			foodTV.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					TableSelectionModel<Food> tableSelectionModel = foodTV.getSelectionModel();
					tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
					Food food = tableSelectionModel.getSelectedItem();
//					String foodID = food.getFoodID();
					String foodName = food.getFoodName();
					String foodDescription = food.getFoodDescription();
					int price = food.getPrice();
					int quantity = food.getQuantity();
					
					
//					foodIDTF.setText(foodID);
					foodnameTF.setText(foodName);
					foodDescTF.setText(foodDescription);
					priceTF.setText(Integer.toString(price));
					qtyTF.setText(Integer.toString(quantity));
					
				}
			});
		}
		
		public void inputFoodTable () {
			String query = "SELECT * FROM food";
			database.rs = database.execQuery(query);
			try {
				 while (database.rs.next()) {
					String foodID = database.rs.getString("foodID");
					String foodName = database.rs.getString("foodName");
					String foodDescription = database.rs.getString("foodDescription");
					
					 int price = database.rs.getInt("price");
					 int quantity = database.rs.getInt("quantity");
					 
					 foodTV.getItems().add(new Food(foodID, foodName, foodDescription, price, quantity));
					 foodV.add(new Food(foodID, foodName, foodDescription, price, quantity));
				 }
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		public void insertFood (String foodName, String foodDescription, int price, int quantity) {
			String query = String.format("INSERT INTO food(foodName, foodDescription, price, quantity)"
					+ "VALUES ('%s', '%s', %s, %s)", foodName, foodDescription, price, quantity);
			database.execUpdate(query);
		}
		
		
		public void updateFood (String foodName, String foodDescription, int price, int quantity) {
			String query = String.format("UPDATE food\n" 
					+ "SET foodName = '%s', foodDescription = '%s', price = %s, quantity = %s\n"
					+ "WHERE id = %s", foodName, foodDescription, price, quantity);
			database.execUpdate(query);
		}

		
		public void deleteUser (String foodID) {
			String query = String.format("DELETE FROM food\n"
					+ "WHERE id = %s", foodID);
			database.execUpdate(query);
		}
		
		
	
		
		public void initialize () {
			//loginScene
			loginVB = new VBox();
			loginGP = new GridPane();
			loginHB = new HBox();
			loginScene = new Scene(loginVB, 320, 220);
			loginLbl = new Label("Login");
			emailLbl = new Label("Email");
			passLbl = new Label("Password");
			emailTF = new TextField();
			pf = new PasswordField();
			loginBtn = new Button("Login");
			signupBtn = new Button("Sign Up");
			
			//mainScene
			contentVB = new VBox();
			contentGP = new GridPane();
			contentHB = new HBox();
			mainScene = new Scene(contentVB, 520, 420);
			
			foodTV = new TableView<>();
			foodV = new Vector<>();
			foodTV.setMaxHeight(200);
			foodNameLbl = new Label(" Food Name");
			foodDescLbl = new Label("Description");
			priceLbl = new Label("Price");
			qtyLbl = new Label("Qty");
			foodnameTF = new TextField();
			foodDescTF = new TextField();
			priceTF = new TextField();
			qtyTF = new TextField();
			insert = new Button("Insert");
			update = new Button("Update");
			delete = new Button("Delete");
		
		}
		
		public void setPos () {
			//loginScene
			loginGP.add(emailLbl, 0, 2);
			loginGP.add(loginLbl, 1, 0);
			loginGP.add(passLbl, 0, 3);
			loginGP.add(emailTF, 1, 2);
			loginGP.add(pf, 1, 3);
			loginLbl.setFont(Font.font("", FontWeight.BOLD, 24));
			loginHB.getChildren().addAll(loginBtn, signupBtn);
			loginVB.getChildren().addAll(loginGP, loginHB);
			loginGP.setAlignment(Pos.CENTER);
			loginHB.setAlignment(Pos.CENTER);
			loginVB.setSpacing(35);
			loginGP.setVgap(15);
			loginGP.setHgap(10);
			loginHB.setSpacing(10);
			loginVB.setStyle("-fx-background-color: #c8efd7");
			
			//mainScene
			contentGP.add(foodNameLbl, 0, 0);
			contentGP.add(foodDescLbl, 0, 1);
			contentGP.add(priceLbl, 0, 2);
			contentGP.add(qtyLbl, 0, 3);
			contentGP.add(foodnameTF, 1, 0);
			contentGP.add(foodDescTF, 1, 1);
			contentGP.add(priceTF, 1, 2);
			contentGP.add(qtyTF, 1, 3);
			contentHB.getChildren().addAll(insert, update, delete);
			contentVB.getChildren().addAll(foodTV, contentGP, contentHB);
			contentGP.setAlignment(Pos.CENTER);
			contentHB.setAlignment(Pos.CENTER);
			contentVB.setSpacing(35);
			contentGP.setVgap(10);
			contentGP.setHgap(10);
			contentHB.setSpacing(15);
			
		}
	
	
		
		@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		window = arg0;
		initialize();
		setPos();
		
			loginBtn.setOnAction(e -> {
			
			String emailInput = emailTF.getText();
			String pwInput = pf.getText();
			
			String queryEmail = "SELECT * FROM user WHERE email LIKE '" + emailInput + "'";
			database.rs = database.execQuery(queryEmail);
			
			if(emailInput.isEmpty() || pwInput.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "Please fill the empty field");
				alert.showAndWait();
			}else {
				try {
					if(!database.rs.next()) {
						Alert alert = new Alert(AlertType.ERROR, "Email doesn't exist!");
						alert.showAndWait();
					}else {
						String pw = database.rs.getString("password");
						
						if(!pwInput.equals(pw)) {
							Alert alert = new Alert(AlertType.ERROR, "Password doesn't match!");
							alert.showAndWait();
						}else {
							Alert alert = new Alert(AlertType.INFORMATION, "Sign In Success!");
							alert.showAndWait();
							window.setScene(mainScene);
							
						}
					}
				}catch (Exception e1){
					
				}
			}
		});
			
			

			//mainScene
			insert.setOnAction(new EventHandler<ActionEvent>() {
				Alert a = new Alert(AlertType.NONE);
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (foodnameTF.getText().toString().equals("")) {
						a.setAlertType(AlertType.ERROR);
						a.setContentText("Name must be filled!");
						a.showAndWait();
					} else {
			
						
//						String foodID = database.rs.getString("foodID");
						String foodName = foodnameTF.getText().toString();
						String foodDescription = foodDescTF.getText().toString();
						int price = Integer.parseInt(priceTF.getText().toString());
						int quantity = Integer.parseInt(qtyTF.getText().toString());
						
						insertFood(foodName, foodDescription, price, quantity);
						foodTV.getItems().clear();
						inputFoodTable();
						
						
						
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Insert Success!");
						a.showAndWait();
					}
				}
			});
			
			update.setOnAction(new EventHandler<ActionEvent>() {
				Alert a = new Alert(AlertType.NONE);
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (foodnameTF.getText().toString().equals("")) {
						a.setAlertType(AlertType.ERROR);
						a.setContentText("Name must be filled!");
						a.showAndWait();
					} else {
			
						
//						String foodID = database.rs.getString("foodID");
						String foodName = foodnameTF.getText().toString();
						String foodDescription = foodDescTF.getText().toString();
						int price = Integer.parseInt(priceTF.getText().toString());
						int quantity = Integer.parseInt(qtyTF.getText().toString());
						
						updateFood(foodName, foodDescription, price, quantity);
						foodTV.getItems().clear();
						inputFoodTable();
						
						
						
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Update Success!");
						a.showAndWait();
					}
				}
			});
			
			delete.setOnAction(new EventHandler<ActionEvent>() {
				Alert a = new Alert(AlertType.NONE);
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (foodnameTF.getText().toString().equals("")) {
						a.setAlertType(AlertType.ERROR);
						a.setContentText("Name must be filled!");
						a.showAndWait();
					} else {
						
						
						
						deleteUser(foodID);
						foodTV.getItems().clear();
						inputFoodTable();
						
						
						
						a.setAlertType(AlertType.INFORMATION);
						a.setContentText("Delete Success!");
						a.showAndWait();
					}
				}
			});
		
		window.setTitle("Nasi Padang");
		window.setScene(loginScene);
		window.show();
	}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
