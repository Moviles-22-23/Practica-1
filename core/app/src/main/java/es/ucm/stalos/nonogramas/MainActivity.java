package es.ucm.stalos.nonogramas;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.stalos.androidengine.AndroidEngine;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.states.LoadState;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _surface = new SurfaceView(this);
        setContentView(_surface);

        _engine = new AndroidEngine();
        State loadAssets = new LoadState(_engine);

        if (!_engine.init(loadAssets, 400, 600, this._surface)) {
            System.out.println("Error al inicializar el engine");
            return;
        }

        _engine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _engine.pause();
    }

    protected AndroidEngine _engine;
    protected SurfaceView _surface;
}