/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import revisions.TaskState;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;

    @FXML
    private Button buttonTaskOne;

    @FXML
    private Button buttonTaskTwo;

    @FXML
    private Button buttonTaskThree;

    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buttonTaskOne.setText("Start Task1");
        buttonTaskTwo.setText("Start Task2");
        buttonTaskThree.setText("Start Task3");
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        } else {
            task1.end();
            task1 = null;
        }
    }
    
    @Override
    public void notify(TaskState state, int i) {

        switch (state) {
            case STARTED:
                textArea.appendText("Task1 start." + "\n");
                buttonTaskOne.setText("End Task1");
                break;
            case RUNNING:
                textArea.appendText("It happened in Task1: " + i + "\n");
                break;
            case COMPLETED: case EXITED:
                task1 = null;
                textArea.appendText("Task1 done." + "\n");
                buttonTaskOne.setText("Start Task1");
                break;
        }
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((TaskState state, int i) -> {
                switch (state) {
                    case STARTED:
                        textArea.appendText("Task2 start." + "\n");
                        buttonTaskTwo.setText("End Task2");
                        break;
                    case RUNNING:
                        textArea.appendText("It happened in Task2: " + i + "\n");
                        break;
                    case COMPLETED: case EXITED:
                        task2 = null;
                        textArea.appendText("Task2 done." + "\n");
                        buttonTaskTwo.setText("Start Task2");
                        break;
                }
            });
            
            task2.start();
        } else {
            task2.end();
            task2 = null;
        }
    }

    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                int i = ((IndexedPropertyChangeEvent) evt).getIndex();
                TaskState state = (TaskState) evt.getNewValue();
                switch (state) {
                    case STARTED:
                        textArea.appendText("Task3 start." + "\n");
                        buttonTaskThree.setText("End Task3");
                        break;
                    case RUNNING:
                        textArea.appendText("It happened in Task3: " + i + "\n");
                        break;
                    case COMPLETED: case EXITED:
                        task3 = null;
                        textArea.appendText("Task3 done." + "\n");
                        buttonTaskThree.setText("Start Task3");
                        break;
                }
            });
            
            task3.start();
        }  else {
            task3.end();
            task3 = null;
        }
    } 
}
