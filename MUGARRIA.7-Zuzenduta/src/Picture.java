import java.util.Date;
public class Picture {
    private int id;
    private String fileName;
    private Date date;
    private Photographer photographer;
    private int visits;

    public Picture(int id, String fileName, Date date, Photographer photographer, int visits) {
        this.id = id;
        this.fileName = fileName;
        this.date = date;
        this.photographer = photographer;
        this.visits = visits;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getDate() {
        return date;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public int getVisits() {
        return visits;
    }

    public void incrementVisits() {
        visits++;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
