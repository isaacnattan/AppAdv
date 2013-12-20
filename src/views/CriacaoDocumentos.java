package views;

import javax.swing.JDialog;
import util.BackgroundButton;

/**
 *
 * @author Isaac Nattan
 */
public class CriacaoDocumentos extends JDialog {
    private BackgroundButton botaoWord;
    
    public CriacaoDocumentos() {
        botaoWord = new BackgroundButton("/src/images/docx");
        setLocationRelativeTo(this);
        //setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setVisible(true);
    }
    
    private void montaPainel(){
        
    }
    
    public static void main(String [] args){
        CriacaoDocumentos c = new CriacaoDocumentos();
    }
}
