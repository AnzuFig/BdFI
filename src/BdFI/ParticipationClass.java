package BdFI;

import java.util.Objects;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public class ParticipationClass implements Participation {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    /**
     * The person responsible for the participation
     */
    StaffPrivate person;

    /**
     * Brief description of the participation
     */
    String description;

    /**
     * ParticipationClass's constructor
     *
     * @param person person responsible for the participation
     * @param description brief description of the participation
     */
    public ParticipationClass(StaffPrivate person, String description) {
        this.person = person;
        this.description = description;
    }

    @Override
    public StaffPrivate getStaff() {
        return person;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationClass that = (ParticipationClass) o;
        return Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }
}
