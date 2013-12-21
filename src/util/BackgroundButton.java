package util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

public class BackgroundButton extends javax.swing.JButton {
    //Carrega a sua imagem  

    ImageIcon imagem;

    public BackgroundButton(String pathImage) {
        imagem = new ImageIcon(pathImage);
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Image backgroundImage = imagem.getImage();
        double scaleX = getWidth() / (double) backgroundImage.getWidth(null);
        double scaleY = getHeight() / (double) backgroundImage.getHeight(null);
        AffineTransform xform = AffineTransform.getScaleInstance(scaleX, scaleY);
        ((Graphics2D) g).drawImage(backgroundImage, xform, this);
        String texto = this.getText();
        // Find the size of string s in font f in the current Graphics context g.  
        Font font = new Font("Dialog", Font.PLAIN, 11);
        java.awt.FontMetrics fm = g.getFontMetrics(font);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(texto, g);
        int textWidth = (int) (rect.getWidth());
        int textHeight = (int) (rect.getHeight());
        Dimension size = this.getSize();
        int x = (size.width - textWidth) / 2;
        int y = (size.height - textHeight) / 2 + fm.getAscent();
        g.drawString(texto, x, y);
    }
    
    public static void main(String [] args){
        BackgroundButton bkb = new BackgroundButton("src/images/docx.png");
        JPanel painelBtn = new JPanel(new BorderLayout());
        painelBtn.add(bkb, BorderLayout.CENTER);
        SpringLayout layout = new SpringLayout();
        JFrame tela = new JFrame();
        tela.setLayout(layout);
        tela.add(painelBtn);
        layout.putConstraint(SpringLayout.WEST, bkb,
                15, SpringLayout.WEST, tela);
        layout.putConstraint(SpringLayout.NORTH, bkb,
                20, SpringLayout.NORTH, tela);
        tela.setLocationRelativeTo(tela);
        tela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tela.setSize(400, 300);
        tela.setResizable(false);
        tela.setVisible(true);
    }
}
