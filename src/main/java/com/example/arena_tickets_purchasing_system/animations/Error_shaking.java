package com.example.arena_tickets_purchasing_system.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.w3c.dom.Node;

public class Error_shaking {
    private TranslateTransition translateTransition1;
    private TranslateTransition translateTransition2;

    public Error_shaking (TextArea node1, TextField node2) {
        translateTransition1 = new TranslateTransition(Duration.millis(100), node1);
        translateTransition1.setFromX(0f);
        translateTransition1.setByX(10f);
        translateTransition1.setCycleCount(4);
        translateTransition1.setAutoReverse(true);
        translateTransition2 = new TranslateTransition(Duration.millis(100), node2);
        translateTransition2.setFromX(0f);
        translateTransition2.setByX(10f);
        translateTransition2.setCycleCount(4);
        translateTransition2.setAutoReverse(true);
    }

    public void executeAnimation(){
        translateTransition1.playFromStart();
        translateTransition2.playFromStart();
    }
}
