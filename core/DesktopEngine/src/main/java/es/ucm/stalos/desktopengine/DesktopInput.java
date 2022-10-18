package es.ucm.stalos.desktopengine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import es.ucm.stalos.engine.AbstractInput;
import es.ucm.stalos.engine.Engine;

public class DesktopInput extends AbstractInput implements MouseListener, MouseMotionListener {
    public DesktopInput(Engine e) {
        super(e);
        _events = new ArrayList<>();
    }

    public boolean init() {
        return _events != null;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        onTouchDownEvent(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
