package mundial_fifa.view;

import javax.swing.*;
import mundial_fifa.view.component.BotonSidebar;
import java.awt.*;

public class MainLayout extends JFrame {

    private JPanel panelCentralDinamico;
    private BotonSidebar btnContinentes;
    private BotonSidebar btnConfederacion;
    private BotonSidebar btnSelecciones;
    private BotonSidebar btnPartidos;
    private BotonSidebar btnEstadisticas;
    private BotonSidebar btnMundiales;
    private BotonSidebar btnDashboard;

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
        
        //GridLayout: organiza los componentes dentro de un contenedor en 
        //una cuadrícula rectangular de filas y columnas
        sidebar.setLayout(new GridLayout(8, 1, 0, 5));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblModulo = new JLabel("MUNDIAL FIFA", SwingConstants.CENTER);
        lblModulo.setForeground(Color.WHITE);
        lblModulo.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(lblModulo);

        // Instanciamos el componente personalizado
        btnDashboard = new BotonSidebar("Dashboard");
        btnContinentes = new BotonSidebar("Continentes");
        btnConfederacion = new BotonSidebar("Confederación");
        btnSelecciones = new BotonSidebar("Selecciones");
        btnPartidos = new BotonSidebar("Partidos");
        btnEstadisticas = new BotonSidebar("Estadísticas");
        btnMundiales = new BotonSidebar("Mundiales");

        sidebar.add(btnDashboard);
        sidebar.add(btnContinentes);
        sidebar.add(btnConfederacion);
        sidebar.add(btnSelecciones);
        sidebar.add(btnPartidos);
        sidebar.add(btnEstadisticas);
        sidebar.add(btnMundiales);


        add(sidebar, BorderLayout.WEST);

        // 2. Panel Central Dinámico (Intercambiable) Divide el contenedor (BorderLayout)
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
    public BotonSidebar getBtnSelecciones() { return btnSelecciones; }
    public BotonSidebar getBtnPartidos() { return btnPartidos; }
    public BotonSidebar getBtnEstadisticas() { return btnEstadisticas; }
    public BotonSidebar getBtnMundiales() { return btnMundiales; }
    public BotonSidebar getBtnDashboard() { return btnDashboard; }
}