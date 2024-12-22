package ro.itarcea.spaceInvaders.gui;

import javax.swing.*;
import java.awt.*;

public interface GameComponent {
    default void setDesign(JComponent c) {
        c.setFont(Display.pixelFont);
        c.setForeground(Color.RED);
        c.setBackground(Color.BLACK);
        c.setOpaque(true);
    }
}
