package mundial_fifa.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.Confederacion;
import mundial_fifa.model.entity.Continente;
import mundial_fifa.model.service.ConfederacionService;
import mundial_fifa.model.service.ContinenteService;
import mundial_fifa.view.component.ConfederacionFormDialog.ContinenteItem;
import mundial_fifa.view.component.ConfederacionPanel;

public class ConfederacionController {

	private ConfederacionPanel vista;
	private ConfederacionService servicio;
	private ContinenteService continenteService;

	public ConfederacionController(ConfederacionPanel vista, ConfederacionService servicio, ContinenteService continenteService) {
		this.vista = vista;
		this.servicio = servicio;
		this.continenteService = continenteService;

		this.vista.escucharBotonRefrescar(e -> cargarTabla());
		this.vista.escucharBotonCrear(e -> abrirFormularioCrear());
		this.vista.escucharBotonEditar(e -> abrirFormularioEditar());
		this.vista.escucharBotonEliminar(e -> abrirFormularioEliminar());

		cargarTabla();
	}

	private void cargarTabla() {
		DefaultTableModel modelo = vista.getModeloTabla();
		modelo.setRowCount(0);

		List<Confederacion> lista = servicio.obtenerTodos();

		for (Confederacion c : lista) {
			modelo.addRow(new Object[] {
					c.getIdConfederacion(),
					c.getNombre(),
					c.getSiglas(),
					//c.getIdContinente(),
          c.getContinente().getNombre(),
					c.getEstado(),
					c.getFechaCreacion(),
					c.getFechaModificacion()
			});
		}
	}

	private DefaultComboBoxModel<ContinenteItem> construirModeloContinentes() {
		DefaultComboBoxModel<ContinenteItem> modelo = new DefaultComboBoxModel<>();
		try {
			List<Continente> continentes = continenteService.obtenerTodos();
			for (Continente c : continentes) {
				modelo.addElement(new ContinenteItem(c.getIdContinente(), c.getNombre()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(vista, "No se pudieron cargar los continentes.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}

	private void abrirFormularioCrear() {
		DefaultComboBoxModel<ContinenteItem> modeloContinentes = construirModeloContinentes();
		Confederacion nuevo = vista.mostrarModalFormulario(null, modeloContinentes);

		if (nuevo != null) {
			try {
				servicio.registrarConfederacion(nuevo);
				JOptionPane.showMessageDialog(vista, "Confederación creada con éxito.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void abrirFormularioEditar() {
		Integer idSeleccionado = vista.getIdSeleccionado();

		if (idSeleccionado == null) {
			JOptionPane.showMessageDialog(vista, "Por favor, seleccione una confederación de la tabla para editar.");
			return;
		}

		try {
			Confederacion confederacionAEditar = servicio.buscarPorId(idSeleccionado);
			DefaultComboBoxModel<ContinenteItem> modeloContinentes = construirModeloContinentes();
			Confederacion confederacionModificada = vista.mostrarModalFormulario(confederacionAEditar, modeloContinentes);

			if (confederacionModificada != null) {
				servicio.actualizarConfederacion(confederacionModificada);
				JOptionPane.showMessageDialog(vista, "Confederación actualizada correctamente.");
				cargarTabla();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void abrirFormularioEliminar() {
		Integer idSeleccionado = vista.getIdSeleccionado();

		if (idSeleccionado == null) {
			JOptionPane.showMessageDialog(vista, "Seleccione una confederación primero.");
			return;
		}

		int respuesta = JOptionPane.showConfirmDialog(
				vista,
				"¿Estás seguro de que deseas eliminar esta confederación?",
				"Confirmar Eliminación",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				servicio.eliminarConfederacion(idSeleccionado);
				JOptionPane.showMessageDialog(vista, "Confederación eliminada con éxito.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
