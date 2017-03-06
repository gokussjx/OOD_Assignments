/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bm346saxparseproject;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author bidyut
 */
public class UserInterfaceController implements Initializable {

    @FXML
    private Label label;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }

    public void ready(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
//                FileInputStream fileIn = new FileInputStream(file.getPath());
//                ObjectInputStream in = new ObjectInputStream(fileIn);

                // HANDLE THE INPUT by using in.readObject()
                BmXMLNode node = BmSAXParser.parse(file);
//                in.close();
//                fileIn.close();

                // Handle whatever you want to do here with the newly made object
            } catch (IOException ioEx) {
                String message = "File open exception. " + file.getPath();
                displayExceptionAlert(message, ioEx);
            } catch (ClassNotFoundException cnfEx) {
                String message = "Class not found exception. " + file.getPath();
                displayExceptionAlert(message, cnfEx);
            } catch (Exception e) {
                String message = "Exception found during parsing. " + file.getPath();
                displayExceptionAlert(message, e);
            }
        }
    }

    private void displayExceptionAlert(String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception!");
        alert.setContentText(message);

        // Create expandable Exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

}
