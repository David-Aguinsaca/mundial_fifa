package mundial_fifa.view.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private JComboBox<MundialItem> comboMundiales;
    private JTable tablaPartidos;
    private DefaultTableModel modeloTabla;
    private JPanel panelGraficos;
    private ChartPanel chartOfensiva;
    private ChartPanel chartDisciplina;
    private ChartPanel chartControl;

    public DashboardPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== NORTE: Selector de Mundial =====
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.add(new JLabel("Seleccionar Mundial:"));
        comboMundiales = new JComboBox<>();
        comboMundiales.setPreferredSize(new Dimension(250, 25));
        panelNorte.add(comboMundiales);
        add(panelNorte, BorderLayout.NORTH);

        // ===== OESTE: Tabla de Partidos =====
        JPanel panelOeste = new JPanel(new BorderLayout());
        panelOeste.setPreferredSize(new Dimension(380, 0));
        panelOeste.setBorder(BorderFactory.createTitledBorder("Partidos del Mundial"));

        String[] columnas = {"ID", "Fecha", "Fase", "Local", "GL", "GV", "Visitante"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPartidos = new JTable(modeloTabla);
        tablaPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPartidos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaPartidos.getColumnModel().getColumn(1).setPreferredWidth(80);
        tablaPartidos.getColumnModel().getColumn(2).setPreferredWidth(90);
        tablaPartidos.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaPartidos.getColumnModel().getColumn(4).setPreferredWidth(30);
        tablaPartidos.getColumnModel().getColumn(5).setPreferredWidth(30);
        tablaPartidos.getColumnModel().getColumn(6).setPreferredWidth(90);

        JScrollPane scrollTabla = new JScrollPane(tablaPartidos);
        panelOeste.add(scrollTabla, BorderLayout.CENTER);
        add(panelOeste, BorderLayout.WEST);

        // ===== CENTRO: Panel de Gráficos =====
        panelGraficos = new JPanel(new GridLayout(3, 1, 0, 10));
        panelGraficos.setBorder(BorderFactory.createTitledBorder("Estadísticas Comparativas"));

        chartOfensiva = crearChartPanelVacío("Ofensiva");
        chartDisciplina = crearChartPanelVacío("Disciplina y Juego");
        chartControl = crearChartPanelVacío("Control y Eficiencia");

        panelGraficos.add(chartOfensiva);
        panelGraficos.add(chartDisciplina);
        panelGraficos.add(chartControl);

        add(panelGraficos, BorderLayout.CENTER);
    }

    private ChartPanel crearChartPanelVacío(String titulo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                titulo,
                "Métrica",
                "Valor",
                dataset
        );
        personalizarChart(chart);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMinimumDrawHeight(150);
        panel.setMinimumDrawWidth(300);
        return panel;
    }

    private void personalizarChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultShapesFilled(true);
        plot.setRenderer(renderer);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
    }

    // ===== Getters para el Controller =====
    public JComboBox<MundialItem> getComboMundiales() {
        return comboMundiales;
    }

    public JTable getTablaPartidos() {
        return tablaPartidos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public ChartPanel getChartOfensiva() {
        return chartOfensiva;
    }

    public ChartPanel getChartDisciplina() {
        return chartDisciplina;
    }

    public ChartPanel getChartControl() {
        return chartControl;
    }

    // ===== Métodos para actualizar gráficos =====
    public void actualizarGraficoOfensiva(String local, String visitante,
                                           int tirosArcoL, int tirosArcoV,
                                           int tirosEsquinaL, int tirosEsquinaV,
                                           int tirosLibresL, int tirosLibresV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(tirosArcoL, local, "Tiros al Arco");
        dataset.addValue(tirosArcoV, visitante, "Tiros al Arco");
        dataset.addValue(tirosEsquinaL, local, "Tiros de Esquina");
        dataset.addValue(tirosEsquinaV, visitante, "Tiros de Esquina");
        dataset.addValue(tirosLibresL, local, "Tiros Libres");
        dataset.addValue(tirosLibresV, visitante, "Tiros Libres");

        JFreeChart chart = ChartFactory.createLineChart(
                "Ofensiva: " + local + " vs " + visitante,
                "Métrica",
                "Cantidad",
                dataset
        );
        personalizarChart(chart);
        chartOfensiva.setChart(chart);
    }

    public void actualizarGraficoDisciplina(String local, String visitante,
                                             int faltasL, int faltasV,
                                             int fueraJuegoL, int fueraJuegoV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(faltasL, local, "Faltas");
        dataset.addValue(faltasV, visitante, "Faltas");
        dataset.addValue(fueraJuegoL, local, "Fuera de Juego");
        dataset.addValue(fueraJuegoV, visitante, "Fuera de Juego");

        JFreeChart chart = ChartFactory.createLineChart(
                "Disciplina y Juego: " + local + " vs " + visitante,
                "Métrica",
                "Cantidad",
                dataset
        );
        personalizarChart(chart);
        chartDisciplina.setChart(chart);
    }

    public void actualizarGraficoControl(String local, String visitante,
                                          double posesionL, double posesionV,
                                          double precisionL, double precisionV,
                                          int salvadasL, int salvadasV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(posesionL, local, "Posesión (%)");
        dataset.addValue(posesionV, visitante, "Posesión (%)");
        dataset.addValue(precisionL, local, "Precisión Pases (%)");
        dataset.addValue(precisionV, visitante, "Precisión Pases (%)");
        dataset.addValue(salvadasL, local, "Salvadas Portero");
        dataset.addValue(salvadasV, visitante, "Salvadas Portero");

        JFreeChart chart = ChartFactory.createLineChart(
                "Control y Eficiencia: " + local + " vs " + visitante,
                "Métrica",
                "Valor",
                dataset
        );
        personalizarChart(chart);
        chartControl.setChart(chart);
    }

    // ===== Clase interna para el ComboBox =====
    public static class MundialItem {
        private Integer idMundial;
        private Integer anio;
        private String paisAnfitrion;

        public MundialItem(Integer idMundial, Integer anio, String paisAnfitrion) {
            this.idMundial = idMundial;
            this.anio = anio;
            this.paisAnfitrion = paisAnfitrion;
        }

        public Integer getIdMundial() {
            return idMundial;
        }

        public Integer getAnio() {
            return anio;
        }

        public String getPaisAnfitrion() {
            return paisAnfitrion;
        }

        @Override
        public String toString() {
            return anio + " - " + paisAnfitrion;
        }
    }
}
