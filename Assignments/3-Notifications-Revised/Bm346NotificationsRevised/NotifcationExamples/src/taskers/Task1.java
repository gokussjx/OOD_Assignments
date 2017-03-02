/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;
import revisions.*;

/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    private boolean exit = false;
    
    private Notifiable notificationTarget;
    
    public Task1(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify(TaskState.STARTED, 0);
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify(TaskState.RUNNING, i);
            }
            
            if (exit) {
                doNotify(TaskState.EXITED, 0);
                return;
            }
        }
        doNotify(TaskState.COMPLETED, 0);
    }

    public void end() {
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(TaskState state, int i) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> notificationTarget.notify(state, i));
        }
    }
}
