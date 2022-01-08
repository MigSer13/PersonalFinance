package personalfinance.gui;

import personalfinance.settings.Style;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainButton extends JButton {

    public MainButton(String title, ImageIcon icon, ActionListener listener, String action){
        super(title);
        setIcon(icon);
        addActionListener(listener);
        addMouseListener(new HoverButton());
        setActionCommand(action);

        setFont(Style.FONT_MAIN_BUTTON);
        setFocusPainted(false);
        setBackground(Style.COLOR_BUTTON_BG_NORMAL);
    }
    public MainButton(String title, ActionListener listener, String action){
        this(title, null, listener, action);
    }
    public MainButton(ImageIcon icon, ActionListener listener, String action){
        this("", icon, listener, action);
    }

    private class HoverButton implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            ((MainButton) e.getSource()).setBackground(Style.COLOR_BUTTON_BG_HOVER);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((MainButton) e.getSource()).setBackground(Style.COLOR_BUTTON_BG_NORMAL);
        }
    }
}
