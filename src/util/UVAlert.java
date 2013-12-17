package util;

import javax.swing.JOptionPane;

/**
 * @author PBoaventura
 */
public class UVAlert {

    public static void alertSucess(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso do Sistema", JOptionPane.OK_OPTION);
    }

    public static void alertError(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertWarning(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso do Sistema", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean inputQuestion(String question, String title) {
        String[] opcs = {"Sim", "Não"};
        int op = JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcs, null);
        return op == JOptionPane.YES_OPTION;
    }

    public static String inputValue(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
    }
}
