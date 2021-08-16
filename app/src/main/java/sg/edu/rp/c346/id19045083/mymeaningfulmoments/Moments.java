package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import java.io.Serializable;

public class Moments implements Serializable {

    private int id;
    private String title;
    private String location;
    private String date;
    private String description;
    private int stars;          // Rate the experience of the moment

    public Moments(int id, String title, String location, String date, String description, int stars) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
