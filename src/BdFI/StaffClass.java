package BdFI;

import dataStructures.*;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class StaffClass implements StaffPrivate, StaffPublic {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    /**
     * the person's identifier
     */
    private final String idPerson;

    /**
     * the person's birth year
     */
    private final int year;

    /**
     * the person's electronic mail
     */
    private final String email;

    /**
     * the person's phone number
     */
    private final String phone;

    /**
     * the person's gender
     */
    private final String gender;

    /**
     * the person's name
     */
    private final String name;

    /**
     * List of all programs the person participates in.
     * We use a dictionary where the key is the program id and the value is the program.
     */
    private OrderedDictionary<String, ProgramPublic> programs;

    /**
     * the StaffClass constructor
     *
     * @param idPerson  the person's identifier
     * @param year      the person's birth year
     * @param email     the person's electronic mail
     * @param telephone the person's phone number
     * @param gender    the person's gender
     * @param name      the person's name
     */
    public StaffClass(String idPerson, int year, String email, String telephone, String gender, String name) {
        this.idPerson = idPerson;
        this.year = year;
        this.email = email;
        this.phone = telephone;
        this.gender = gender;
        this.name = name;
        programs = new BinarySearchTree<>();
    }

    @Override
    public String getId() {
        return idPerson;
    }

    @Override
    public int getBirthYear() {
        return year;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneN() {
        return phone;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isParticipating(ProgramPrivate show) {
        return programs.find(show.getIdShow().toLowerCase()) != null;
    }

    @Override
    public void addProgram(String idShow, ProgramPrivate show) {
        programs.insert(show.getIdShow(), show);
    }

    @Override
    public void removeProgram(String idShow) {
        programs.remove(idShow);
    }

    @Override
    public boolean hasParticipations() {
        return !programs.isEmpty();
    }

    @Override
    public Iterator<Entry<String, ProgramPublic>> getShowsIterator() {
        return programs.iterator();
    }

}
