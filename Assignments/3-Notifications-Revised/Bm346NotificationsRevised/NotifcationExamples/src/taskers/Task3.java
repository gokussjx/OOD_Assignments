/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.application.Platform;
import revisions.TaskState;

/**
 *
 * @author dalemusser
 * 
 * This example uses PropertyChangeSupport to implement
 * property change listeners.
 * 
 */
public class Task3 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public Task3(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
//        doNotify("Task3 start.");
        doNotify(TaskState.STARTED, 0);
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
//                doNotify("It happened in Task3: " + i);
                doNotify(TaskState.RUNNING, i);
            }
            
            if (exit) {
                doNotify(TaskState.EXITED, 0);
                return;
            }
        }
        doNotify(TaskState.COMPLETED, 0);
//        doNotify("Task3 done.");
    }
    
    public void end() {
        exit = true;
    }
    
    // the following two methods allow property change listeners to be added
    // and removed
    public void addPropertyChangeListener(PropertyChangeListener listener) {
         pcs.addPropertyChangeListener(listener);
     }

     public void removePropertyChangeListener(PropertyChangeListener listener) {
         pcs.removePropertyChangeListener(listener);
     }
    
    private void doNotify(TaskState state, int i) {
        // this provides the notification through the property change listener
        Platform.runLater(() -> {
            // I'm choosing not to send the old value (second param).  Sending null instead.
            pcs.fireIndexedPropertyChange("state", i, null, state);
        });
    }
}