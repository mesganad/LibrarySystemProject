package ui;

import application.Main;
import business.AddMemberException;
import business.CheckoutException;
import business.ControllerInterface;
import business.MainTest;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CheckOutPage extends Stage implements LibWindow {
	public static final CheckOutPage INSTANCE = new CheckOutPage();
	protected static final String Dialogs = null;
	final double rem = Font.getDefault().getSize();
	final String WINDOW_TITLE = "Librarian Check Out Page";
	final String ID = "User ID";
	final String ISBN = "ISBN";
	final String CHECKOUTDATE = "CheckoutDate";

	final String SUBMIT = "Submit";
	final String PRINT = "Print";
	final String BACK = "Back";
	final Font labelFont = new Font(12);

	private TextField id;
	private TextField isbn;
	private TextField checkoutDate;

	private HBox topPanel;
	private HBox bottomPanel;
	private Button submitButton;
	private Button printButton;
	private Button goBack;

	@Override
	public void init() {
		this.setTitle(WINDOW_TITLE);
		defineTopPanel();

		defineBottomPanel();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(topPanel, 0, 0);

		bottomPanel.setPadding(new Insets(35, 0, 0, 0));
		grid.add(bottomPanel, 0, 2);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					ControllerInterface ci = new SystemController();
					String s = ci.checkoutBook(id.getText(), isbn.getText(), checkoutDate.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(s);
					alert.showAndWait();
					clearFields();
				} catch (CheckoutException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
				}

			}
		});
		goBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				switch (SystemController.currentAuth) {
				case LIBRARIAN:
					Start.hideAllWindows();
					if (!LibrarianHome.INSTANCE.isInitialized()) {
						LibrarianHome.INSTANCE.init();
					}
					LibrarianHome.INSTANCE.show();
					break;
				case ADMIN:
					Start.hideAllWindows();
					if (!AdminHome.INSTANCE.isInitialized()) {
						AdminHome.INSTANCE.init();
					}
					AdminHome.INSTANCE.show();

					break;
				case BOTH:
					Start.hideAllWindows();
					if (!BothHome.INSTANCE.isInitialized()) {
						BothHome.INSTANCE.init();
					}
					BothHome.INSTANCE.show();
					break;
				}
			}
		});

		printButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
					//Main m = new Main();
					
					 try {
						MainTest.print(id.getText());
					} catch (AddMemberException e1) {
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText(e1.getMessage());
						alert.showAndWait();
					}
					
					clearFields();
				

			}
		});

		Scene scene = new Scene(grid, 600, 350);

		setScene(scene);

		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

	}

	private void defineTopPanel() {
		VBox id = getItem(ID);
		VBox isbn = getItem(ISBN);
		VBox checkoutDate = getItem(CHECKOUTDATE);

		topPanel = new HBox(rem * 5);
		topPanel.getChildren().addAll(id, isbn, checkoutDate);
	}

	private void defineBottomPanel() {
		submitButton = new Button(SUBMIT);
		printButton = new Button(PRINT);
		goBack = new Button(BACK);
		bottomPanel = new HBox(rem * 10);
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.getChildren().addAll(goBack, submitButton, printButton);

	}

	public void clearFields() {

		id.setText("");
		isbn.setText("");
		// checkoutDate.setText("");
	}

	private VBox getItem(String labelName) {
		Label label = new Label(labelName);
		label.setFont(labelFont);

		VBox item = new VBox(0.2 * rem);

		TextField textField = null;
		switch (labelName) {
		case ID:
			id = new TextField();
			textField = id;
			break;
		case ISBN:
			isbn = new TextField();
			textField = isbn;
			break;
		case CHECKOUTDATE:
			checkoutDate = new TextField();
			textField = checkoutDate;
			break;

		default:
			break;
		}

		item.getChildren().addAll(label, textField);
		return item;
	}

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

}
