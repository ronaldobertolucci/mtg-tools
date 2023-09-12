package br.com.bertolucci.mtgtools.ui.util;

import javax.swing.*;
import java.awt.*;

public class OptionDialogUtil {

    public static int showDialog(Window component, String message) {
        Object[] options = { "Sim", "Não" };
        return JOptionPane.showOptionDialog(component, message, "Selecione uma opção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

}
