package es.ucm.stalos.desktopengine;

import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DesktopScreen extends JFrame {
    public DesktopScreen(String titulo){
        super(titulo);
    }

    public boolean init(int winWidth, int winHeight){
        setSize(winWidth, winHeight);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        // Renderizado activo
        setIgnoreRepaint(true);

        int intentos = 100;
        while (intentos-- > 0) {
            try {
                createBufferStrategy(2);
                break;
            } catch (Exception ignored) {
            }
        }
        if (intentos <= 0) {
            System.err.println("No pude crear la BufferStrategy");
            return false;
        } else {
            System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        _strategy = getBufferStrategy();
        return true;
    }

    public BufferStrategy getStrategy(){
        return _strategy;
    }

    private BufferStrategy _strategy;
}
