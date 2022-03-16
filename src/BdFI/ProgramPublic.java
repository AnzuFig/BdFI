package BdFI;

import java.io.Serializable;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public interface ProgramPublic extends Serializable {

    /**
     * Gets the production year of the program
     *
     * @return the year
     */
    int getYear();

    /**
     * Gets the title of the program
     *
     * @return the title
     */
    String getTitle();

    /**
     * Gets the program's identifier
     *
     * @return the id
     */
    String getIdShow();

    /**
     * Gets the rating of the program
     *
     * @return the rating
     */
    int getRating();


}
