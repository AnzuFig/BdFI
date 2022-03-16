package BdFI;

import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.DoubleList;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class ProgramClass implements ProgramPrivate, ProgramPublic {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    /**
     * The program's identifier
     */
    private final String idShow;

    /**
     * The program's production
     */
    private final int year;

    /**
     * The program's title
     */
    private final String title;

    /**
     * List of the program's participations
     */
    private List<Participation> participations;

    /**
     * List of the program's tags
     */
    private List<String> tags;

    /**
     * Whether the program ha premiered or not
     */
    private boolean hasPremiered;

    /**
     * The average rating of the program
     */
    private int rating;

    /**
     * The counter o times the program has been rated
     */
    private int ratingsCount;

    /**
     * ProgramClass constructor
     *
     * @param idShow the program's identifier
     * @param year   the program's production
     * @param title  the program's title
     */
    public ProgramClass(String idShow, int year, String title) {
        this.idShow = idShow;
        this.year = year;
        this.title = title;
        participations = new DoubleList<>();
        tags = new DoubleList<>();
        hasPremiered = false;
        rating = 0;
        ratingsCount = 0;
    }

    @Override
    public Iterator<Participation> getParticipationsIterator() {
        return participations.iterator();
    }

    @Override
    public boolean hasPremiered() {
        return hasPremiered;
    }

    @Override
    public String getIdShow() {
        return idShow;
    }

    @Override
    public void addTag(String tag) {
        tags.addLast(tag);
    }

    @Override
    public void addRating(int stars) {
        rating = bdfiAlg.updateReview(stars, ratingsCount, rating);
        ratingsCount++;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public Iterator<String> getTagsIterator() {
        return tags.iterator();
    }

    @Override
    public boolean isRated() {
        return ratingsCount > 0;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void addParticipation(Participation participation) {
        participations.addLast(participation);
    }

    @Override
    public void premier() {
        hasPremiered = true;
    }
}
