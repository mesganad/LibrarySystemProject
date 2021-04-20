package ui;

import java.time.LocalDate;

import business.SystemController;
import business.EntryDataModel;
import business.EntryDataModelException;
import business.OverdueDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OverdueStatus extends Stage implements LibWindow {
	public static final OverdueStatus INSTANCE = new OverdueStatus();
	protected static final String Dialogs = null;

	final double rem = Font.getDefault().getSize();
	final String WINDOW_TITLE = "Checkout Entry Information";
	final String ISBN = "ISBN";

	final String SEARCH = "Search";
	//final String BACK = "Back";
	final Font labelFont = new Font(12);

	private TextField isbn1;

	private HBox topPanel;
	private HBox bottomPanel;
	private Button searchButton;
	//private Button goBack;

	private TableView<OverdueDataModel> table = new TableView<>();

	@Override
	public void init() {

		this.setTitle(WINDOW_TITLE);
		defineTopPanel();

		defineBottomPanel();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 0, 0, 0));

		grid.add(topPanel, 0, 1);

		bottomPanel.setPadding(new Insets(0, 0, 0, 0));
		grid.add(bottomPanel, 0, 2);
		table.setEditable(true);

		TableColumn isbn = new TableColumn("Book ISBN");
		isbn.setMinWidth(100);
		TableColumn memberId = new TableColumn("Library Member");
		memberId.setMinWidth(100);
		TableColumn title = new TableColumn("Book Title");
		title.setMinWidth(100);
		TableColumn copyNumber = new TableColumn("Copy Number");
		copyNumber.setMinWidth(100);
		TableColumn overdueDate = new TableColumn("Overdue Status");
		overdueDate.setMinWidth(100);

		table.getColumns().addAll(isbn, memberId, title, copyNumber, overdueDate);

		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					OverdueDataModel odl = new OverdueDataModel("a", "b", "c", 1, "e");
					ObservableList<OverdueDataModel> data = FXCollections
							.observableArrayList(odl.getOverdueList(isbn1.getText()));

					isbn.setCellValueFactory(new PropertyValueFactory<OverdueDataModel, String>("isbn"));
					memberId.setCellValueFactory(new PropertyValueFactory<OverdueDataModel, String>("id"));
					title.setCellValueFactory(new PropertyValueFactory<OverdueDataModel, String>("title"));

					copyNumber.setCellValueFactory(new PropertyValueFactory<OverdueDataModel, Integer>("copyNum"));

					overdueDate.setCellValueFactory(
							new PropertyValueFactory<OverdueDataModel, LocalDate>("overdueStatus"));

					table.setItems(data);

				} catch (EntryDataModelException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(e1.getMessage());
					alert.showAndWait();
					table.getItems().clear();
					// e1.printStackTrace();
				}

			}
		});

		/*
		 * goBack.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) { switch
		 * (SystemController.currentAuth) { case LIBRARIAN: Start.hideAllWindows(); if
		 * (!LibrarianHome.INSTANCE.isInitialized()) { LibrarianHome.INSTANCE.init(); }
		 * LibrarianHome.INSTANCE.show(); break; case ADMIN: Start.hideAllWindows(); if
		 * (!AdminHome.INSTANCE.isInitialized()) { AdminHome.INSTANCE.init(); }
		 * AdminHome.INSTANCE.show();
		 * 
		 * break; case BOTH: Start.hideAllWindows(); if
		 * (!BothHome.INSTANCE.isInitialized()) { BothHome.INSTANCE.init(); }
		 * BothHome.INSTANCE.show(); break; } } });
		 */

		Scene scene = new Scene(grid, 700, 500);

		setScene(scene);

		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

	}

	private void defineTopPanel() {

		VBox isbn = getItem(ISBN);
		searchButton = new Button(SEARCH);

		topPanel = new HBox(rem * 2);
		topPanel.setAlignment(Pos.TOP_LEFT);
		topPanel.getChildren().addAll(isbn, searchButton);
	}

	private void defineBottomPanel() {

		//goBack = new Button(BACK);
		bottomPanel = new HBox(rem * 5);
		bottomPanel.setAlignment(Pos.BOTTOM_LEFT);
		bottomPanel.getChildren().addAll(table);

	}

	public void clearFields() {

		isbn1.setText("");

	}

	private VBox getItem(String labelName) {
		Label label = new Label(labelName);
		label.setFont(labelFont);

		VBox item = new VBox(0.2 * rem);

		TextField textField = null;
		switch (labelName) {

		case ISBN:
			isbn1 = new TextField();
			textField = isbn1;
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
