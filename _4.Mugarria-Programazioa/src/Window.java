import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Window extends JFrame {

    private JLabel imageLabel;
    private JComboBox<String> imageComboBox;
    private JCheckBox commentCheckBox;
    private JTextField commentTextField;

    private static final String IMAGE_FOLDER = "C:\\IMAGE_FOLDER\\"; // Replace with your folder path
    private static final String PASSWORD = "damocles";

    public Window() {
        super("My Image Viewer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String input = JOptionPane.showInputDialog(this, "Input password");
        if (input == null || !input.equals(PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Wrong password.");
            System.exit(0);
        }


        imageComboBox = new JComboBox<>(getImageNames());
        imageComboBox.addActionListener(new ComboListener());
        loadCombo();


        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(IMAGE_FOLDER + (String) imageComboBox.getSelectedItem()));
        add(imageLabel, BorderLayout.CENTER);


        commentCheckBox = new JCheckBox("Add comment");
        commentCheckBox.setSelected(true);
        commentTextField = new JTextField(20);
        JPanel commentPanel = new JPanel();
        commentPanel.add(commentCheckBox);
        commentPanel.add(commentTextField);
        add(commentPanel, BorderLayout.SOUTH);


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imageName = (String) imageComboBox.getSelectedItem();
                String comment = commentCheckBox.isSelected() ? commentTextField.getText() : "";
                saveComment(imageName, comment);
            }
        });
        add(saveButton, BorderLayout.NORTH);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(Window.this, " ");
            }
        });

        setVisible(true);
    }

    private String[] getImageNames() {
        File folder = new File(IMAGE_FOLDER);
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith("flower.jpg");
            }
        });
        if (files == null) {
            System.err.println("Failed to list files in " + IMAGE_FOLDER);
            System.exit(1);
        }
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    private void loadCombo() {
        for (String name : getImageNames()) {
            imageComboBox.addItem(name);
        }
    }

    private void saveComment(String imageName, String comment) {
        String filePath = IMAGE_FOLDER + imageName + ".txt";
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(comment + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ComboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String imageName = (String) imageComboBox.getSelectedItem();
            imageLabel.setIcon(new ImageIcon(IMAGE_FOLDER + imageName));
        }
    }

    public static void main(String[] args) {
        new Window();
    }
}
