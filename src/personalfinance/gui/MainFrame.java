package personalfinance.gui;

import personalfinance.gui.dialog.AboutDialog;
import personalfinance.gui.dialog.AccountAddEditDialog;
import personalfinance.gui.dialog.ErrorDialog;
import personalfinance.gui.menu.MainMenu;
import personalfinance.gui.toolbar.MainToolbar;
import personalfinance.settings.Style;
import personalfinance.settings.Text;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Refresh{
    private final GridBagConstraints constraints;
    private final MainMenu mainMenu;
    private final MainToolbar toolbar;


    public MainFrame() {
        super(Text.get("PROGRAMM_NAME"));


        new AccountAddEditDialog(this).showDialog();
//        new AboutDialog().setVisible(true);
//        ErrorDialog.show(this, "Проверка ");
//        MainFileChooser fileChooser = new MainFileChooser(this);
//        fileChooser.save();

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
        toolbar = new MainToolbar();
        add(toolbar, constraints);


        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        //add leftpanel
        add(new MainDatePicker().getDatePicker(), constraints);

        setSize(900, 500);
        setLocationRelativeTo(null);
    }

    @Override
    public void refresh() {
        SwingUtilities.updateComponentTreeUI(this);
        mainMenu.refresh();
        pack();
    }
}
