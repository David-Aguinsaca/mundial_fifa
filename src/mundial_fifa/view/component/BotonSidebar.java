package mundial_fifa.view.component;

import javax.swing.*;
import java.awt.*;

public class BotonSidebar extends JButton {

    public BotonSidebar(String texto) {
        super(texto);
        // Estilo ERP moderno e industrial
        setForeground(Color.LIGHT_GRAY);
        setBackground(new Color(52, 66, 80));
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setHorizontalAlignment(SwingConstants.LEFT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Efecto hover básico al pasar el ratón (opcional)
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(66, 84, 102));
                setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(52, 66, 80));
                textEfectoActivo();
            }
        });
    }

    private void textEfectoActivo() {
        if (!isSelected()) {
            setForeground(Color.LIGHT_GRAY);
        }
    }
}