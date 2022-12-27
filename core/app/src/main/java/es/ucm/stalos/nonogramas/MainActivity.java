package es.ucm.stalos.nonogramas;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.stalos.androidengine.AndroidEngine;
import es.ucm.stalos.logic.states.LoadState;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        SurfaceView surfaceView = new SurfaceView(this.getApplicationContext());
        this.setContentView(surfaceView);

        _engine = new AndroidEngine();
        LoadState loadAssets = new LoadState(_engine);

        if (!_engine.init(loadAssets, 400, 600, this, surfaceView)) {
            System.out.println("Error al inicializar el engine");
        }
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
}