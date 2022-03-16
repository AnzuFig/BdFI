package BdFI;

import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface ProgramPrivate extends Serializable, ProgramPublic {

    /**
     * Adds a participation to the participation list
     *
     * @param participation participation to add
     */
    void addParticipation(Participation participation);

    /**
     * Returns an iterator of the participation list
     *
     * @return iterator of the participation list
     */
    Iterator<Participation> getParticipationsIterator();

    /**
     * Returns an iterator of the tags list
     *
     * @return iterator of the tags list
     */
    Iterator<String> getTagsIterator();

    /**
     * Verifies whether the program has premiered or not
     *
     * @return true iff the program has premiered
     */
    boolean hasPremiered();

    /**
     * Set's the program has premiered
     */
    void premier();

    /**
     * Adds a tag to the tags list
     *
     * @param tag tag to add
     */
    void addTag(String tag);

    /**
     * Adds the given rating to the program's average rating
     *
     * @param stars rating
     */
    void addRating(int stars);

    /**
     * Verifies whether the program has been rated
     *
     * @return true iff the program has been rating at least once
     */
    boolean isRated();

}
