package mundial_fifa.view.component;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaCeldaRenderer extends DefaultTableCellRenderer {

    // Definimos el formato deseado (HH en mayúsculas es formato 24 horas)
    private final SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        // Heredamos el comportamiento y estilos base de la celda
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Si el valor es de tipo Timestamp o Date, lo formateamos
        if (value instanceof Date) { 
            // java.sql.Timestamp hereda de java.util.Date, por lo que este instanceof atrapa ambos
            String fechaFormateada = formateador.format((Date) value);
            setText(fechaFormateada);
        } else if (value == null) {
            setText(""); // Evitamos que pinte un "null" de texto si el campo está vacío
        }

        // Opcional: Alinear la fecha a la derecha o al centro para estética de ERP
        setHorizontalAlignment(SwingConstants.CENTER);

        return c;
    }
}