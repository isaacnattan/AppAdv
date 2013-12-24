package main;

import controladores.CTPai;
import controladores.CTViewPrincipal;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.DAOInfoArquivosTabela;
import util.AutenticacaoHardware;
import views.ViewLogin;

/**
 * @author Isaac Nattan
 */
public class Main extends CTPai {

    private static ViewLogin sessaoLogin;
    public static final String passDefault = "mudar@123";
    public static final String userDefault = "Admin";
    public static final String passEspecial = "especial";
    private static String user;
    private static String pass;

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
            sessaoLogin = new ViewLogin();
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
        if (user.equals(userDefault) && pass.equals(passDefault)) {
            sessaoLogin.dispose();
            atualizaTemps();
            new CTViewPrincipal();
        } else if (user.equals(userDefault) && pass.equals(passEspecial)) {
            sessaoLogin.dispose();
            // Serve para carregar arquivos de informação com ficheiros manuais
            CTViewPrincipal ct = new CTViewPrincipal(null);
            // recarrega-os
            ct.reCarregaArquivosDeInformacao();
            System.exit(1);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Opss ! Senha inválida!");
            // verificar como chamar a telinha de novo
            sessaoLogin.dispose();
            new Main();
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

    private void atualizaTemps() {
        DAOInfoArquivosTabela dao = new DAOInfoArquivosTabela(System.
                getProperty("user.home") + File.separator + "RepositorioDeFicheiros");
        File temp = new File(System.
                getProperty("user.home") + File.separator + "RepositorioDeFicheiros"
                + File.separator + "tempF-" + dao.getNumTempF() + ".txt");
        // Deleta o arquivo de Ficheiros atual
        dao.destroiArquivosDeInformacao();
        try {
            File infoArq = new File(System.
                    getProperty("user.home") + File.separator + "RepositorioDeFicheiros"
                    + File.separator + "infoFicheiro.txt");
            FileReader fr = new FileReader(temp);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(infoArq, true);
            BufferedWriter bw = new BufferedWriter(fw);
            while (br.ready()) {
                // Escreve tudo que ta no ultimo temp no novo infoFicheiro.txt
                bw.write(br.readLine());
            }
            // apaga todos os outros temps
            String[] workspace = new File(System.
                    getProperty("user.home") + File.separator + "RepositorioDeFicheiros").list();
            for (int i = 0; i < workspace.length; i++) {
                if (workspace[i].contains(".")) {
                    if (workspace[i].replace(".", "/").split("/")[0].contains("tempF")) {
                        new File(System.
                                getProperty("user.home") + File.separator
                                + "RepositorioDeFicheiros" + workspace[i]).delete();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            javax.swing.JOptionPane.showMessageDialog(sessaoLogin,
                    "Problemas ao inicializar recriação de arquivos de informação. " + ex);
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(sessaoLogin,
                    "Problemas ao inicializar recriação de arquivos de informação. " + ex);
        }
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
