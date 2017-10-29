package vn.edu.hust.set.tung.rikkei_assignment.model;

/**
 * Created by tungt on 10/29/17.
 */

public class Image {
    private long id;
    private String link;

    public Image(String link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
