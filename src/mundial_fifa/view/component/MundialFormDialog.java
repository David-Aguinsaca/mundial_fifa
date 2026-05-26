package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.Mundial;
import java.awt.*;

public class MundialFormDialog extends JDialog {

    private JSpinner spnAnio;
    private JTextField txtPaisAnfitrion;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;

    private Mundial mundialOriginal;

    public MundialFormDialog(Frame padre, Mundial mundialAEditar) {
        super(padre, (mundialAEditar == null) ? "Crear Nuevo Mundial" : "Editar Mundial", true);
        this.mundialOriginal = mundialAEditar;

        setSize(400, 200);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Año:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        spnAnio = new JSpinner(new SpinnerNumberModel(2026, 1930, 2100, 1));
        panelForm.add(spnAnio, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("País Anfitrión:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtPaisAnfitrion = new JTextField();
        panelForm.add(txtPaisAnfitrion, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((mundialAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (mundialAEditar != null) {
            spnAnio.setValue(mundialAEditar.getAnio());
            txtPaisAnfitrion.setText(mundialAEditar.getPaisAnfitrion());
        }

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            if (txtPaisAnfitrion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El país anfitrión es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    public Mundial getMundialProcesado() {
        if (!guardado) return null;

        Mundial resultado = (mundialOriginal != null) ? mundialOriginal : new Mundial();
        resultado.setAnio((Integer) spnAnio.getValue());
        resultado.setPaisAnfitrion(txtPaisAnfitrion.getText().trim());

        return resultado;
    }
}
