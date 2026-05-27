package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.Partido;
import java.awt.*;
import java.time.LocalDate;

public class PartidoFormDialog extends JDialog {

    private JComboBox<?> cmbMundial;
    private JTextField txtFecha;
    private JTextField txtFase;
    private JComboBox<?> cmbSeleccionLocal;
    private JComboBox<?> cmbSeleccionVisitante;
    private JSpinner spnGolesLocal;
    private JSpinner spnGolesVisitante;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;

    private Partido partidoOriginal;

    public PartidoFormDialog(Frame padre, Partido partidoAEditar,
                             DefaultComboBoxModel<?> mundialModelo,
                             DefaultComboBoxModel<?> seleccionLocalModelo,
                             DefaultComboBoxModel<?> seleccionVisitanteModelo) {
        super(padre, (partidoAEditar == null) ? "Crear Nuevo Partido" : "Editar Partido", true);
        this.partidoOriginal = partidoAEditar;

        setSize(500, 350);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Mundial:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        cmbMundial = new JComboBox<>(mundialModelo);
        panelForm.add(cmbMundial, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Fecha (yyyy-MM-dd):"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtFecha = new JTextField();
        panelForm.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Fase:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtFase = new JTextField();
        panelForm.add(txtFase, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Selección Local:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        cmbSeleccionLocal = new JComboBox<>(seleccionLocalModelo);
        panelForm.add(cmbSeleccionLocal, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelForm.add(new JLabel("Selección Visitante:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        cmbSeleccionVisitante = new JComboBox<>(seleccionVisitanteModelo);
        panelForm.add(cmbSeleccionVisitante, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        panelForm.add(new JLabel("Goles Local:"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        spnGolesLocal = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnGolesLocal, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        panelForm.add(new JLabel("Goles Visitante:"), gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        spnGolesVisitante = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnGolesVisitante, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0;
        panelForm.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1; gbc.gridy = 7;
        chkEstado = new JCheckBox("Activo");
        panelForm.add(chkEstado, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((partidoAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (partidoAEditar != null) {
            txtFecha.setText(partidoAEditar.getFecha().toString());
            txtFase.setText(partidoAEditar.getFase());
            spnGolesLocal.setValue(partidoAEditar.getGolesLocal());
            spnGolesVisitante.setValue(partidoAEditar.getGolesVisitante());
            chkEstado.setSelected(partidoAEditar.getEstado());
            for (int i = 0; i < cmbMundial.getItemCount(); i++) {
                Object item = cmbMundial.getItemAt(i);
                if (item instanceof MundialItem mi && mi.id().equals(partidoAEditar.getIdMundial())) {
                    cmbMundial.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbSeleccionLocal.getItemCount(); i++) {
                Object item = cmbSeleccionLocal.getItemAt(i);
                if (item instanceof SeleccionItem si && si.id().equals(partidoAEditar.getIdSeleccionLocal())) {
                    cmbSeleccionLocal.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbSeleccionVisitante.getItemCount(); i++) {
                Object item = cmbSeleccionVisitante.getItemAt(i);
                if (item instanceof SeleccionItem si && si.id().equals(partidoAEditar.getIdSeleccionVisitante())) {
                    cmbSeleccionVisitante.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            chkEstado.setSelected(true);
        }

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            if (cmbMundial.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un mundial.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtFecha.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fecha es obligatoria (yyyy-MM-dd).", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                LocalDate.parse(txtFecha.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtFase.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fase es obligatoria.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cmbSeleccionLocal.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar la selección local.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cmbSeleccionVisitante.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar la selección visitante.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cmbSeleccionLocal.getSelectedItem() instanceof SeleccionItem sl &&
                cmbSeleccionVisitante.getSelectedItem() instanceof SeleccionItem sv &&
                sl.id().equals(sv.id())) {
                JOptionPane.showMessageDialog(this, "La selección local y visitante no pueden ser la misma.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    public Partido getPartidoProcesado() {
        if (!guardado) return null;

        Partido resultado = (partidoOriginal != null) ? partidoOriginal : new Partido();

        Object mundialSelected = cmbMundial.getSelectedItem();
        if (mundialSelected instanceof MundialItem mi) {
            resultado.setIdMundial(mi.id());
        }

        resultado.setFecha(LocalDate.parse(txtFecha.getText().trim()));
        resultado.setFase(txtFase.getText().trim());

        Object localSelected = cmbSeleccionLocal.getSelectedItem();
        if (localSelected instanceof SeleccionItem si) {
            resultado.setIdSeleccionLocal(si.id());
        }

        Object visitanteSelected = cmbSeleccionVisitante.getSelectedItem();
        if (visitanteSelected instanceof SeleccionItem si) {
            resultado.setIdSeleccionVisitante(si.id());
        }

        resultado.setGolesLocal((Integer) spnGolesLocal.getValue());
        resultado.setGolesVisitante((Integer) spnGolesVisitante.getValue());
        resultado.setEstado(chkEstado.isSelected());
        return resultado;
    }

    public record MundialItem(Integer id, Integer anio, String pais) {
        @Override
        public String toString() {
            return anio + " - " + pais;
        }
    }

    public record SeleccionItem(Integer id, String nombre) {
        @Override
        public String toString() {
            return nombre;
        }
    }
}