package es.ucm.stalos.nonogramas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

import es.ucm.stalos.androidengine.AndroidEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _surface = new SurfaceView(this);
        setContentView(_surface);

        _engine = new AndroidEngine();

        //if(!_engine.init(loadAssets, 400, 600));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected AndroidEngine _engine;
    protected SurfaceView _surface;
}