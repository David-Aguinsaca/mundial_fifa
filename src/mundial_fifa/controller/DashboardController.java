package mundial_fifa.controller;

import mundial_fifa.model.entity.EstadisticasPartidoEquipo;
import mundial_fifa.model.entity.Mundial;
import mundial_fifa.model.entity.Partido;
import mundial_fifa.model.service.EstadisticasPartidoEquipoService;
import mundial_fifa.model.service.MundialService;
import mundial_fifa.model.service.PartidoService;
import mundial_fifa.view.component.DashboardPanel;
import mundial_fifa.view.component.DashboardPanel.MundialItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class DashboardController {

    private DashboardPanel vista;
    private MundialService mundialService;
    private PartidoService partidoService;
    private EstadisticasPartidoEquipoService estadisticasService;

    public DashboardController(DashboardPanel vista,
                               MundialService mundialService,
                               PartidoService partidoService,
                               EstadisticasPartidoEquipoService estadisticasService) {
        this.vista = vista;
        this.mundialService = mundialService;
        this.partidoService = partidoService;
        this.estadisticasService = estadisticasService;

        this.vista.getComboMundiales().addActionListener(this::onMundialSeleccionado);
        this.vista.getTablaPartidos().getSelectionModel().addListSelectionListener(this::onPartidoSeleccionado);

        cargarMundiales();
    }

    private void cargarMundiales() {
        JComboBox<MundialItem> combo = vista.getComboMundiales();
        combo.removeAllItems();

        try {
            List<Mundial> mundiales = mundialService.obtenerTodos();
            for (Mundial m : mundiales) {
                combo.addItem(new MundialItem(m.getIdMundial(), m.getAnio(), m.getPaisAnfitrion()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar los mundiales.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onMundialSeleccionado(ActionEvent e) {
        MundialItem seleccionado = (MundialItem) vista.getComboMundiales().getSelectedItem();
        if (seleccionado == null) {
            return;
        }

        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        try {
            List<Partido> partidos = partidoService.obtenerPorMundial(seleccionado.getIdMundial());
            for (Partido p : partidos) {
                modelo.addRow(new Object[]{
                        p.getIdPartido(),
                        p.getFecha(),
                        p.getFase(),
                        p.getSeleccionLocal().getNombre(),
                        p.getGolesLocal(),
                        p.getGolesVisitante(),
                        p.getSeleccionVisitante().getNombre()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onPartidoSeleccionado(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        int fila = vista.getTablaPartidos().getSelectedRow();
        if (fila < 0) {
            return;
        }

        Integer idPartido = (Integer) vista.getModeloTabla().getValueAt(fila, 0);
        if (idPartido == null) {
            return;
        }

        try {
            List<EstadisticasPartidoEquipo> stats = estadisticasService.obtenerPorPartido(idPartido);
            if (stats.size() < 2) {
                JOptionPane.showMessageDialog(vista, "No hay estadísticas completas para este partido.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            EstadisticasPartidoEquipo statL = stats.get(0);
            EstadisticasPartidoEquipo statV = stats.get(1);

            String nombreL = statL.getSeleccion().getNombre();
            String nombreV = statV.getSeleccion().getNombre();

            vista.actualizarGraficoOfensiva(
                    nombreL, nombreV,
                    statL.getTirosAlArco(), statV.getTirosAlArco(),
                    statL.getTirosEsquina(), statV.getTirosEsquina(),
                    statL.getTirosLibres(), statV.getTirosLibres()
            );

            vista.actualizarGraficoDisciplina(
                    nombreL, nombreV,
                    statL.getFaltas(), statV.getFaltas(),
                    statL.getFueraDeJuego(), statV.getFueraDeJuego()
            );

            vista.actualizarGraficoControl(
                    nombreL, nombreV,
                    statL.getPosesionPorcentaje().doubleValue(),
                    statV.getPosesionPorcentaje().doubleValue(),
                    statL.getPrecisionPasesPorcentaje().doubleValue(),
                    statV.getPrecisionPasesPorcentaje().doubleValue(),
                    statL.getSalvadasPortero(), statV.getSalvadasPortero()
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
