import BdFI.DataBaseManager;
import BdFI.DataBaseManagerClass;
import BdFI.ProgramPublic;
import BdFI.StaffPublic;
import BdFI.Participation;
import dataStructures.Entry;
import dataStructures.Iterator;
import exceptions.*;

import java.io.*;
import java.util.Scanner;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class Main {

    private static final String DATA_FILE = "storedDataManager.dat";

    /**
     * Commands that should be received through the console
     */
    private enum Command {
        ADDPERSON, ADDSHOW, ADDPARTICIPATION, PREMIERE, REMOVESHOW, TAGSHOW, INFOSHOW, RATESHOW, INFOPERSON,
        LISTSHOWSPERSON, LISTPARTICIPATIONS, LISTBESTSHOWS, LISTSHOWS, LISTTAGGEDSHOWS, QUIT, UNKNOWN
    }

    /**
     * System messages and formats to display to the user
     */
    private enum Msg {
        PERSON_ADDED("Person added."), SHOW_ADDED("Show added."),
        PARTICIPATION_ADDED("Participation added."), SHOW_REMOVED("Show removed."), PREMIERED("Successful production."),
        TAG_ADDED("Tag added."), RATING_APPLIED("Rating applied."), PARTICIPATION_LISTING("%s %s %d %s %s %s %s\n"),
        INFO_SHOW_HEADER("%s %s %d %d\n"), PERSON_INFO_HEADER("%s %s %d %s %s %s\n"),
        QUIT_MESSAGE("Serializing and quitting..."), TAGGED_SHOW_HEADER("%s %s %d %d\n");

        private final String msg;

        Msg(String msg) {
            this.msg = msg;
        }

        String getMsg() {
            return msg;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        runCommands();
    }

    /**
     * Command interpreter
     */
    private static void runCommands() {
        DataBaseManager data = load();
        Scanner in = new Scanner(System.in);
        Command com = null;

        while (com != Command.QUIT) {
            com = getCom(in);
            switch (com) {
                case ADDPERSON:
                    addPerson(in, data);
                    break;
                case ADDSHOW:
                    addShow(in, data);
                    break;
                case ADDPARTICIPATION:
                    addParticipation(in, data);
                    break;
                case PREMIERE:
                    premiere(in, data);
                    break;
                case REMOVESHOW:
                    removeShow(in, data);
                    break;
                case TAGSHOW:
                    tagShow(in, data);
                    break;
                case INFOSHOW:
                    infoShow(in, data);
                    break;
                case RATESHOW:
                    rateShow(in, data);
                    break;
                case INFOPERSON:
                    infoPerson(in, data);
                    break;
                case LISTSHOWSPERSON:
                    listShowPerson(in, data);
                    break;
                case LISTPARTICIPATIONS:
                    listParticipations(in, data);
                    break;
                case LISTBESTSHOWS:
                    listBestShows(data);
                    break;
                case LISTSHOWS:
                    listShows(in, data);
                    break;
                case LISTTAGGEDSHOWS:
                    listTaggedShows(in, data);
                    break;
                case QUIT:
                    System.out.println(Msg.QUIT_MESSAGE.getMsg());
                    break;
                default:
                    break;
            }
            System.out.println();
        }
        in.close();
        save(data);
    }

    /**
     * Receives a command and verifies if it is one of the  expected ones
     *
     * @param in Input Scanner
     * @return the expected command
     */
    private static Command getCom(Scanner in) {
        try {
            return Command.valueOf(in.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    /**
     * Adds a person to the system
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void addPerson(Scanner in, DataBaseManager data) {

        String idPerson = in.next().trim();
        int year = in.nextInt();
        String email = in.next();
        String telephone = in.next();
        String gender = in.next();
        String name = in.nextLine().trim();

        try {
            data.addPerson(idPerson, year, email, telephone, gender, name);
            System.out.println(Msg.PERSON_ADDED.getMsg());
        } catch (InvalidYearException | PersonAlreadyExistException | InvalidGenderException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Adds a program to the system
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void addShow(Scanner in, DataBaseManager data) {
        String idShow = in.next().trim();
        int year = in.nextInt();
        String title = in.nextLine().trim();
        try {
            data.addShow(idShow, year, title);
            System.out.println(Msg.SHOW_ADDED.getMsg());
        } catch (ShowAlreadyExistsException | InvalidYearException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a persons' participation
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void addParticipation(Scanner in, DataBaseManager data) {
        String idPerson = in.next().trim();
        String idShow = in.next();
        String description = in.nextLine().trim();
        try {
            data.addParticipation(idPerson, idShow, description);
            System.out.println(Msg.PARTICIPATION_ADDED.getMsg());
        } catch (IdPersonDoesNotExistException | IdShowDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Premieres a program
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void premiere(Scanner in, DataBaseManager data) {
        String idShow = in.next().trim();
        in.nextLine();
        try {
            data.premier(idShow);
            System.out.println(Msg.PREMIERED.getMsg());
        } catch (IdShowAlreadyPremieredException | IdShowDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a program from the system
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void removeShow(Scanner in, DataBaseManager data) {
        String idShow = in.nextLine().trim();
        try {
            data.removeShow(idShow);
            System.out.println(Msg.SHOW_REMOVED.getMsg());
        } catch (IdShowDoesNotExistException | IdShowAlreadyPremieredException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a tag to an existing program
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void tagShow(Scanner in, DataBaseManager data) {
        String idShow = in.next();
        String tag = in.nextLine().trim();
        try {
            data.tagShow(idShow, tag);
            System.out.println(Msg.TAG_ADDED.getMsg());
        } catch (IdShowDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the information from a program
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void infoShow(Scanner in, DataBaseManager data) {
        String idShow = in.nextLine().trim();
        try {
            ProgramPublic program = data.getInfoShowProgram(idShow);
            System.out.printf(Msg.INFO_SHOW_HEADER.getMsg(), program.getIdShow(), program.getTitle(), program.getYear(), program.getRating());
            Iterator<String> it = data.tagShowIterator(idShow);
            while (it.hasNext()) {
                String tag = it.next();
                System.out.println(tag);
            }
        } catch (IdShowDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a rating to a program
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void rateShow(Scanner in, DataBaseManager data) {
        String idShow = in.next().trim();
        int stars = in.nextInt();
        in.nextLine();
        try {
            data.rateShow(idShow, stars);
            System.out.println(Msg.RATING_APPLIED.getMsg());
        } catch (IdShowDoesNotExistException | IdShowNotYetPremieredException | InvalidRatingException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gets the information from a person
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void infoPerson(Scanner in, DataBaseManager data) {
        String idPerson = in.nextLine().trim();
        try {
            StaffPublic person = data.getPerson(idPerson);
            System.out.printf(Msg.PERSON_INFO_HEADER.getMsg(), person.getId(), person.getName(), person.getBirthYear(),
                    person.getEmail(), person.getPhoneN(), person.getGender());
        } catch (IdPersonDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the programs which a certain person has participated in
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */

    private static void listShowPerson(Scanner in, DataBaseManager data) {
        String idPerson = in.nextLine().trim();
        try {
            Iterator<Entry<String, ProgramPublic>> it = data.listPersonShows(idPerson);
            while (it.hasNext()) {
                ProgramPublic program = it.next().getValue();
                System.out.printf(Msg.INFO_SHOW_HEADER.getMsg(), program.getIdShow(), program.getTitle(), program.getYear(), program.getRating());
            }
        } catch (PersonHasNoShowsException | IdPersonDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets all the participations of a certain program
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void listParticipations(Scanner in, DataBaseManager data) {
        String idShow = in.nextLine().trim();
        try {
            Iterator<Participation> it = data.listParticipations(idShow);
            while (it.hasNext()) {
                Participation p = it.next();
                StaffPublic s = (StaffPublic) p.getStaff();
                String description = p.getDescription();
                System.out.printf(Msg.PARTICIPATION_LISTING.getMsg(), s.getId(), s.getName(), s.getBirthYear(),
                        s.getEmail(), s.getPhoneN(), s.getGender(), description);
            }
        } catch (IdShowDoesNotExistException | IdShowEmptyParticipationsException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Lists the programs with the highest ratings
     *
     * @param data Data Manager
     */
    private static void listBestShows(DataBaseManager data) {
        try {
            Iterator<Entry<String, ProgramPublic>> it = data.listBestShows();
            while (it.hasNext()) {
                ProgramPublic program = it.next().getValue();
                System.out.printf(Msg.INFO_SHOW_HEADER.getMsg(), program.getIdShow(), program.getTitle(), program.getYear(), program.getRating());
            }
        } catch (NoShowsPremieredException | NoShowsException | NoRatedProductionsException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Lists the programs that have a certain rating
     *
     * @param in   Input Scanner
     * @param data Data Manager
     */
    private static void listShows(Scanner in, DataBaseManager data) {
        int rating = in.nextInt();
        in.nextLine();
        try {
            Iterator<Entry<String, ProgramPublic>> it = data.listShows(rating);
            while (it.hasNext()) {
                ProgramPublic program = it.next().getValue();
                System.out.printf(Msg.INFO_SHOW_HEADER.getMsg(), program.getIdShow(), program.getTitle(), program.getYear(), program.getRating());
            }
        } catch (InvalidRatingException | NoShowsPremieredException | NoRatedProductionsException | NoShowsException | NoProductionsWithGivenRatingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listTaggedShows(Scanner in, DataBaseManager data) {
        String tag = in.nextLine().trim();
        try {
            Iterator<Entry<String, ProgramPublic>> it = data.listTaggedShows(tag);
            while (it.hasNext()) {
                ProgramPublic program = it.next().getValue();
                System.out.printf(Msg.TAGGED_SHOW_HEADER.getMsg(), program.getIdShow(), program.getTitle(), program.getYear(), program.getRating());
            }
        } catch (NoShowsException | NoTaggedShowsException | NoShowWithTagException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the program externally creating a file if there is not one already
     *
     * @param data Data Manager
     */
    private static void save(DataBaseManager data) {
        try {
            ObjectOutputStream file = new ObjectOutputStream(
                    new FileOutputStream(DATA_FILE));
            file.writeObject(data);
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Erro no save: " + e.getMessage());
        }
    }

    /**
     * Loads a program externally if there is one
     *
     * @return the loaded data manager
     */
    private static DataBaseManager load() {
        DataBaseManager data;
        try {
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream(DATA_FILE));
            data = (DataBaseManager) file.readObject();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            data = new DataBaseManagerClass();
        }

        return data;
    }

}
