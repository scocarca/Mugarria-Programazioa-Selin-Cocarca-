import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PictureViewer {
    private List<Photographer> photographers;
    private List<Picture> pictures;
    private JComboBox<Photographer> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<Picture> pictureList;
    private JLabel pictureLabel;

    public PictureViewer() {

        photographers = loadPhotographers();


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


        pictures = loadPictures();


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
    private List<Photographer> loadPhotographers() {


        List<Photographer> photographers = new ArrayList<>();
        photographers.add(new Photographer(1, "Ansel Adams"));
        photographers.add(new Photographer(2, "Vangogh"));
        return photographers;
    }

    private List<Picture> loadPictures() {


        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(1, "./ansealdams1.jpg", new Date(), photographers.get(0), 0));
        pictures.add(new Picture(1, "./ansealdams2.jpg", new Date(), photographers.get(1), 0));
        pictures.add(new Picture(2, "./rothko1.jpg", new Date(), photographers.get(0), 0));
        pictures.add(new Picture(2, "./vangogh1.jpg", new Date(), photographers.get(0), 0));
        pictures.add(new Picture(2, "./vangogh2.jpg", new Date(), photographers.get(0), 0));
        return pictures;
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



    public static void main(String[] args) {
        new PictureViewer();
    }
}