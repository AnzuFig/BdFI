package BdFI;

import java.io.Serializable;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface StaffPublic extends Serializable {
    /**
     * Gets the person's identifier
     *
     * @returnthe id
     */
    String getId();

    /**
     * Gets the person's birth year
     *
     * @return the year
     */
    int getBirthYear();

    /**
     * Gets the person's electronic mail
     *
     * @return the email
     */
    String getEmail();

    /**
     * Gets the person's phone number
     *
     * @return the phone number
     */
    String getPhoneN();

    /**
     * Gets the persons name
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the person's gender
     *
     * @return the gender
     */
    String getGender();
}
