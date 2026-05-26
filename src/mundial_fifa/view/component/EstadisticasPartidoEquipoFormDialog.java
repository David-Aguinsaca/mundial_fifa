package mundial_fifa.view.component;

import javax.swing.*;
import mundial_fifa.model.entity.EstadisticasPartidoEquipo;
import java.awt.*;
import java.math.BigDecimal;

public class EstadisticasPartidoEquipoFormDialog extends JDialog {

    private JComboBox<?> cmbPartido;
    private JComboBox<?> cmbSeleccion;
    private JSpinner spnPosesion;
    private JSpinner spnTirosArco;
    private JSpinner spnTirosEsquina;
    private JSpinner spnTirosLibres;
    private JSpinner spnFaltas;
    private JSpinner spnPrecisionPases;
    private JSpinner spnFueraJuego;
    private JSpinner spnSalvadas;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;

    private EstadisticasPartidoEquipo estadisticaOriginal;

    public EstadisticasPartidoEquipoFormDialog(Frame padre, EstadisticasPartidoEquipo estadisticaAEditar,
                                                DefaultComboBoxModel<?> partidoModelo,
                                                DefaultComboBoxModel<?> seleccionModelo) {
        super(padre, (estadisticaAEditar == null) ? "Crear Estadística" : "Editar Estadística", true);
        this.estadisticaOriginal = estadisticaAEditar;

        setSize(520, 460);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Partido:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        cmbPartido = new JComboBox<>(partidoModelo);
        panelForm.add(cmbPartido, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Selección:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbSeleccion = new JComboBox<>(seleccionModelo);
        panelForm.add(cmbSeleccion, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Posesión %:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        spnPosesion = new JSpinner(new SpinnerNumberModel(50.00, 0.00, 100.00, 0.5));
        JSpinner.NumberEditor editorPosesion = (JSpinner.NumberEditor) spnPosesion.getEditor();
        editorPosesion.getFormat().setMinimumFractionDigits(2);
        panelForm.add(spnPosesion, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Tiros al Arco:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        spnTirosArco = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnTirosArco, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelForm.add(new JLabel("Tiros Esquina:"), gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        spnTirosEsquina = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnTirosEsquina, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        panelForm.add(new JLabel("Tiros Libres:"), gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0;
        spnTirosLibres = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnTirosLibres, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        panelForm.add(new JLabel("Faltas:"), gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        spnFaltas = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnFaltas, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0;
        panelForm.add(new JLabel("Precisión Pases %:"), gbc);

        gbc.gridx = 1; gbc.gridy = 7; gbc.weightx = 1.0;
        spnPrecisionPases = new JSpinner(new SpinnerNumberModel(80.00, 0.00, 100.00, 0.5));
        JSpinner.NumberEditor editorPrecision = (JSpinner.NumberEditor) spnPrecisionPases.getEditor();
        editorPrecision.getFormat().setMinimumFractionDigits(2);
        panelForm.add(spnPrecisionPases, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.weightx = 0;
        panelForm.add(new JLabel("Fuera de Juego:"), gbc);

        gbc.gridx = 1; gbc.gridy = 8; gbc.weightx = 1.0;
        spnFueraJuego = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnFueraJuego, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.weightx = 0;
        panelForm.add(new JLabel("Salvadas Portero:"), gbc);

        gbc.gridx = 1; gbc.gridy = 9; gbc.weightx = 1.0;
        spnSalvadas = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        panelForm.add(spnSalvadas, gbc);

        gbc.gridx = 0; gbc.gridy = 10; gbc.weightx = 0;
        panelForm.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1; gbc.gridy = 10;
        chkEstado = new JCheckBox("Activo");
        panelForm.add(chkEstado, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton((estadisticaAEditar == null) ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (estadisticaAEditar != null) {
            spnPosesion.setValue(estadisticaAEditar.getPosesionPorcentaje().doubleValue());
            spnTirosArco.setValue(estadisticaAEditar.getTirosAlArco());
            spnTirosEsquina.setValue(estadisticaAEditar.getTirosEsquina());
            spnTirosLibres.setValue(estadisticaAEditar.getTirosLibres());
            spnFaltas.setValue(estadisticaAEditar.getFaltas());
            spnPrecisionPases.setValue(estadisticaAEditar.getPrecisionPasesPorcentaje().doubleValue());
            spnFueraJuego.setValue(estadisticaAEditar.getFueraDeJuego());
            spnSalvadas.setValue(estadisticaAEditar.getSalvadasPortero());
            chkEstado.setSelected(estadisticaAEditar.getEstado());
            for (int i = 0; i < cmbPartido.getItemCount(); i++) {
                Object item = cmbPartido.getItemAt(i);
                if (item instanceof PartidoItem pi && pi.id().equals(estadisticaAEditar.getIdPartido())) {
                    cmbPartido.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbSeleccion.getItemCount(); i++) {
                Object item = cmbSeleccion.getItemAt(i);
                if (item instanceof SeleccionItem si && si.id().equals(estadisticaAEditar.getIdSeleccion())) {
                    cmbSeleccion.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            chkEstado.setSelected(true);
        }

        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            if (cmbPartido.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un partido.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cmbSeleccion.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una selección.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            guardado = true;
            setVisible(false);
        });
    }

    public EstadisticasPartidoEquipo getEstadisticaProcesada() {
        if (!guardado) return null;

        EstadisticasPartidoEquipo resultado = (estadisticaOriginal != null) ? estadisticaOriginal : new EstadisticasPartidoEquipo();

        Object partidoSelected = cmbPartido.getSelectedItem();
        if (partidoSelected instanceof PartidoItem pi) {
            resultado.setIdPartido(pi.id());
        }

        Object seleccionSelected = cmbSeleccion.getSelectedItem();
        if (seleccionSelected instanceof SeleccionItem si) {
            resultado.setIdSeleccion(si.id());
        }

        resultado.setPosesionPorcentaje(BigDecimal.valueOf((Double) spnPosesion.getValue()));
        resultado.setTirosAlArco((Integer) spnTirosArco.getValue());
        resultado.setTirosEsquina((Integer) spnTirosEsquina.getValue());
        resultado.setTirosLibres((Integer) spnTirosLibres.getValue());
        resultado.setFaltas((Integer) spnFaltas.getValue());
        resultado.setPrecisionPasesPorcentaje(BigDecimal.valueOf((Double) spnPrecisionPases.getValue()));
        resultado.setFueraDeJuego((Integer) spnFueraJuego.getValue());
        resultado.setSalvadasPortero((Integer) spnSalvadas.getValue());
        resultado.setEstado(chkEstado.isSelected());
        return resultado;
    }

    public record PartidoItem(Integer id, String fecha, String fase) {
        @Override
        public String toString() {
            return fecha + " - " + fase;
        }
    }

    public record SeleccionItem(Integer id, String nombre) {
        @Override
        public String toString() {
            return nombre;
        }
    }
}