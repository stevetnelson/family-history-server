package Result;

/**
 * Parent class of all Result classes
 */
public class Result {
    /**
     * message result as a String
     */
    private String message;
    /**
     * success or failure of a request as a boolean
     */
    private boolean success;


    /**
     * Constructor of the error
     * @param message message of the error
     * @param success whether the command was successful or not as a boolean
     */
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Returns the message of the error (may be null)
     * @return message as a String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns whether the command was successful or not
     * @return success as a boolean
     */
    public boolean isSuccess() {
        return success;
    }
}
