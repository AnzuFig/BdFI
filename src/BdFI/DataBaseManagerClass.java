package BdFI;

import dataStructures.*;
import exceptions.*;

import java.io.Serializable;
import java.time.YearMonth;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jha.garcia@campus.fct.unl.pt
 **/
public class DataBaseManagerClass implements DataBaseManager, Serializable {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    /**
     * The system's people
     */
    Dictionary<String, StaffPrivate> people;

    /**
     * The system's programs
     */
    Dictionary<String, ProgramPrivate> programs;

    /**
     * Dictionary with each tag's programs
     */
    Dictionary<String, BinarySearchTree<String, ProgramPublic>> programsByTag;

    /**
     * Dictionary with people that participated in each program
     */
    Dictionary<String, DoubleList<StaffPrivate>> staffByShow;

    /**
     * Ordered dictionary with lists of program's with certain rating
     */
    OrderedDictionary<Integer, BinarySearchTree<String, ProgramPublic>> programsByRating;

    /**
     * current year
     */
    int currentYear;

    /**
     * DataBaseManagerClass's constructor
     */
    public DataBaseManagerClass() {
        people = new SepChainHashTable<>();
        programs = new SepChainHashTable<>();
        programsByTag = new SepChainHashTable<>();
        staffByShow = new SepChainHashTable<>();
        programsByRating = new OrderedDoubleList<>();
        currentYear = YearMonth.now().getYear();
    }

    @Override
    public void addPerson(String idPerson, int year, String email, String telephone, String gender,
                          String name) throws InvalidYearException, InvalidGenderException, PersonAlreadyExistException {

        if (!validYear(year)) {
            throw new InvalidYearException();
        } else if (!validGender(gender)) {
            throw new InvalidGenderException();
        } else if (hasPerson(idPerson)) {
            throw new PersonAlreadyExistException();
        } else {
            StaffPrivate person = new StaffClass(idPerson, year, email, telephone, gender, name);
            people.insert(idPerson.toLowerCase(), person);
        }

    }

    @Override
    public void addShow(String idShow, int year, String title) throws InvalidYearException, ShowAlreadyExistsException {
        if (!validYear(year)) {
            throw new InvalidYearException();
        } else if (hasShowWithId(idShow)) {
            throw new ShowAlreadyExistsException();
        } else {
            ProgramPrivate program = new ProgramClass(idShow, year, title);
            programs.insert(idShow.toLowerCase(), program);
            staffByShow.insert(idShow, new DoubleList<>());
            if (year < currentYear) {
                program.premier();
            }


        }
    }

