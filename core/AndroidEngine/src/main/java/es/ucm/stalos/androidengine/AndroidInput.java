package es.ucm.stalos.androidengine;

import android.view.MotionEvent;
import android.view.View;

import es.ucm.stalos.engine.AbstractInput;
import es.ucm.stalos.engine.IEngine;

import java.util.ArrayList;


public class AndroidInput extends AbstractInput implements View.OnTouchListener {
    public AndroidInput(IEngine e) {
        super(e);
        _events = new ArrayList<>();
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            onTouchDownEvent((int) e.getX(), (int) e.getY());
        }
        return true;
    }
}
