package mundial_fifa.controller;

import mundial_fifa.model.service.ConfederacionService;
import mundial_fifa.model.service.ContinenteService;
import mundial_fifa.model.service.EstadisticasPartidoEquipoService;
import mundial_fifa.model.service.MundialService;
import mundial_fifa.model.service.PartidoService;
import mundial_fifa.model.service.SeleccionService;
import mundial_fifa.view.MainLayout;
import mundial_fifa.view.component.ConfederacionPanel;
import mundial_fifa.view.component.ContinentePanel;
import mundial_fifa.view.component.EstadisticasPartidoEquipoPanel;
import mundial_fifa.view.component.MundialPanel;
import mundial_fifa.view.component.PartidoPanel;
import mundial_fifa.view.component.SeleccionPanel;

public class Main {

  public static void main(String[] args) {
    MainLayout ventanaPrincipal = new MainLayout();

    ContinentePanel moduloContinente = new ContinentePanel();
    ContinenteService continenteService = new ContinenteService();

    ConfederacionPanel moduloConfederacion = new ConfederacionPanel();
    ConfederacionService confederacionService = new ConfederacionService();

    SeleccionPanel moduloSelecciones = new SeleccionPanel();
    SeleccionService seleccionService = new SeleccionService();

    PartidoPanel moduloPartidos = new PartidoPanel();
    PartidoService partidoService = new PartidoService();

    EstadisticasPartidoEquipoPanel moduloEstadisticas = new EstadisticasPartidoEquipoPanel();
    EstadisticasPartidoEquipoService estadisticasService = new EstadisticasPartidoEquipoService();

    MundialPanel moduloMundiales = new MundialPanel();
    MundialService mundialService = new MundialService();

    new ContinenteController(moduloContinente, continenteService);
    new ConfederacionController(moduloConfederacion, confederacionService, continenteService);
    new SeleccionController(moduloSelecciones, seleccionService, confederacionService);
    new PartidoController(moduloPartidos, partidoService, mundialService, seleccionService);
    new EstadisticasPartidoEquipoController(moduloEstadisticas, estadisticasService, partidoService, seleccionService);
    new MundialController(moduloMundiales, mundialService);

    ventanaPrincipal.setModuloPanel(moduloContinente);

    ventanaPrincipal.getBtnContinentes().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloContinente);
    });

    ventanaPrincipal.getBtnConfederacion().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloConfederacion);
    });

    ventanaPrincipal.getBtnSelecciones().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloSelecciones);
    });

    ventanaPrincipal.getBtnPartidos().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloPartidos);
    });

    ventanaPrincipal.getBtnEstadisticas().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloEstadisticas);
    });

    ventanaPrincipal.getBtnMundiales().addActionListener(e -> {
      ventanaPrincipal.setModuloPanel(moduloMundiales);
    });

    ventanaPrincipal.setVisible(true);
  }

}
