package Request;

/**
 * Class holding the information from the user to carry out the fill command
 */
public class FillRequest {
    /**
     * username of the user to have ancestors added as a String
     */
    private String username = new String();
    /**
     * number of generations to add as an int
     */
    private int generationNum = 4;

    /**
     * Constructor for FillRequest with generations specified
     * @param username username of the user as a String
     * @param generationNum number of generations to generate as an int
     */
    public FillRequest(String username, int generationNum) {
        this.username = username;
        this.generationNum = generationNum;
    }

    /**
     * Constructor for FillRequest without generations specified
     * @param username username of the user as a String
     */
    public FillRequest(String username) {
        this.username = username;
    }

    /**
     * Return username of the user making the request
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

}
