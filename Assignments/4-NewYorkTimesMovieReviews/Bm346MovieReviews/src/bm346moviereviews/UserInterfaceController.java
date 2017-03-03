/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bm346moviereviews;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bidyut
 */
public class UserInterfaceController implements Initializable {

    @FXML
    private TextField movieSearchField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<NYTMovieReview.Results> movieResults;

    @FXML
    private ImageView imageView;

    @FXML
    private Label titleLabelContent;

    @FXML
    private Label mpaaLabelContent;

    @FXML
    private Label bylineLabelContent;

    @FXML
    private Label headlineLabelContent;

    @FXML
    private Label openingDateLabelContent;

    @FXML
    private Label pubDateLabelContent;

    @FXML
    private Label dateUpdatedLabelContent;

    @FXML
    private TextArea summaryText;

    private HostServices hostServices;
    private String fullUrl;
    private final String noImageFoundUrl = "http://www.faygoluvers.net/v5/wp-content/themes/original/images/no_image_available_s_large.jpg";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Empty
    }

    void ready(HostServices hostServices) {
        this.hostServices = hostServices;
        this.searchButton.setDefaultButton(true);
        this.movieSearchField.requestFocus();
    }

    @FXML
    public void searchMovieReviews() throws Exception {
        String searchString = movieSearchField.getText();

        NYTMovieReviewsManager manager = new NYTMovieReviewsManager();
        try {
            manager.load(searchString);
        } catch (Exception ex) {
            throw ex;
        }

        NYTMovieReview movieReview = manager.getMovieReviews();

        ObservableList<NYTMovieReview.Results> movieReviewTitles = FXCollections.observableArrayList(movieReview.results);
        movieResults.setItems(movieReviewTitles);

        movieResults.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayReview(newValue);
        });

    }

    private void displayReview(NYTMovieReview.Results result) {
        if (result == null) return;

        Platform.runLater(() -> {
            Image image;
            if (result.multimedia != null && result.multimedia.src.contains("http")) {
                image = new Image(result.multimedia.src);
            } else {
                image = new Image(noImageFoundUrl);
            }
            imageView.setImage(image);
        });

        titleLabelContent.setText(getDisplayString(result.displayTitle));
        mpaaLabelContent.setText(getDisplayString(result.mpaaRating));
        bylineLabelContent.setText(getDisplayString(result.byline));
        headlineLabelContent.setText(getDisplayString(result.headline));
        openingDateLabelContent.setText(getDisplayString(result.openingDate));
        pubDateLabelContent.setText(getDisplayString(result.publicationDate));
        dateUpdatedLabelContent.setText(getDisplayString(result.dateUpdated));
        fullUrl = result.link.url;
        summaryText.setText(getDisplayString(result.summaryShort));
    }

    private String getDisplayString(String input) {
        if (input == null || input.equals(""))
            return "-";
        else
            return input;
    }

    @FXML
    public void launchBrowser() {
        hostServices.showDocument(fullUrl);
    }

    @FXML
    public void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("NYT Movie Reviews");
        alert.setContentText("This application was developed by Bidyut Mukherjee (bm346) for CS7330 at the University of Missouri, under the guidance of Dr. Dale Musser.");

        TextArea textArea = new TextArea("This assignment allows the user to search and view any movie review from New York Times database.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
