package br.com.bertolucci.mtgtools.ui.util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class FontUtil {

    public static <T extends JComponent> void setFont(List<T> components, Font font) {
        components.forEach(component -> setFont(component, font));
    }

    public static <T extends JComponent> void setFont(T component, Font font) {
        component.setFont(font);
    }

    public static Font getFont(String filename, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    FontUtil.class.getClassLoader().getResourceAsStream(filename)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
