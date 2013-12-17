package controladores;

import interfaces.IThread;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Isaac_Nattan
 */
public abstract class CTPai extends Thread implements KeyListener, MouseListener, IThread {
    
    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
        
}
