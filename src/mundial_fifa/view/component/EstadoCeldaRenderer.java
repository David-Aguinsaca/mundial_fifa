package mundial_fifa.view.component;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class EstadoCeldaRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        // Llamamos al comportamiento base para mantener la selección y fuentes estándar
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Validamos que el valor de la celda sea efectivamente un Booleano
        if (value instanceof Boolean) {
            boolean activo = (Boolean) value;

            if (activo) {
                setText("  Activo  ");
                if (!isSelected) {
                    c.setBackground(new Color(212, 239, 223)); // Verde claro
                    c.setForeground(new Color(21, 101, 41));    // Texto verde oscuro
                }
            } else {
                setText("  Desactivo  ");
                if (!isSelected) {
                    c.setBackground(new Color(242, 215, 213)); // Rojo claro
                    c.setForeground(new Color(148, 32, 23));    // Texto rojo oscuro
                }
            }
            
            // Opcional: Centrar el texto en la celda
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(c.getFont().deriveFont(Font.BOLD)); // Texto en negrita para estilo ERP
        }

        // Si la fila está seleccionada por el usuario, respetamos el color de selección del ERP
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }

        return c;
    }
}