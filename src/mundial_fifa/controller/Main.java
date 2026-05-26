package mundial_fifa.controller;

import mundial_fifa.model.service.ConfederacionService;
import mundial_fifa.model.service.ContinenteService;
import mundial_fifa.model.service.MundialService;
import mundial_fifa.view.MainLayout;
import mundial_fifa.view.component.ConfederacionPanel;
import mundial_fifa.view.component.ContinentePanel;
import mundial_fifa.view.component.MundialPanel;

public class Main {

  public static void main(String[] args) {
    MainLayout ventanaPrincipal = new MainLayout();

    ContinentePanel moduloContinente = new ContinentePanel();
    ContinenteService continenteService = new ContinenteService();

    ConfederacionPanel moduloConfederacion = new ConfederacionPanel();
    ConfederacionService confederacionService = new ConfederacionService();

    MundialPanel moduloMundiales = new MundialPanel();
    MundialService mundialService = new MundialService();

    new ContinenteController(moduloContinente, continenteService);
    new ConfederacionController(moduloConfederacion, confederacionService, continenteService);
    new MundialController(moduloMundiales, mundialService);

    ventanaPrincipal.setModuloPanel(moduloContinente);

    ventanaPrincipal.getBtnContinentes().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloContinente);
    });

    ventanaPrincipal.getBtnConfederacion().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloConfederacion);
    });

    ventanaPrincipal.getBtnMundiales().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloMundiales);
    });

    ventanaPrincipal.setVisible(true);
  }

}
