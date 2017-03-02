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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bidyut
 */
public class UserInterfaceController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label movieLabel;

    @FXML
    private TextField movieSearchField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<NYTMovieReview.Results> movieResults;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label mpaaLabel;

    @FXML
    private Label bylineLabel;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label openingDateLabel;

    @FXML
    private Label publicationDateLabel;

    @FXML
    private Label dateUpdatedLabel;

    @FXML
    private Button fullReviewButton;

    @FXML
    private TextArea summaryText;

    private HostServices hostServices;

    private String fullUrl;

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void ready(Stage stage, HostServices hostServices) {
        this.stage = stage;
        this.hostServices = hostServices;
        this.searchButton.setDefaultButton(true);
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
            Image image = null;
            if (result.multimedia != null) {
                image = new Image(result.multimedia.src);
            }
            imageView.setImage(image);
        });

        titleLabel.setText("Title: " + getDisplayString(result.displayTitle));
        mpaaLabel.setText("MPAA Rating: " + getDisplayString(result.mpaaRating));
        bylineLabel.setText("Byline: " + getDisplayString(result.byline));
        headlineLabel.setText("Headline Review: " + getDisplayString(result.headline));
        openingDateLabel.setText("Opening Date: " + getDisplayString(result.openingDate));
        publicationDateLabel.setText("Publication Date: " + getDisplayString(result.publicationDate));
        dateUpdatedLabel.setText("Date Updated: " + getDisplayString(result.dateUpdated));
        fullUrl = result.link.url;
        summaryText.setText(getDisplayString(result.summaryShort));
    }

    private String getDisplayString(String input) {
        if (input == null)
            return "-";
        else
            return input;
    }

    @FXML
    public void launchBrowser() {
        hostServices.showDocument(fullUrl);
    }
}
