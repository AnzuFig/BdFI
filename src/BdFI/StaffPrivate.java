package BdFI;

import dataStructures.Entry;
import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface StaffPrivate extends Serializable {

    /**
     * Verifies if the person is participating in the given program
     *
     * @param show show to verify if the person is participating in
     * @return true iff the person is participating in the given show
     */
    boolean isParticipating(ProgramPrivate show);

    /**
     * Adds the given program to the person's participating programs
     *
     * @param show program to add
     */
    void addProgram(String idShow, ProgramPrivate show);

    /**
     * Removes the program with the given idShow from the persons participating programs list
     *
     * @param idShow id of the program to remove
     */
    void removeProgram(String idShow);

    /**
     * Verifies if the person is participating in a program
     *
     * @return true iff the person is participating in a program
     */
    boolean hasParticipations();

    /**
     * Returns an iterator of all the shows this person is participating in
     *
     * @return iterator of all the shows this person is participating in
     */
    Iterator<Entry<String, ProgramPublic>> getShowsIterator();
}
