package com.example.arena_tickets_purchasing_system.animations;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;
public class ButtonsSkin extends ButtonSkin {

    public ButtonsSkin(Button button) {
        super(button);

    final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
    fadeIn.setNode(button);
    fadeIn.setToValue(5);
    button.setOnMouseEntered(e -> fadeIn.playFromStart());

    final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
    fadeOut.setNode(button);
    fadeOut.setToValue(0.5);
    button.setOnMouseExited(e -> fadeOut.playFromStart());

    button.setOpacity(0.5);

    }
}
