package controladores;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import views.Login;

/**
 * @author Isaac Nattan
 * @version 1.0
 * @since 12/12/13
 */
public class CTLogin extends CTPai {

    public final String passDefault = "mudar@123";
    public final String userDefault = "Admin";
    private Login sessaoLogin;
    // informações interessantes
    public String pass = null;
    public String user = null;

    public CTLogin() {
        sessaoLogin = new Login();
        addListeners();
        sessaoLogin.setLocationRelativeTo(sessaoLogin);
        sessaoLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sessaoLogin.setVisible(true);
    }

    private void addListeners() {
        sessaoLogin.getInputPass().addKeyListener(this);
        sessaoLogin.getInputUser().addKeyListener(this);
        sessaoLogin.getButtonCancel().addKeyListener(this);
        sessaoLogin.getButtonOk().addKeyListener(this);
        sessaoLogin.getButtonCancel().addMouseListener(this);
        sessaoLogin.getButtonOk().addMouseListener(this);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == sessaoLogin.getInputUser()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                user = sessaoLogin.getInputUser().getText();
                sessaoLogin.getInputPass().requestFocus();
            }
        } else if (ke.getSource() == sessaoLogin.getInputPass()) {
            char[] password = sessaoLogin.getInputPass().getPassword();
            pass = password.toString();
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                sessaoLogin.getInputPass().requestFocus();
            }
        } else if (ke.getSource() == sessaoLogin.getButtonOk()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                sessaoLogin.dispose();
            }
        } else if (ke.getSource() == sessaoLogin.getButtonCancel()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                sessaoLogin.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == sessaoLogin.getButtonOk()) {
            sessaoLogin.dispose();
        } else if (me.getSource() == sessaoLogin.getButtonCancel()) {
            sessaoLogin.dispose();
        }
    }
    
    public static void main (String [] args){
        CTLogin ctl = new CTLogin();
    }
}
