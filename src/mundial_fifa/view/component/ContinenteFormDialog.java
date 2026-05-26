package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.Continente;
import java.awt.*;


public class ContinenteFormDialog extends JDialog {

    private JTextField txtNombre;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;
    
    // Guardamos una referencia al objeto original si estamos editando
    private Continente continenteOriginal;

    // El constructor ahora acepta un tercer parámetro: 'continenteAEditar'
    public ContinenteFormDialog(Frame padre, Continente continenteAEditar) {
        // Título dinámico según si viene un objeto o null
        super(padre, (continenteAEditar == null) ? "Crear Nuevo Continente" : "Editar Continente", true);
        this.continenteOriginal = continenteAEditar;
        
        setSize(380, 220);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(new BorderLayout());

        // --- DISEÑO DEL FORMULARIO ---
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
        panelForm.add(new JLabel("Estado:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        chkEstado = new JCheckBox("Activo");
        panelForm.add(chkEstado, gbc);

        add(panelForm, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((continenteAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Si el objeto NO es null, cargamos sus datos actuales en la pantalla
        if (continenteAEditar != null) {
            txtNombre.setText(continenteAEditar.getNombre());
            chkEstado.setSelected(continenteAEditar.getEstado());
        } else {
            chkEstado.setSelected(true); // Default para nuevos registros
        }

        // --- EVENTOS ---
        btnCancelar.addActionListener(e -> dispose());
        
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    /**
     * Retorna el objeto procesado. Sirve tanto para INSERT como para UPDATE.
     */
    public Continente getContinenteProcesado() {
        if (!guardado) return null;
        
        // Si estábamos editando, reutilizamos el mismo objeto para conservar su ID
        Continente resultado = (continenteOriginal != null) ? continenteOriginal : new Continente();
        
        resultado.setNombre(txtNombre.getText().trim());
        resultado.setEstado(chkEstado.isSelected());
        
        return resultado;
    }
}