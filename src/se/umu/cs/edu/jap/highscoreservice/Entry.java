package se.umu.cs.edu.jap.highscoreservice;

public class Entry implements Comparable<Entry> {
    private final String name;
    private final String date;
    private final String score;

    // ---------------------------------------------------------
    public Entry(String name, String date, String score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    // ---------------------------------------------------------
    public String getName() {
        return name;
    }

    // ---------------------------------------------------------
    public String getDate() {
        return date;
    }

    // ---------------------------------------------------------
    public String getScore() {
        return score;
    }

    // ---------------------------------------------------------
    @Override
    public String toString() {
        return score + " (" + name + " @ " + date + ")";
    }

    // ---------------------------------------------------------
    // ---------------------------------------------------------
    public int compareTo(Entry rhs) {
        return -score.compareTo(rhs.score);
    }
}
