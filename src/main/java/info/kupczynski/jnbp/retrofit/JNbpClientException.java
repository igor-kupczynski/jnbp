package info.kupczynski.jnbp.retrofit;

/**
 * Generic client exception, examine the {@code cause} for more details
 */
public class JNbpClientException extends RuntimeException {

    public JNbpClientException() {
        super();
    }

    public JNbpClientException(String message) {
        super(message);
    }

    public JNbpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public JNbpClientException(Throwable cause) {
        super(cause);
    }

    protected JNbpClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
