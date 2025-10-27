package clinica.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CitasFrame extends JFrame {
    public final JTable table = new JTable();
    public final JButton btnNueva = new JButton("Nueva");
    public final JButton btnEditar = new JButton("Editar");
    public final JButton btnCancelar = new JButton("Cancelar");
    public final JButton btnRefrescar = new JButton("Refrescar");

    public CitasFrame() {
        super("Gestión de Citas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table.setModel(new DefaultTableModel(new Object[]{"ID","Doctor ID","Paciente ID","Fecha/Hora","Motivo","Estado"},0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnNueva);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnRefrescar);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        setSize(800, 450);
        setLocationRelativeTo(null);
    }
}
