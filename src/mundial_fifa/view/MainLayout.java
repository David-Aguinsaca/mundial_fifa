package mundial_fifa.view;

import javax.swing.*;
import mundial_fifa.view.component.BotonSidebar;
import java.awt.*;

public class MainLayout extends JFrame {

    private JPanel panelCentralDinamico;
    private BotonSidebar btnContinentes;
    private BotonSidebar btnConfederacion;
    private BotonSidebar btnMundiales;

    public MainLayout() {
        setTitle("ERP - Sistema de Gestión Mundial FIFA");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Construir Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(43, 54, 66));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new GridLayout(7, 1, 0, 5));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblModulo = new JLabel("MUNDIAL FIFA", SwingConstants.CENTER);
        lblModulo.setForeground(Color.WHITE);
        lblModulo.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(lblModulo);

        // Instanciamos el componente personalizado
        btnContinentes = new BotonSidebar("Continentes");
        btnConfederacion = new BotonSidebar("Confederación");
        btnMundiales = new BotonSidebar("Mundiales");

        sidebar.add(btnContinentes);
        sidebar.add(btnConfederacion);
        sidebar.add(btnMundiales);


        add(sidebar, BorderLayout.WEST);

        // 2. Panel Central Dinámico (Intercambiable)
        panelCentralDinamico = new JPanel(new BorderLayout());
        add(panelCentralDinamico, BorderLayout.CENTER);
    }

    /**
     * Permite intercambiar el panel del centro (Módulo) de forma dinámica.
     */
    public void setModuloPanel(JPanel nuevoPanel) {
        panelCentralDinamico.removeAll();               // Quita el panel anterior
        panelCentralDinamico.add(nuevoPanel, BorderLayout.CENTER); // Inserta el nuevo
        panelCentralDinamico.revalidate();              // Re-calcula el layout
        panelCentralDinamico.repaint();                 // Vuelve a pintar la pantalla
    }

    // Getters para que el controlador pueda escuchar los clics del menú lateral si lo requieres
    public BotonSidebar getBtnContinentes() { return btnContinentes; }
    public BotonSidebar getBtnConfederacion() { return btnConfederacion; }
    public BotonSidebar getBtnMundiales() { return btnMundiales; }
}