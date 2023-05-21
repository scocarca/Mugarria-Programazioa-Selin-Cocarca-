import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PictureViewer {
    private List<Photographer> photographers;
    private List<Picture> pictures;
    private JComboBox<Photographer> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<Picture> pictureList;
    private JLabel pictureLabel;
    private MySQLConnectionExample connectionExample;

    public PictureViewer() throws SQLException {
        connectionExample = new MySQLConnectionExample();

        photographers = loadPhotographers();
        pictures = loadPictures();

        JFrame frame = new JFrame("Picture Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JLabel photographerLabel = new JLabel("Fotografo:");
        photographerComboBox = new JComboBox<>(photographers.toArray(new Photographer[0]));
        JLabel dateLabel = new JLabel("Photos after:");
        datePicker = new JXDatePicker(new Date());
        pictureList = new JList<>(new DefaultListModel<>());
        pictureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pictureList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Picture picture = pictureList.getSelectedValue();
                if (picture != null) {
                    pictureLabel.setIcon(new ImageIcon(picture.getFileName()));
                    picture.incrementVisits();
                }
            }
        });
        JScrollPane pictureScrollPane = new JScrollPane(pictureList);

        pictureLabel = new JLabel();

        frame.setLayout(null);
        photographerLabel.setBounds(10, 10, 100, 20);
        photographerComboBox.setBounds(120, 10, 150, 20);
        dateLabel.setBounds(280, 10, 100, 20);
        datePicker.setBounds(390, 10, 150, 20);
        pictureScrollPane.setBounds(10, 40, 300, 300);
        pictureLabel.setBounds(320, 40, 250, 250);

        frame.add(photographerLabel);
        frame.add(photographerComboBox);
        frame.add(dateLabel);
        frame.add(datePicker);
        frame.add(pictureScrollPane);
        frame.add(pictureLabel);

        frame.setVisible(true);

        loadPicturesForPhotographerAndDate();

        photographerComboBox.addActionListener(e -> loadPicturesForPhotographerAndDate());
        datePicker.addActionListener(e -> loadPicturesForPhotographerAndDate());

        frame.getContentPane().setLayout(null);
        photographerLabel.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(photographerLabel);
        photographerComboBox.setBounds(100, 10, 200, 25);
        frame.getContentPane().add(photographerComboBox);
        dateLabel.setBounds(310, 10, 80, 25);
        frame.getContentPane().add(dateLabel);
        datePicker.setBounds(400, 10, 150, 25);
        frame.getContentPane().add(datePicker);
        pictureScrollPane.setBounds(10, 50, 200, 300);
        frame.getContentPane().add(pictureScrollPane);
        pictureLabel.setBounds(220, 50, 350, 300);
        frame.getContentPane().add(pictureLabel);
    }


        private List<Picture> loadPictures() {
            List<Picture> pictures = new ArrayList<>();

            try {
                ResultSet resultSet = connectionExample.select("SELECT * FROM pictures");
                while (resultSet.next()) {
                    int id = resultSet.getInt("PictureId");
                    String fileName = resultSet.getString("File");
                    Date date = resultSet.getDate("Date");
                    int photographerId = resultSet.getInt("PhotographerId");
                    int visits = resultSet.getInt("Visits");

                    Photographer photographer = findPhotographerById(photographerId);
                    pictures.add(new Picture(id, "./src/" + fileName, date, photographer, visits));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return pictures;
        }


    private Photographer findPhotographerById(int photographerId) {
        for (Photographer photographer : photographers) {
            if (photographer.getId() == photographerId) {
                return photographer;
            }
        }
        return null;
    }
    private List<String> loadPictureNames() {
        List<String> pictureNames = new ArrayList<>();

        try {
            ResultSet resultSet = connectionExample.select("SELECT fileName FROM pictures");
            while (resultSet.next()) {
                String fileName = resultSet.getString("fileName");
                pictureNames.add(fileName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pictureNames;
    }
    

    private List<Photographer> loadPhotographers() {
        List<Photographer> photographers = new ArrayList<>();

        try {
            ResultSet resultSet = connectionExample.select("SELECT * FROM photographers");
            while (resultSet.next()) {
                int id = resultSet.getInt("photographerId");
                String name = resultSet.getString("name");
                photographers.add(new Photographer(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photographers;
    }

    private void loadPicturesForPhotographerAndDate() {
        Photographer photographer = (Photographer) photographerComboBox.getSelectedItem();
        Date date = datePicker.getDate();
        DefaultListModel<Picture> model = (DefaultListModel<Picture>) pictureList.getModel();
        model.clear();

        for (Picture picture : pictures) {
            if (picture.getPhotographer().equals(photographer) && picture.getDate().after(date)) {
                model.addElement(picture);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        new PictureViewer();
    }
}





