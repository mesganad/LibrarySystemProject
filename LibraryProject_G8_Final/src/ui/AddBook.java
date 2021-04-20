package ui;

import business.AddBookException;
import business.ControllerInterface;
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

public class AddBook extends Stage implements LibWindow {
	public static final AddBook INSTANCE = new AddBook();
	final double rem = Font.getDefault().getSize();
	final String WINDOW_TITLE = "Book Registration Form";
	final String TITLE = "Title";
	final String ISBN = "ISBN";
	final String MAX_CHECKOUT_LENGTH = "Max Checkout Length";

	// final String NUMBER_OF_COPIES = "Number Of Copies";

	final String AUTHORS = "Add Authors";
	final String SUBMIT = "Submit";
	final String BACK = "Back";
	final Font labelFont = new Font(12);

	private TextField title;
	private TextField isbn;
	private TextField maxCheckoutLen;
	// private TextField numOfCopies;

	private HBox topPanel;
	private HBox middlePanel;
	private HBox bottomPanel;
	private Button authorsBtn;
	private Button submitButton;
	private Button goBack;

	@Override
	public void init() {
		this.setTitle(WINDOW_TITLE);
		defineTopPanel();
		defineMiddlePanel();
		defineBottomPanel();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(topPanel, 0, 0);
		grid.add(middlePanel, 0, 1);
		bottomPanel.setPadding(new Insets(35, 0, 0, 0));
		grid.add(bottomPanel, 0, 2);

		authorsBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// hideAllWindows();
				if (!AddAuthor.INSTANCE.isInitialized()) {
					AddAuthor.INSTANCE.init();
				}
				AddAuthor.INSTANCE.show();
			}
		});

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ControllerInterface ci = new SystemController();
				try {
					ci.addBooks(title.getText(), isbn.getText(), maxCheckoutLen.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("New book succesfully added.");
					alert.showAndWait();
					clearFields();
				} catch (AddBookException e1) {
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

		Scene scene = new Scene(grid, 500, 250);
		setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

	}

	private void defineTopPanel() {
		VBox title = getItem(TITLE);
		VBox isbn = getItem(ISBN);
		
		topPanel = new HBox(rem * 5);
		topPanel.getChildren().addAll(title, isbn);
	}

	private void defineMiddlePanel() {

		VBox maxCheckoutLen = getItem(MAX_CHECKOUT_LENGTH);
		authorsBtn = new Button(AUTHORS);

		middlePanel = new HBox(rem * 5);
		middlePanel.setAlignment(Pos.CENTER);
		middlePanel.getChildren().addAll(maxCheckoutLen, authorsBtn);

	}

	private void defineBottomPanel() {
		submitButton = new Button(SUBMIT);
		goBack = new Button(BACK);
		bottomPanel = new HBox(rem * 25);
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.getChildren().addAll(goBack, submitButton);

	}

	public void clearFields() {

		title.setText("");
		isbn.setText("");
		maxCheckoutLen.setText("");
		

	}

	private VBox getItem(String labelName) {
		Label label = new Label(labelName);
		label.setFont(labelFont);

		VBox item = new VBox(0.2 * rem);

		TextField textField = null;
		switch (labelName) {
		case TITLE:
			title = new TextField();
			textField = title;
			break;

		case ISBN:
			isbn = new TextField();
			textField = isbn;
			break;

		case MAX_CHECKOUT_LENGTH:
			maxCheckoutLen = new TextField();
			textField = maxCheckoutLen;
			break;

		/*
		 * case NUMBER_OF_COPIES: numOfCopies = new TextField(); textField =
		 * numOfCopies; break;
		 */
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
