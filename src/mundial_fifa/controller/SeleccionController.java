package mundial_fifa.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.Confederacion;
import mundial_fifa.model.entity.Seleccion;
import mundial_fifa.model.service.ConfederacionService;
import mundial_fifa.model.service.SeleccionService;
import mundial_fifa.view.component.SeleccionFormDialog.ConfederacionItem;
import mundial_fifa.view.component.SeleccionPanel;

public class SeleccionController {

    private SeleccionPanel vista;
    private SeleccionService servicio;
    private ConfederacionService confederacionService;

    public SeleccionController(SeleccionPanel vista, SeleccionService servicio, ConfederacionService confederacionService) {
        this.vista = vista;
        this.servicio = servicio;
        this.confederacionService = confederacionService;

        this.vista.escucharBotonRefrescar(e -> cargarTabla());
        this.vista.escucharBotonCrear(e -> abrirFormularioCrear());
        this.vista.escucharBotonEditar(e -> abrirFormularioEditar());
        this.vista.escucharBotonEliminar(e -> abrirFormularioEliminar());

        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Seleccion> lista = servicio.obtenerTodos();

        for (Seleccion s : lista) {
            modelo.addRow(new Object[] {
                    s.getIdSeleccion(),
                    s.getNombre(),
                    s.getConfederacion().getNombre(),
                    s.getEstado(),
                    s.getFechaCreacion(),
                    s.getFechaModificacion()
            });
        }
    }

    private DefaultComboBoxModel<ConfederacionItem> construirModeloConfederaciones() {
        DefaultComboBoxModel<ConfederacionItem> modelo = new DefaultComboBoxModel<>();
        try {
            List<Confederacion> confederaciones = confederacionService.obtenerByEstado();
            for (Confederacion c : confederaciones) {
                modelo.addElement(new ConfederacionItem(c.getIdConfederacion(), c.getNombre(), c.getSiglas()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "No se pudieron cargar las confederaciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    private void abrirFormularioCrear() {
        DefaultComboBoxModel<ConfederacionItem> modeloConfederaciones = construirModeloConfederaciones();
        Seleccion nuevo = vista.mostrarModalFormulario(null, modeloConfederaciones);

        if (nuevo != null) {
            try {
                servicio.registrarSeleccion(nuevo);
                JOptionPane.showMessageDialog(vista, "Selección creada con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirFormularioEditar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una selección de la tabla para editar.");
            return;
        }

        try {
            Seleccion seleccionAEditar = servicio.buscarPorId(idSeleccionado);
            DefaultComboBoxModel<ConfederacionItem> modeloConfederaciones = construirModeloConfederaciones();
            Seleccion seleccionModificada = vista.mostrarModalFormulario(seleccionAEditar, modeloConfederaciones);

            if (seleccionModificada != null) {
                servicio.actualizarSeleccion(seleccionModificada);
                JOptionPane.showMessageDialog(vista, "Selección actualizada correctamente.");
                cargarTabla();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioEliminar() {
        Integer idSeleccionado = vista.getIdSeleccionado();

        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una selección primero.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas eliminar esta selección?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                servicio.eliminarSeleccion(idSeleccionado);
                JOptionPane.showMessageDialog(vista, "Selección eliminada con éxito.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}