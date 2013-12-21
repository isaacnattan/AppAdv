package views;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

/**
 * @author Isaac Nattan
 */
public class ViewCriaDoc extends JDialog {

    private JButton btword;
    private JButton btPowerPoint;
    private JButton btExcel;
    private JButton btBlocoNotas;
    private JLabel titulo;

    public ViewCriaDoc() {
        montarConteudoCentral();
    }
    
    public void getVisible(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(420, 180);
        setResizable(false);
        setVisible(true);
    }

    private void montarConteudoCentral() {
        SpringLayout layoutRoot = new SpringLayout();
        setLayout(layoutRoot);
        
        titulo = new JLabel("Selecione o tipo do Documento que deseja criar");
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        add(titulo);
        layoutRoot.putConstraint(SpringLayout.WEST, titulo,
                35, SpringLayout.WEST, this);
        layoutRoot.putConstraint(SpringLayout.NORTH, titulo,
                20, SpringLayout.NORTH, this);

        btword = new JButton(new ImageIcon("src/images/word.png"));
        add(btword);
        layoutRoot.putConstraint(SpringLayout.WEST, btword,
                15, SpringLayout.WEST, this);
        layoutRoot.putConstraint(SpringLayout.NORTH, btword,
                70, SpringLayout.NORTH, this);
        
        btPowerPoint = new JButton(new ImageIcon("src/images/PowerPoint.png"));
        add(btPowerPoint);
        layoutRoot.putConstraint(SpringLayout.WEST, btPowerPoint,
                115, SpringLayout.WEST, this);
        layoutRoot.putConstraint(SpringLayout.NORTH, btPowerPoint,
                70, SpringLayout.NORTH, this);
        
        btExcel = new JButton(new ImageIcon("src/images/excel2.png"));
        add(btExcel);
        layoutRoot.putConstraint(SpringLayout.WEST, btExcel,
                215, SpringLayout.WEST, this);
        layoutRoot.putConstraint(SpringLayout.NORTH, btExcel,
                70, SpringLayout.NORTH, this);
        
        btBlocoNotas = new JButton(new ImageIcon("src/images/bloco.png"));
        add(btBlocoNotas);
        layoutRoot.putConstraint(SpringLayout.WEST, btBlocoNotas,
                315, SpringLayout.WEST, this);
        layoutRoot.putConstraint(SpringLayout.NORTH, btBlocoNotas,
                70, SpringLayout.NORTH, this);
    }
    
    // Getters    
    public JButton getBtword() {
        return btword;
    }

    public JButton getBtPowerPoint() {
        return btPowerPoint;
    }

    public JButton getBtExcel() {
        return btExcel;
    }

    public JButton getBtBlocoNotas() {
        return btBlocoNotas;
    }
        
    public static void main(String[] args) {
        ViewCriaDoc c = new ViewCriaDoc();
    }
}
