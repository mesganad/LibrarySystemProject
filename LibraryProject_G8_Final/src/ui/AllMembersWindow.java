package ui;

import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AllMembersWindow extends Stage implements LibWindow {
	public static final AllMembersWindow INSTANCE = new AllMembersWindow();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private TextArea ta;

	public void setData(String data) {
		ta.setText(data);
	}

	private AllMembersWindow() {
	}

	public void init() {
		this.setTitle("All Members");
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("All Members");
		scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		ta = new TextArea();
		grid.add(ta, 0, 1);

		Button backBtn = new Button("Back");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
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
		HBox hBack = new HBox(10);
		hBack.setAlignment(Pos.BOTTOM_LEFT);
		hBack.getChildren().add(backBtn);
		grid.add(hBack, 0, 2);
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);
	}
}
