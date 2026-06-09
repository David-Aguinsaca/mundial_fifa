package mundial_fifa.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.EstadisticasPartidoEquipo;
import mundial_fifa.model.entity.Partido;
import mundial_fifa.model.entity.Seleccion;
import mundial_fifa.model.service.EstadisticasPartidoEquipoService;
import mundial_fifa.model.service.PartidoService;
import mundial_fifa.model.service.SeleccionService;
import mundial_fifa.view.component.EstadisticasPartidoEquipoFormDialog.PartidoItem;
import mundial_fifa.view.component.EstadisticasPartidoEquipoFormDialog.SeleccionItem;
import mundial_fifa.view.component.EstadisticasPartidoEquipoPanel;

public class EstadisticasPartidoEquipoController {

    private EstadisticasPartidoEquipoPanel vista;
    private EstadisticasPartidoEquipoService servicio;
    private PartidoService partidoService;
    private SeleccionService seleccionService;

    public EstadisticasPartidoEquipoController(EstadisticasPartidoEquipoPanel vista,
                                                EstadisticasPartidoEquipoService servicio,
                                                PartidoService partidoService,
                                                SeleccionService seleccionService) {
        this.vista = vista;
        this.servicio = servicio;
        this.partidoService = partidoService;
        this.seleccionService = seleccionService;

        this.vista.escucharBotonRefrescar(e -> cargarTabla());
        this.vista.escucharBotonCrear(e -> abrirFormularioCrear());
        this.vista.escucharBotonEditar(e -> abrirFormularioEditar());
        this.vista.escucharBotonEliminar(e -> abrirFormularioEliminar());

        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<EstadisticasPartidoEquipo> lista = servicio.obtenerTodos();

        for (EstadisticasPartidoEquipo e : lista) {
            modelo.addRow(new Object[] {
                    e.getIdEstadistica(),
                    e.getPartido().getFecha() + " - " + e.getPartido().getFase(),
                    e.getSeleccion().getNombre(),
                    e.getPosesionPorcentaje(),
                    e.getTirosAlArco(),
                    e.getTirosEsquina(),
                    e.getTirosLibres(),
                    e.getFaltas(),
                    e.getPrecisionPasesPorcentaje(),
                    e.getFueraDeJuego(),
                    e.getSalvadasPortero(),
                    e.getEstado(),
                    e.getFechaCreacion(),
                    e.getFechaModificacion()
            });
        }
    }

    private DefaultComboBoxModel<PartidoItem> construirModeloPartidos() {
        DefaultComboBoxModel<PartidoItem> modelo = new DefaultComboBoxModel<>();
        try {
            List<Partido> partidos = partidoService.obtenerTodos();
            for (Partido p : partidos) {
                modelo.addElement(new PartidoItem(p.getIdPartido(), p.getFecha().toString(), p.getFase()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar los partidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    private DefaultComboBoxModel<SeleccionItem> construirModeloSelecciones() {
        DefaultComboBoxModel<SeleccionItem> modelo = new DefaultComboBoxModel<>();
        try {
            List<Seleccion> selecciones = seleccionService.listarTodosByEstado();
            for (Seleccion s : selecciones) {
                modelo.addElement(new SeleccionItem(s.getIdSeleccion(), s.getNombre()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar las selecciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    private void abrirFormularioCrear() {
        DefaultComboBoxModel<PartidoItem> modeloPartidos = construirModeloPartidos();
        DefaultComboBoxModel<SeleccionItem> modeloSelecciones = construirModeloSelecciones();
        EstadisticasPartidoEquipo nuevo = vista.mostrarModalFormulario(null, modeloPartidos, modeloSelecciones);

        if (nuevo != null) {
            try {
                servicio.registrarEstadistica(nuevo);
                JOptionPane.showMessageDialog(vista, "Estadística creada con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormularioEditar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una estadística de la tabla para editar.");
            return;
        }

        try {
            EstadisticasPartidoEquipo estadisticaAEditar = servicio.buscarPorId(idSeleccionado);
            DefaultComboBoxModel<PartidoItem> modeloPartidos = construirModeloPartidos();
            DefaultComboBoxModel<SeleccionItem> modeloSelecciones = construirModeloSelecciones();
            EstadisticasPartidoEquipo estadisticaModificada = vista.mostrarModalFormulario(
                    estadisticaAEditar, modeloPartidos, modeloSelecciones);

            if (estadisticaModificada != null) {
                servicio.actualizarEstadistica(estadisticaModificada);
                JOptionPane.showMessageDialog(vista, "Estadística actualizada correctamente.");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioEliminar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una estadística primero.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas eliminar esta estadística?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                servicio.eliminarEstadistica(idSeleccionado);
                JOptionPane.showMessageDialog(vista, "Estadística eliminada con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}