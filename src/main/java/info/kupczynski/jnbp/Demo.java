package info.kupczynski.jnbp;

import info.kupczynski.jnbp.retrofit.JNbpClient;

import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException {
        JNbpClient client = new JNbpClient();
        System.out.println(client.current("A", "EUR"));
    }
}
