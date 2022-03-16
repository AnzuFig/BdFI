package BdFI;

/**
 * @author Guilherme Ribeiro Figueira (60288) gr.figueira@campus.fct.unl.pt
 * @author Joao Henrique Garcia (60106) jh.garcia@campus.fct.unl.pt
 **/
public enum Gender {

    F("female"), M("male"), O("other"), NP("not-provided");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
