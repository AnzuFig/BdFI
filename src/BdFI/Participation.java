package BdFI;

import java.io.Serializable;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface Participation extends Serializable {
    /**
     * Returns person participating
     *
     * @return the person
     */
    StaffPrivate getStaff();

    /**
     * Returns the function of the participant
     *
     * @return the description
     */
    String getDescription();
}
