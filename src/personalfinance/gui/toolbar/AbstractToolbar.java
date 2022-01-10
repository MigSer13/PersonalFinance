package personalfinance.gui.toolbar;

import personalfinance.gui.MainButton;
import personalfinance.gui.Refresh;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

abstract public class AbstractToolbar extends JPanel implements Refresh {

    public AbstractToolbar(EmptyBorder border) {
        super();
        setBorder(border);
    }

    abstract protected void init();

     @Override
     public void refresh() {
        removeAll();
        init();
     }

     protected MainButton addButton(String title, ImageIcon icon, String action, boolean topIcon){
        MainButton button = new MainButton(title, icon, null, action);
        if (topIcon){
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
        }else {
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setVerticalTextPosition(SwingConstants.CENTER);
        }
        add(button);
        return button;
     }
 }
