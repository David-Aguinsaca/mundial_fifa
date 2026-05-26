package mundial_fifa.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import mundial_fifa.model.entity.Continente;
import mundial_fifa.model.service.ContinenteService;
import mundial_fifa.view.component.ContinentePanel;

public class ContinenteController {

	private ContinentePanel vista;
	private ContinenteService servicio;

	public ContinenteController(ContinentePanel vista, ContinenteService servicio) {
		this.vista = vista;
		this.servicio = servicio;

		// Vinculamos los eventos de la vista con la lógica de este controlador
		this.vista.escucharBotonRefrescar(e -> cargarTabla());
		this.vista.escucharBotonCrear(e -> abrirFormularioCrear());
		this.vista.escucharBotonEditar(e -> abrirFormularioEditar());
		this.vista.escucharBotonEliminar(e -> abrirFormularioEliminar());

		// Carga inicial al abrir el sistema
		cargarTabla();
	}

	private void cargarTabla() {
		DefaultTableModel modelo = vista.getModeloTabla();
		modelo.setRowCount(0); // Limpiar filas viejas

		// Llamamos al servicio (Lógica de negocio -> Repository -> PostgreSQL)
		List<Continente> lista = servicio.obtenerTodos();

		for (Continente c : lista) {
			modelo.addRow(new Object[] {
					c.getIdContinente(),
					c.getNombre(),
					c.getEstado(),
					c.getFechaCreacion(),
					c.getFechaModificacion()
			});
		}
	}

	// CREAR NUEVO
	private void abrirFormularioCrear() {
		// Mandamos NULL porque queremos el formulario en blanco
		Continente nuevo = vista.mostrarModalFormulario(null);

		if (nuevo != null) {
			try {
				servicio.registrarContinente(nuevo); // Llama al INSERT del service
				JOptionPane.showMessageDialog(vista, "Continente creado con éxito.");
				cargarTabla();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// EDITAR SELECCIONADO
	private void abrirFormularioEditar() {
		Integer idSeleccionado = vista.getIdSeleccionado();

		if (idSeleccionado == null) {
			JOptionPane.showMessageDialog(vista, "Por favor, seleccione un continente de la tabla para editar.");
			return;
		}

		try {
			// 1. Buscamos los datos completos y frescos de la BD para cargarlos en el modal
			Continente continenteAEditar = servicio.buscarPorId(idSeleccionado);

			// 2. Abrimos el mismo modal pasándole el objeto lleno
			Continente continenteModificado = vista.mostrarModalFormulario(continenteAEditar);

			// 3. Si el usuario guardó los cambios, llamamos a la actualización
			if (continenteModificado != null) {
				servicio.actualizarContinente(continenteModificado); // Llama al UPDATE del service
				JOptionPane.showMessageDialog(vista, "Continente actualizado correctamente.");
				cargarTabla(); // Refresca la cuadrícula
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void abrirFormularioEliminar() {
    Integer idSeleccionado = vista.getIdSeleccionado();
    
    if (idSeleccionado == null) {
        JOptionPane.showMessageDialog(vista, "Seleccione un continente primero.");
        return;
    }

    // 1. Guardamos la respuesta en la variable
    int respuesta = JOptionPane.showConfirmDialog(
            vista, 
            "¿Estás seguro de que deseas eliminar este continente?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
    );

    // 2. ¡CRÍTICO! Todo lo relacionado con borrar DEBE estar DENTRO de las llaves del IF
    if (respuesta == JOptionPane.YES_OPTION) { 
        try {
            // Esto SOLO se ejecuta si presionó "SÍ"
            servicio.eliminarContinente(idSeleccionado);
            JOptionPane.showMessageDialog(vista, "Continente eliminado con éxito.");
            cargarTabla(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    // Si presiona NO o cierra, el código salta directamente aquí y no hace nada.
}
}