    @Override
    public void addParticipation(String idPerson, String idShow, String description) throws IdShowDoesNotExistException, IdPersonDoesNotExistException {


        if (!hasPerson(idPerson)) {
            throw new IdPersonDoesNotExistException();
        } else if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        } else {
            StaffPrivate person = getPersonNoException(idPerson);
            ProgramPrivate show = getShow(idShow);
            Participation participation = new ParticipationClass(person, description);
            if (!person.isParticipating(show)) {
                person.addProgram(idShow.toLowerCase(), show);
            }
            show.addParticipation(participation);
            staffByShow.find(idShow.toLowerCase()).addLast(person);
        }
    }

    @Override
    public void removeShow(String idShow) throws IdShowAlreadyPremieredException, IdShowDoesNotExistException {
        if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        }

        ProgramPrivate show = getShow(idShow);

        if(show.hasPremiered()) {
            throw new IdShowAlreadyPremieredException();
        } else {
            removeShowFromTagsDictionary(show);
            programs.remove(idShow.toLowerCase());
            if (!people.isEmpty()) {
                removeShowFromStaff(idShow.toLowerCase());
            }
            staffByShow.remove(idShow.toLowerCase());
        }
    }


    @Override
    public void premier(String idShow) throws IdShowAlreadyPremieredException, IdShowDoesNotExistException {
        if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        } else if (hasPremiered(idShow)) {
            throw new IdShowAlreadyPremieredException();
        } else {
            getShow(idShow).premier();
        }
    }

    @Override
    public void tagShow(String idShow, String tag) throws IdShowDoesNotExistException {
        if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        } else {
            if (programsByTag.find(tag.toLowerCase()) == null) {
                programsByTag.insert(tag.toLowerCase(), new BinarySearchTree<>());
            }
            ProgramPrivate program = getShow(idShow);
            programsByTag.find(tag.toLowerCase()).insert(program.getTitle(), program);
            programs.find(idShow.toLowerCase()).addTag(tag);
        }
    }


    @Override
    public ProgramPublic getInfoShowProgram(String idShow) throws IdShowDoesNotExistException {
        if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        } else {
            return getShow(idShow);
        }
    }

    @Override
    public Iterator<String> tagShowIterator(String idShow) {
        return getShow(idShow).getTagsIterator();
    }

    @Override
    public StaffPublic getPerson(String idPerson) throws IdPersonDoesNotExistException {
        if (!hasPerson(idPerson)) {
            throw new IdPersonDoesNotExistException();
        } else {
            return (StaffPublic) getPersonNoException(idPerson);
        }
    }

    @Override
    public Iterator<Entry<String, ProgramPublic>> listPersonShows(String idPerson) throws IdPersonDoesNotExistException, PersonHasNoShowsException {
        if (!hasPerson(idPerson))
            throw new IdPersonDoesNotExistException();
        if (!hasParticipations(idPerson))
            throw new PersonHasNoShowsException();
        else

            return getPersonNoException(idPerson).getShowsIterator();
    }

    @Override
    public Iterator<Participation> listParticipations(String idShow) throws IdShowDoesNotExistException, IdShowEmptyParticipationsException {
        if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        }
        ProgramPrivate show = getShow(idShow);
        Iterator<Participation> it = show.getParticipationsIterator();
        if (!it.hasNext()) {
            throw new IdShowEmptyParticipationsException();
        } else {
            return it;
        }
    }

    @Override
    public Iterator<Entry<String, ProgramPublic>> listBestShows() throws NoShowsException, NoShowsPremieredException, NoRatedProductionsException {
        if (!hasShow())
            throw new NoShowsException();
        if (!hasPremieredShows())
            throw new NoShowsPremieredException();
        if (!hasRatedShows())
            throw new NoRatedProductionsException();
        else
            return programsByRating.maxEntry().getValue().iterator();
    }

    @Override
    public Iterator<Entry<String, ProgramPublic>> listShows(int rating) throws InvalidRatingException, NoShowsException, NoShowsPremieredException, NoRatedProductionsException, NoProductionsWithGivenRatingException {
        if (!(rating >= 0 && rating <= 10)) {
            throw new InvalidRatingException();
        }
        if (!hasShow())
            throw new NoShowsException();
        if (!hasPremieredShows())
            throw new NoShowsPremieredException();
        if (!hasRatedShows())
            throw new NoRatedProductionsException();
        if (!hasShowWithRating(rating))
            throw new NoProductionsWithGivenRatingException();

        return programsByRating.find(rating).iterator();
    }

    @Override
    public Iterator<Entry<String, ProgramPublic>> listTaggedShows(String tag) throws NoShowsException, NoTaggedShowsException, NoShowWithTagException {
        if (!hasShow()) {
            throw new NoShowsException();
        }
        if (programsByTag.isEmpty()) {
            throw new NoTaggedShowsException();
        }
        if (programsByTag.find(tag.toLowerCase()) == null) {
            throw new NoShowWithTagException();
        }
        return programsByTag.find(tag.toLowerCase()).iterator();
    }


    @Override
    public void     rateShow(String idShow, int stars) throws InvalidRatingException, IdShowNotYetPremieredException, IdShowDoesNotExistException {
        if (!(stars >= 0 && stars <= 10)) {
            throw new InvalidRatingException();
        } else if (!hasShowWithId(idShow)) {
            throw new IdShowDoesNotExistException();
        } else if (!getShow(idShow).hasPremiered()) {
            throw new IdShowNotYetPremieredException();
        } else {
            ProgramPrivate program = getShow(idShow);
            int oldRating = program.getRating();
            boolean wasRated = program.isRated();
            program.addRating(stars);
            updateProgramsByRating(program, oldRating, wasRated);
        }
    }

    // -- Private methods --

    /**
     * Adjusts programsByRating to the new average rating of the program
     * @param program the program whose average rating changed
     * @param oldRating average rating before the new rating
     * @param wasRated whether the program had been previously rated or not
     */
    private void updateProgramsByRating(ProgramPrivate program, int oldRating, boolean wasRated) {
        if (programsByRating.find(program.getRating()) == null) {
            programsByRating.insert(program.getRating(), new BinarySearchTree<>());
        }
        programsByRating.find(program.getRating()).insert(program.getTitle(), program);
        if (wasRated) {
            if (oldRating != program.getRating()) {
                programsByRating.find(oldRating).remove(program.getTitle());
            }
        }
    }

    /**
     * Removes a program from each person in staffByShow dictionary.
     *
     * @param idShow show identifier
     */
    private void removeShowFromStaff(String idShow) {
        Iterator<StaffPrivate> it = staffByShow.find(idShow).iterator();
        while (it.hasNext()) {
            it.next().removeProgram(idShow);
        }
    }

    /**
     * Verifies if there are any programs premiered.
     *
     * @return true iff there is at least one program premiered
     */
    private boolean hasPremieredShows() {
        Iterator<Entry<String, ProgramPrivate>> it = programs.iterator();
        boolean foundPremiered = false;
        while (it.hasNext() && !foundPremiered) {
            foundPremiered = it.next().getValue().hasPremiered();
        }
        return foundPremiered;
    }

    /**
     * Verifies if the person with the given identifier has participated in a program.
     *
     * @param idPerson person identifier
     * @return true iff there is at least one participation
     */
    private boolean hasParticipations(String idPerson) {
        return getPersonNoException(idPerson).hasParticipations();
    }

    /**
     * Removes a program from each tag that has it from programsByTag dictionary
     *
     * @param show the program
     */
    private void removeShowFromTagsDictionary(ProgramPrivate show) {
        Iterator<String> it = show.getTagsIterator();
        while (it.hasNext()) {
            String tag = it.next();
            BinarySearchTree<String, ProgramPublic> programsWithTag = programsByTag.find(tag.toLowerCase());
            programsWithTag.remove(show.getTitle());
            if (programsWithTag.isEmpty()) {
                programsByTag.remove(tag.toLowerCase());
            }
        }
    }

    /**
     * Verifies whether the given rating is the same as the program's one
     *
     * @param rating the given rating to compare
     * @return true iff the program has the given rating
     */
    private boolean hasShowWithRating(int rating) {
        boolean has = false;
        BinarySearchTree<String, ProgramPublic> rated = programsByRating.find(rating);
        if (rated != null) {
            if (!rated.isEmpty()) {
                has = true;
            }
        }
        return has;
    }

    /**
     * Verifies whether there is any programs that are rated
     *
     * @return true iff there is at least one program that is rated
     */
    private boolean hasRatedShows() {
        return !programsByRating.isEmpty();
    }

    /**
     * Verifies whether exists a program in the system or not
     *
     * @return true iff there is a show
     */
    private boolean hasShow() {
        return !programs.isEmpty();
    }

    /**
     * Verifies if the program has been premiered
     *
     * @return true iff the program has premiered
     */
    private boolean hasPremiered(String idShow) {
        return getShow(idShow).hasPremiered();
    }

    /**
     * Returns the person
     *
     * @return the person
     */

    private StaffPrivate getPersonNoException(String idPerson) {
        return people.find(idPerson.toLowerCase());
    }

    /**
     * Returns the program
     *
     * @return the program
     */
    private ProgramPrivate getShow(String idShow) {
        return programs.find(idShow.toLowerCase());
    }

    /**
     * Verifies if there is a person registered in the system
     * and if so, if that person's identifier is the same as the
     * given id.
     *
     * @param idPerson person's identifier
     * @return true iff there is a person registered in the system and that person's id
     * is the same as the give id.
     */
    private boolean hasPerson(String idPerson) {
        return people.find(idPerson.toLowerCase()) != null;
    }

    /**
     * Verifies whether the given id is the same as the program's in the system
     *
     * @param idShow the given program identifier
     * @return true iff the system's program has the same id as the given id and there is a program
     */
    private boolean hasShowWithId(String idShow) {
        return getShow(idShow) != null;
    }

    /**
     * Verifies if the given gender is one of the four available genders (Male, Female, Not-Provided, Other)
     *
     * @param gender gender to verify
     * @return true iff the gender is valid
     */
    private boolean validGender(String gender) {
        boolean valid = false;
        for (Gender gender1 : Gender.values()) {
            if (gender.equalsIgnoreCase(gender1.getGender())) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    /**
     * Verifies if the given year is above 0 (zero)
     *
     * @param year year to verify
     * @return true iff the year is valid
     */
    private boolean validYear(int year) {
        return year > 0;
    }

}
