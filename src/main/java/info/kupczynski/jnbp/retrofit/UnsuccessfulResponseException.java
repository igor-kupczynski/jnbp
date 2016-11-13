package info.kupczynski.jnbp.retrofit;

/**
 * Indicates an unsuccessful where a success is required
 */
public class UnsuccessfulResponseException extends RuntimeException {

    private final int code;
    private final String response;

    public UnsuccessfulResponseException(int code, String response) {
        super(String.format("Unsuccessful response [code: %d]: %s", code, response));
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}
