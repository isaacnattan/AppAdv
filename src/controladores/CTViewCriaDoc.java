package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import views.ViewCriaDoc;

/**
 * @author Isaac_Nattan
 */
public class CTViewCriaDoc extends CTPai implements MouseListener, KeyListener {

    private ViewCriaDoc viewCriaDoc;
    private String extensao;

    /*public CTViewCriaDoc() {
        viewCriaDoc = new ViewCriaDoc();
        addListeners();
        viewCriaDoc.setLocationRelativeTo(null);
        viewCriaDoc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        viewCriaDoc.setSize(420, 180);
        viewCriaDoc.setResizable(false);
        viewCriaDoc.setVisible(true);
    }*/

    @Override
    public void run() {
        viewCriaDoc = new ViewCriaDoc();
        addListeners();
        addAtalhos();
        viewCriaDoc.setLocationRelativeTo(null);
        viewCriaDoc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        viewCriaDoc.setSize(420, 180);
        viewCriaDoc.setResizable(false);
        viewCriaDoc.setVisible(true);
    }

    private void addListeners() {
        viewCriaDoc.getBtword().addMouseListener(this);
        viewCriaDoc.getBtExcel().addMouseListener(this);
        viewCriaDoc.getBtPowerPoint().addMouseListener(this);
        viewCriaDoc.getBtBlocoNotas().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == viewCriaDoc.getBtword()) {
            setDoc(".docx");
        } else if (me.getSource() == viewCriaDoc.getBtExcel()) {
            setDoc(".xlsx");
        } else if (me.getSource() == viewCriaDoc.getBtPowerPoint()) {
            setDoc(".ppt");
        } else if (me.getSource() == viewCriaDoc.getBtBlocoNotas()) {
            setDoc(".txt");
        }
    }

    private void setDoc(String extensao) {
        this.extensao = extensao;
        javax.swing.JOptionPane.showMessageDialog(viewCriaDoc, extensao);
    }

    public String getDoc() {
        return extensao;
    }
    
    private void addAtalhos() {
        //Atalho <ESC>
        viewCriaDoc.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelarAction");
        viewCriaDoc.getRootPane().getActionMap().put("cancelarAction", new AbstractAction("cancelarAction") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        CTViewCriaDoc ct;
        ct = new CTViewCriaDoc();
        ct.start();
    }
}
