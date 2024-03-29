/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dale
 */
public class CheckersUIController implements Initializable {
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private StackPane stackPane;
    
    private Stage stage;
    private Scene scene;
    
    private double boardWidth;
    private double boardHeight;

    private int numRows;
    private int numCols;
    
    private Color darkColor;
    private Color lightColor;
    
    private CheckerBoard checkerBoard;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDefaultColor();
        setDefaultRowsCols();
    }   
    
    public void ready(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
        
        ChangeListener<Number> listener = (ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) -> {
            renderBoard();
        };

        scene.widthProperty().addListener(listener);
        scene.heightProperty().addListener(listener);
        
        renderBoard();
    }

    private void renderBoard() {

        boardWidth = scene.getWidth();
        boardHeight = scene.getHeight() - menuBar.getHeight();

        double padding;
        if (boardHeight > boardWidth) {
            padding = (boardHeight - boardWidth) / 2.0;
            boardHeight -= 2 * padding;
            stackPane.setPadding(new Insets(padding, 0, padding, 0));
        } else {
            padding = (boardWidth - boardHeight) / 2.0;
            boardWidth -= 2 * padding;
            stackPane.setPadding(new Insets(0, padding, 0, padding));
        }

        stackPane.getChildren().clear();
        checkerBoard = new CheckerBoard(numRows, numCols, boardWidth, boardHeight, lightColor, darkColor);
        stackPane.getChildren().add(checkerBoard.build());
    }
    
    @FXML
    public void selectSize(ActionEvent event) {
        MenuItem menuItem = (MenuItem)(event.getSource());
        switch(menuItem.getId()) {
            case "16 x 16":
                numRows = 16;
                numCols = 16;
                break;
            case "10 x 10":
                numRows = 10;
                numCols = 10;
                break;
            case "8 x 8":
                numRows = 8;
                numCols = 8;
                break;
            case "3 x 3":
                numRows = 3;
                numCols = 3;
                break;
            default:
                setDefaultRowsCols();
        }
        renderBoard();
    }
    
    @FXML
    public void selectColor(ActionEvent event) {
        MenuItem menuItem = (MenuItem)(event.getSource());
        switch(menuItem.getId()) {
            case "Blue":
                setBlueColor();
                break;
            default:
                setDefaultColor();
        }
        renderBoard();
    }
    
    private void setDefaultColor() {
        darkColor = Color.BLACK;
        lightColor = Color.RED;
    }
    
    private void setBlueColor() {
        darkColor = Color.DARKBLUE;
        lightColor = Color.SKYBLUE;
    }
    
    private void setDefaultRowsCols() {
        numRows = 8;
        numCols = 8;
    }
}
