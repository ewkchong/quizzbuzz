package model;

import org.json.JSONObject;

import java.util.ArrayList;

// A flash card with tags
public class Card {
    private String front;                   // Content on the "front" of the card
    private String back;                    // Content on the "back" of the card
    private ArrayList<String> tags;         // List of tags applied to card
    private int reviewCount;                // Running total of normal reviews done
    private long currentInterval;           // Current review interval in hours
    private long reviewDate;                // Next scheduled date of review
    private double ease;                    // Ease factor used in calculating next interval
    private int status;                     // 0 for "young" card, 1 for "mature" card

    // Constructs a card
    // EFFECTS: Card created with front, back, and tags
    public Card(String front, String back, ArrayList<String> tags) {
        this.front = front;
        this.back = back;
        this.tags = tags;
        reviewCount = 0;
        status = 0;
        currentInterval = 0;
        reviewDate = 0;
        ease = 2.5;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public double getEase() {
        return ease;
    }

    public long getCurrentInterval() {
        return currentInterval;
    }

    public long getReviewDate() {
        return reviewDate;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getStatus() {
        return status;
    }

    public void setFront(String s) {
        this.front = s;
    }

    public void setBack(String s) {
        this.back = s;
    }

    public Card setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public Card setStatus(int status) {
        this.status = status;
        return this;
    }

    public Card setCurrentInterval(long currentInterval) {
        this.currentInterval = currentInterval;
        return this;
    }

    public Card setReviewDate(long reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public Card setEase(double ease) {
        this.ease = ease;
        return this;
    }

    // MODIFIES: this
    // EFFECTS: removes tag named entry if in list,
    //          adds tag if tag entry not already in list
    public void changeTag(int i, String entry) {
        if (i != -1) {
            tags.remove(i);
        } else {
            tags.add(entry);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets tags as new array of strings
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    // EFFECTS: returns a JSONObject representation of this object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("front", front);
        json.put("back", back);
        json.put("tags", tags);
        json.put("reviewCount", reviewCount);
        json.put("status", status);
        json.put("currentInterval", currentInterval);
        json.put("reviewDate", reviewDate);
        json.put("ease", ease);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: determines the amount of days before next review date
    public void processReview(int diff) {
        calculateEase(diff);

        if (reviewCount == 0) {
            currentInterval = 24;
        } else if (reviewCount == 1) {
            currentInterval = 24 * 6;
        } else {
            currentInterval *= ease;
        }

        if (currentInterval >= (24 * 21)) {
            status = 1;
        }

        reviewCount++;
        long day = new ReviewCalendar().time();
        reviewDate = day + currentInterval;
    }

    // MODIFIES: this
    // EFFECTS: changes ease factor based on a specified difficulty of review
    //          if ease is too low (1.4), it gets reset to 1.4
    public void calculateEase(int diff) {
        if (ease <= 1.4) {
            ease = 1.4;
        } else {
            ease += (0.1 - (3 - diff) * (0.08 + (3 - diff) * 0.02));
        }
    }
}
