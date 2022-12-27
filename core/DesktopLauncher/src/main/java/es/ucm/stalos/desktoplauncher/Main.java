package es.ucm.stalos.desktoplauncher;

import es.ucm.stalos.desktopengine.DesktopEngine;
import es.ucm.stalos.engine.IState;
import es.ucm.stalos.logic.states.LoadState;

class Main {
    public static void main(String[] args) {
        DesktopEngine engine = new DesktopEngine();
        IState loadAssets = new LoadState(engine);
        if (!engine.init(loadAssets, "Nonogramas", 400, 600)) {
            System.out.println("Error al inicializar el engine");
            return;
        }
        engine.resume();
    }
}