import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

    private JComboBox<String> comboBox;
    private JTextArea textArea;

    public GUI() {

        this.setTitle("ProgramGUI");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] options = {"python.txt", "c.txt", "java.txt"};
        comboBox = new JComboBox<String>(options);
        comboBox.addActionListener(this);
        leftPanel.add(comboBox);

        JButton deleteButton = new JButton("Borrar");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        leftPanel.add(deleteButton);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        rightPanel.add(scrollPane);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        rightPanel.add(closeButton);


        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));
        contentPane.add(leftPanel);
        contentPane.add(rightPanel);


        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox) e.getSource();
        String fileName = (String) comboBox.getSelectedItem();
        try {
            textArea.setText(readFile(fileName));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo " + fileName);
        }
    }

    private String readFile(String fileName) throws Exception {

        return "Este es el contenido del archivo " + fileName;
    }

    public static void main(String[] args) {
        new GUI();
    }
}







