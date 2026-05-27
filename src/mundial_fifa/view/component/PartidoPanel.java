package mundial_fifa.view.component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mundial_fifa.model.entity.Partido;
import java.awt.*;
import java.awt.event.ActionListener;

public class PartidoPanel extends JPanel {

    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear;
    private JButton btnRefrescar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public PartidoPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Partidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = { "ID", "Fecha", "Fase", "Local", "Goles L", "Goles V", "Visitante", "Mundial", "Estado", "Creación", "Modificación" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDatos = new JTable(modeloTabla);
        tablaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaDatos.getColumnModel().getColumn(8).setCellRenderer(new EstadoCeldaRenderer());
        tablaDatos.getColumnModel().getColumn(9).setCellRenderer(new FechaCeldaRenderer());
        tablaDatos.getColumnModel().getColumn(10).setCellRenderer(new FechaCeldaRenderer());

        JScrollPane scrollTabla = new JScrollPane(tablaDatos);
        add(scrollTabla, BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        btnCrear = new JButton("➕ Crear Nuevo");
        btnRefrescar = new JButton("🔄 Refrescar");
        btnEditar = new JButton("✏️ Editar Selección");
        btnEliminar = new JButton("❌ Eliminar");

        btnCrear.setBackground(new Color(40, 167, 69));
        btnCrear.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);

        panelAcciones.add(btnCrear);
        panelAcciones.add(btnRefrescar);
        panelAcciones.add(new JSeparator(SwingConstants.VERTICAL));
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);

        add(panelAcciones, BorderLayout.SOUTH);
    }

    public void escucharBotonCrear(ActionListener l) {
        btnCrear.addActionListener(l);
    }

    public void escucharBotonRefrescar(ActionListener l) {
        btnRefrescar.addActionListener(l);
    }

    public void escucharBotonEditar(ActionListener l) {
        btnEditar.addActionListener(l);
    }

    public void escucharBotonEliminar(ActionListener l) {
        btnEliminar.addActionListener(l);
    }

    public Integer getIdSeleccionado() {
        int fila = tablaDatos.getSelectedRow();
        if (fila == -1)
            return null;
        return (Integer) modeloTabla.getValueAt(fila, 0);
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public Partido mostrarModalFormulario(Partido objetivo,
                                          DefaultComboBoxModel<?> mundialModelo,
                                          DefaultComboBoxModel<?> seleccionLocalModelo,
                                          DefaultComboBoxModel<?> seleccionVisitanteModelo) {
        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        PartidoFormDialog modal = new PartidoFormDialog((Frame) ventanaPadre, objetivo, mundialModelo, seleccionLocalModelo, seleccionVisitanteModelo);
        modal.setVisible(true);
        return modal.getPartidoProcesado();
    }
}