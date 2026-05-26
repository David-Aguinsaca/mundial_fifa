package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.Seleccion;
import java.awt.*;

public class SeleccionFormDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<?> cmbConfederacion;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;

    private Seleccion seleccionOriginal;

    public SeleccionFormDialog(Frame padre, Seleccion seleccionAEditar, DefaultComboBoxModel<?> confederacionesModelo) {
        super(padre, (seleccionAEditar == null) ? "Crear Nueva Selección" : "Editar Selección", true);
        this.seleccionOriginal = seleccionAEditar;

        setSize(420, 240);
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
        panelForm.add(new JLabel("Confederación:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbConfederacion = new JComboBox<>(confederacionesModelo);
        panelForm.add(cmbConfederacion, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        chkEstado = new JCheckBox("Activo");
        panelForm.add(chkEstado, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((seleccionAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (seleccionAEditar != null) {
            txtNombre.setText(seleccionAEditar.getNombre());
            chkEstado.setSelected(seleccionAEditar.getEstado());
            for (int i = 0; i < cmbConfederacion.getItemCount(); i++) {
                Object item = cmbConfederacion.getItemAt(i);
                if (item instanceof ConfederacionItem ci && ci.id().equals(seleccionAEditar.getIdConfederacion())) {
                    cmbConfederacion.setSelectedIndex(i);
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
            if (cmbConfederacion.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una confederación.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    public Seleccion getSeleccionProcesada() {
        if (!guardado) return null;

        Seleccion resultado = (seleccionOriginal != null) ? seleccionOriginal : new Seleccion();
        resultado.setNombre(txtNombre.getText().trim());

        Object selected = cmbConfederacion.getSelectedItem();
        if (selected instanceof ConfederacionItem ci) {
            resultado.setIdConfederacion(ci.id());
        }

        resultado.setEstado(chkEstado.isSelected());
        return resultado;
    }

    public record ConfederacionItem(Integer id, String nombre, String siglas) {
        @Override
        public String toString() {
            return nombre + " (" + siglas + ")";
        }
    }
}