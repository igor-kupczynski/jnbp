package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.JNbpClient;

/**
 * Creates the implementation of {@link info.kupczynski.jnbp.api.JNbpClient}
 * <p>
 * The implementation is backed by <a href="https://square.github.io/retrofit/">retrofit</a>,
 * <i>a type-safe HTTP client for Android and Java</i>
 */
public class JNbpClientFactory {

    public static JNbpClient create() {
        return new JNbpClientBackedByRetrofit();
    }
}
