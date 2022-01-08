package personalfinance.gui;

import personalfinance.gui.menu.MainMenu;
import personalfinance.settings.Style;
import personalfinance.settings.Text;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Refresh{
    private GridBagConstraints constraints;
    private final MainMenu mainMenu;

    public MainFrame() {
        super(Text.get("PROGRAMM_NAME"));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainMenu = new MainMenu(this);
        setJMenuBar(mainMenu);

        setIconImage(Style.ICON_MAIN.getImage());
        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        //add toolbar

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        //add leftpanel

        setSize(700, 700);
        setLocationRelativeTo(null);
    }

    @Override
    public void refresh() {
        SwingUtilities.updateComponentTreeUI(this);
        mainMenu.refresh();
        pack();
    }
}
