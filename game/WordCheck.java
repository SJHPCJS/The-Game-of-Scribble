package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * WordCheck:
 * A tool class to check if a word is correct.
 */
public class WordCheck {

    // Connect to the remote server, send a word, receive and return true or false which means that the word is correct or not
    public static boolean checkWord(String word) throws IOException {
        Socket socket = new Socket("wordcheck.oritention.org.cn",50106);

        OutputStream output = socket.getOutputStream();
        output.write(word.getBytes());

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if(Objects.equals(input.readLine(), "True")) {
            socket.close();
            return true;
        }
        else {
            socket.close();
            return false;
        }


    }
}
