package personalfinance.gui.dialog;

import personalfinance.settings.Style;
import personalfinance.settings.Text;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AboutDialog extends JDialog {
    public AboutDialog() {
        super();
        init();
        setTitle(Text.get("DIALOG_ABOUT_TITLE"));
        setResizable(false);
        setIconImage(Style.ICON_MENU_HELP_ABOUT.getImage());
    }

    private void init() {
        JEditorPane pane = new JEditorPane("text/html", Text.get("ABOUT"));
        pane.setEditable(false);
        pane.setOpaque(false);

        pane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())){
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        });

        add(pane);
        pack();
        setLocationRelativeTo(null);
    }
}
