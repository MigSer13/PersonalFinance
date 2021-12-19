package personalfinance;

import personalfinance.settings.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalFinance {

    public static void main(String[] args) {
        init();
    }

    public static void init(){
        try {
            Text.init();
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(
                    Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Roboto-Light.ttf")));
        }catch (FontFormatException | IOException e){
            Logger.getLogger(PersonalFinance.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
