package mundial_fifa.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.Mundial;
import mundial_fifa.model.service.MundialService;
import mundial_fifa.view.component.MundialPanel;

public class MundialController {

	private MundialPanel vista;
	private MundialService servicio;

	public MundialController(MundialPanel vista, MundialService servicio) {
		this.vista = vista;
		this.servicio = servicio;

		this.vista.escucharBotonRefrescar(e -> cargarTabla());
		this.vista.escucharBotonCrear(e -> abrirFormularioCrear());
		this.vista.escucharBotonEditar(e -> abrirFormularioEditar());
		this.vista.escucharBotonEliminar(e -> abrirFormularioEliminar());

		cargarTabla();
	}

	private void cargarTabla() {
		DefaultTableModel modelo = vista.getModeloTabla();
		modelo.setRowCount(0);

		List<Mundial> lista = servicio.obtenerTodos();

		for (Mundial m : lista) {
			modelo.addRow(new Object[] {
					m.getIdMundial(),
					m.getAnio(),
					m.getPaisAnfitrion()
			});
		}
	}

	private void abrirFormularioCrear() {
		Mundial nuevo = vista.mostrarModalFormulario(null);

		if (nuevo != null) {
			try {
				servicio.registrarMundial(nuevo);
				JOptionPane.showMessageDialog(vista, "Mundial creado con éxito.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void abrirFormularioEditar() {
		Integer idSeleccionado = vista.getIdSeleccionado();

		if (idSeleccionado == null) {
			JOptionPane.showMessageDialog(vista, "Por favor, seleccione un mundial de la tabla para editar.");
			return;
		}

		try {
			Mundial mundialAEditar = servicio.buscarPorId(idSeleccionado);
			Mundial mundialModificado = vista.mostrarModalFormulario(mundialAEditar);

			if (mundialModificado != null) {
				servicio.actualizarMundial(mundialModificado);
				JOptionPane.showMessageDialog(vista, "Mundial actualizado correctamente.");
				cargarTabla();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void abrirFormularioEliminar() {
		Integer idSeleccionado = vista.getIdSeleccionado();

		if (idSeleccionado == null) {
			JOptionPane.showMessageDialog(vista, "Seleccione un mundial primero.");
			return;
		}

		int respuesta = JOptionPane.showConfirmDialog(
				vista,
				"¿Estás seguro de que deseas eliminar este mundial?",
				"Confirmar Eliminación",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				servicio.eliminarMundial(idSeleccionado);
				JOptionPane.showMessageDialog(vista, "Mundial eliminado con éxito.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
