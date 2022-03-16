package BdFI;

import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.Iterator;
import exceptions.*;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface DataBaseManager extends Serializable {
    /**
     * Adds a person to the system.
     *
     * @param idPerson  person's identifier
     * @param year      person's birth year
     * @param email     person's email
     * @param telephone person's phone number
     * @param gender    person's gender
     * @param name      person's name
     * @throws InvalidYearException        if the given year is not valid
     * @throws PersonAlreadyExistException if there is already a person with the given identifier in the system
     * @throws InvalidGenderException      if the given gender is not valid
     */
    void addPerson(String idPerson, int year, String email, String telephone, String gender,
                   String name) throws InvalidYearException, PersonAlreadyExistException, InvalidGenderException;

    /**
     * Adds a program to the system.
     *
     * @param idShow program's identifier
     * @param year   program's production year
     * @param title  program's title
     * @throws InvalidYearException       if the given year is not valid
     * @throws ShowAlreadyExistsException if there is already a program with the given identifier in the system
     */
    void addShow(String idShow, int year, String title) throws InvalidYearException, ShowAlreadyExistsException;

    /**
     * Adds a participation to a program.
     *
     * @param idPerson    person's identifier
     * @param idShow      program's identifier
     * @param description a brief description of the person's contribution to the program
     * @throws IdShowDoesNotExistException   if the program with the given identifier does not exist
     * @throws IdPersonDoesNotExistException if the person with the given identifier does not exist
     */
    void addParticipation(String idPerson, String idShow, String description)
            throws IdShowDoesNotExistException, IdPersonDoesNotExistException;

    /**
     * If the program has not yet premiered, removes it from the system.
     *
     * @param idShow program's identifier
     * @throws IdShowAlreadyPremieredException if the program has already premiered
     * @throws IdShowDoesNotExistException     if the program with the given identifier does not exist
     */
    void removeShow(String idShow) throws IdShowAlreadyPremieredException, IdShowDoesNotExistException;

    /**
     * Set's a program as premiered.
     *
     * @param idShow program's identifier
     * @throws IdShowAlreadyPremieredException if the program has already premiered
     * @throws IdShowDoesNotExistException     if the program with the given identifier does not exist
     */
    void premier(String idShow) throws IdShowAlreadyPremieredException, IdShowDoesNotExistException;

    /**
     * Add's a new tag to a program.
     *
     * @param idShow program's identifier
     * @param tag    tag to add
     * @throws IdShowDoesNotExistException if the program with the given identifier does not exist
     */
    void tagShow(String idShow, String tag) throws IdShowDoesNotExistException;

    /**
     * Returns a program with a specific identifier.
     *
     * @param idShow program's identifier
     * @return the program
     * @throws IdShowDoesNotExistException if the program with the given identifier does not exist
     */
    ProgramPublic getInfoShowProgram(String idShow) throws IdShowDoesNotExistException;

    /**
     * Returns an iterator to list the tags of a program.
     *
     * @param idShow program's identifier
     * @return The program's tag list's iterator
     */
    Iterator<String> tagShowIterator(String idShow);

    /**
     * Adds a rating to a program between 0 (zero) and 10 (ten) stars.
     * This new rating will then update the program's average rating.
     *
     * @param idShow program's identifier
     * @param stars  integer number between 0 (zero) and 10 (ten)
     * @throws InvalidRatingException         if the given rating is not between 0 (zero) and 10 (ten)
     * @throws IdShowNotYetPremieredException if the program has not yet premiered
     * @throws IdShowDoesNotExistException    if the program with the given identifier does not exist
     */
    void rateShow(String idShow, int stars) throws InvalidRatingException, IdShowNotYetPremieredException, IdShowDoesNotExistException;

    /**
     * Returns a person with a specific identifier.
     *
     * @param idPerson person's id
     * @return the person
     * @throws IdPersonDoesNotExistException if the person with the given identifier does not exit
     */
    StaffPublic getPerson(String idPerson) throws IdPersonDoesNotExistException;

    /**
     * Returns each program that the person has participated in.
     *
     * @param idPerson person's identifier
     * @return the program
     * @throws IdPersonDoesNotExistException if the person with the given identifier does not exit
     * @throws PersonHasNoShowsException     if the given person does not have participated in the show
     */
    Iterator<Entry<String, ProgramPublic>> listPersonShows(String idPerson) throws IdPersonDoesNotExistException, PersonHasNoShowsException;

    /**
     * Returns an iterator to list the participations of a program with the given identifier.
     *
     * @param idShow program's identifier
     * @return The program's participation list's iterator
     * @throws IdShowDoesNotExistException        if the program with the given identifier does not exist
     * @throws IdShowEmptyParticipationsException if the given show has no participations
     */
    Iterator<Participation> listParticipations(String idShow) throws IdShowDoesNotExistException, IdShowEmptyParticipationsException;

    /**
     * Returns an iterator with the best programs
     *
     * @return the program
     * @throws NoShowsException            if there are no programs in the system
     * @throws NoShowsPremieredException   if there are no program premiered yet
     * @throws NoRatedProductionsException if there are  program was no rated yet
     */
    Iterator<Entry<String, ProgramPublic>> listBestShows() throws NoShowsException, NoShowsPremieredException, NoRatedProductionsException;

    /**
     * Returns an iterator of the programs with the given rating
     *
     * @param rating rating of the program to list
     * @return system's program
     * @throws InvalidRatingException                if the given rating is not between 0 (zero) and 10 (ten)
     * @throws NoShowsException                      if there are no programs in the system
     * @throws NoShowsPremieredException             if there are no programs premiered yet
     * @throws NoRatedProductionsException           if there are no programs rated yet
     * @throws NoProductionsWithGivenRatingException if there are no programs with the given rating
     */
    Iterator<Entry<String, ProgramPublic>> listShows(int rating) throws InvalidRatingException, NoShowsException, NoShowsPremieredException, NoRatedProductionsException, NoProductionsWithGivenRatingException;

    /**
     * Returns an iterator with all shows that contain a given tag
     *
     * @param tag tag that all shows must contain
     * @return iterator with all shows that contain a given tag
     * @throws NoShowsException       if there is no program in the system
     * @throws NoTaggedShowsException if there are no programs with any tag
     * @throws NoShowWithTagException if there are no programs with given tag
     */
    Iterator<Entry<String, ProgramPublic>> listTaggedShows(String tag) throws NoShowsException, NoTaggedShowsException, NoShowWithTagException;
}
