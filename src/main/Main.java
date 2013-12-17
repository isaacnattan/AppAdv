package main;

import controladores.CTPai;
import controladores.CTViewPrincipal;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.AutenticacaoHardware;
import views.Login;

/**
 * @author Isaac Nattan
 */
public class Main extends CTPai {

    private static Login sessaoLogin;
    public static final String passDefault = "mudar@123";
    public static final String userDefault = "Admin";
    private static String user;
    private static String pass;
    private boolean teclouTAB = false;

    public static void main(String[] args) {
        // Seta LookAndFeel
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // default
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex);
        } catch (IllegalAccessException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex);
        } catch (InstantiationException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex);
        } catch (ClassNotFoundException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex);
        }
        AutenticacaoHardware hw = new AutenticacaoHardware();
        if (hw.autenticacao()) {
            Main main = new Main();
            sessaoLogin = new Login();
            main.addListeners();
            main.addFocusListener();
            sessaoLogin.setLocationRelativeTo(sessaoLogin);
            sessaoLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sessaoLogin.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(sessaoLogin, "");
        }
    }

    private void validaUser() {
        if (userDefault.equals(user) && passDefault.equals(pass)) {
            sessaoLogin.dispose();
            new CTViewPrincipal();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Opss ! Senha inválida!");
            System.exit(0);
        }
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
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                pass = new String(sessaoLogin.getInputPass().getPassword());
                sessaoLogin.getButtonOk().requestFocus();
            }
        } else if (ke.getSource() == sessaoLogin.getButtonOk()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                validaUser();
            }
        } else if (ke.getSource() == sessaoLogin.getButtonCancel()) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                sessaoLogin.dispose();
            }
        }
    }
    
    private void addFocusListener() {
        sessaoLogin.getInputUser().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                sessaoLogin.getInputUser().setBackground(Color.YELLOW);
            }

            @Override
            public void focusLost(FocusEvent e) {
                sessaoLogin.getInputUser().setBackground(Color.WHITE);
                //Quando perder o foco o cara ja apertou tab, logo
                if (sessaoLogin.getInputUser().getText() != null) {
                    user = sessaoLogin.getInputUser().getText();
                    sessaoLogin.getInputPass().requestFocus();
                }
            }
        });

        sessaoLogin.getInputPass().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                sessaoLogin.getInputPass().setBackground(Color.YELLOW);
            }

            @Override
            public void focusLost(FocusEvent e) {
                sessaoLogin.getInputPass().setBackground(Color.WHITE);
                if (sessaoLogin.getInputPass().getPassword().length > 0) {
                    pass = new String(sessaoLogin.getInputPass().getPassword());
                    sessaoLogin.getButtonOk().requestFocus();
                }
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == sessaoLogin.getButtonOk()) {
            validaUser();
        } else if (me.getSource() == sessaoLogin.getButtonCancel()) {
            sessaoLogin.dispose();
        }
    }
}
