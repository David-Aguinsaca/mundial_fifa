package mundial_fifa.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.Mundial;
import mundial_fifa.model.entity.Partido;
import mundial_fifa.model.entity.Seleccion;
import mundial_fifa.model.service.MundialService;
import mundial_fifa.model.service.PartidoService;
import mundial_fifa.model.service.SeleccionService;
import mundial_fifa.view.component.PartidoFormDialog.MundialItem;
import mundial_fifa.view.component.PartidoFormDialog.SeleccionItem;
import mundial_fifa.view.component.PartidoPanel;

public class PartidoController {

    private PartidoPanel vista;
    private PartidoService servicio;
    private MundialService mundialService;
    private SeleccionService seleccionService;

    public PartidoController(PartidoPanel vista, PartidoService servicio,
                             MundialService mundialService, SeleccionService seleccionService) {
        this.vista = vista;
        this.servicio = servicio;
        this.mundialService = mundialService;
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

        List<Partido> lista = servicio.obtenerTodos();

        for (Partido p : lista) {
            modelo.addRow(new Object[] {
                    p.getIdPartido(),
                    p.getFecha(),
                    p.getFase(),
                    p.getSeleccionLocal().getNombre(),
                    p.getGolesLocal(),
                    p.getGolesVisitante(),
                    p.getSeleccionVisitante().getNombre(),
                    p.getMundial().getAnio() + " - " + p.getMundial().getPaisAnfitrion(),
                    p.getEstado(),
                    p.getFechaCreacion(),
                    p.getFechaModificacion()
            });
        }
    }

    private DefaultComboBoxModel<MundialItem> construirModeloMundiales() {
        DefaultComboBoxModel<MundialItem> modelo = new DefaultComboBoxModel<>();
        try {
            List<Mundial> mundiales = mundialService.obtenerTodos();
            for (Mundial m : mundiales) {
                modelo.addElement(new MundialItem(m.getIdMundial(), m.getAnio(), m.getPaisAnfitrion()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar los mundiales.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    private DefaultComboBoxModel<SeleccionItem> construirModeloSelecciones() {
        DefaultComboBoxModel<SeleccionItem> modelo = new DefaultComboBoxModel<>();
        try {
            List<Seleccion> selecciones = seleccionService.obtenerTodos();
            for (Seleccion s : selecciones) {
                modelo.addElement(new SeleccionItem(s.getIdSeleccion(), s.getNombre()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar las selecciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    private void abrirFormularioCrear() {
        DefaultComboBoxModel<MundialItem> modeloMundiales = construirModeloMundiales();
        DefaultComboBoxModel<SeleccionItem> modeloSelecciones = construirModeloSelecciones();
        Partido nuevo = vista.mostrarModalFormulario(null, modeloMundiales, modeloSelecciones);

        if (nuevo != null) {
            try {
                servicio.registrarPartido(nuevo);
                JOptionPane.showMessageDialog(vista, "Partido creado con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormularioEditar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione un partido de la tabla para editar.");
            return;
        }

        try {
            Partido partidoAEditar = servicio.buscarPorId(idSeleccionado);
            DefaultComboBoxModel<MundialItem> modeloMundiales = construirModeloMundiales();
            DefaultComboBoxModel<SeleccionItem> modeloSelecciones = construirModeloSelecciones();
            Partido partidoModificado = vista.mostrarModalFormulario(partidoAEditar, modeloMundiales, modeloSelecciones);

            if (partidoModificado != null) {
                servicio.actualizarPartido(partidoModificado);
                JOptionPane.showMessageDialog(vista, "Partido actualizado correctamente.");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioEliminar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un partido primero.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas eliminar este partido?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                servicio.eliminarPartido(idSeleccionado);
                JOptionPane.showMessageDialog(vista, "Partido eliminado con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}