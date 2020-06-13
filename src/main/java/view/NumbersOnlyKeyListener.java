package view;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class NumbersOnlyKeyListener implements KeyListener {

    JTextField field;

    public NumbersOnlyKeyListener(JTextField textField) {
        field = textField;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        field.setEditable(true);
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            field.setEditable(true);
        } else if ((e.getKeyChar() >= ' ' && e.getKeyChar() <= '/') || (e.getKeyChar() >= ':' && e.getKeyChar() <= '~')) {
            field.setEditable(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
