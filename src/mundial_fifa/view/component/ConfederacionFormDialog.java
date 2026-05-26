package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.Confederacion;
import java.awt.*;

public class ConfederacionFormDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtSiglas;
    private JComboBox<?> cmbContinente;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;

    private Confederacion confederacionOriginal;

    public ConfederacionFormDialog(Frame padre, Confederacion confederacionAEditar, DefaultComboBoxModel<?> continentesModelo) {
        super(padre, (confederacionAEditar == null) ? "Crear Nueva Confederación" : "Editar Confederación", true);
        this.confederacionOriginal = confederacionAEditar;

        setSize(420, 280);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtNombre = new JTextField();
        panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Siglas:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtSiglas = new JTextField();
        panelForm.add(txtSiglas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Continente:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        cmbContinente = new JComboBox<>(continentesModelo);
        panelForm.add(cmbContinente, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        chkEstado = new JCheckBox("Activo");
        panelForm.add(chkEstado, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((confederacionAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (confederacionAEditar != null) {
            txtNombre.setText(confederacionAEditar.getNombre());
            txtSiglas.setText(confederacionAEditar.getSiglas());
            chkEstado.setSelected(confederacionAEditar.getEstado());
            for (int i = 0; i < cmbContinente.getItemCount(); i++) {
                Object item = cmbContinente.getItemAt(i);
                if (item instanceof ContinenteItem ci && ci.id().equals(confederacionAEditar.getIdContinente())) {
                    cmbContinente.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            chkEstado.setSelected(true);
        }

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtSiglas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Las siglas son obligatorias.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cmbContinente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un continente.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    public Confederacion getConfederacionProcesada() {
        if (!guardado) return null;

        Confederacion resultado = (confederacionOriginal != null) ? confederacionOriginal : new Confederacion();
        resultado.setNombre(txtNombre.getText().trim());
        resultado.setSiglas(txtSiglas.getText().trim().toUpperCase());

        Object selected = cmbContinente.getSelectedItem();
        if (selected instanceof ContinenteItem ci) {
            resultado.setIdContinente(ci.id());
        }

        resultado.setEstado(chkEstado.isSelected());
        return resultado;
    }

    public record ContinenteItem(Integer id, String nombre) {
        @Override
        public String toString() {
            return nombre;
        }
    }
}
