package kr.goci.kosc.account;

/**
 * Created by kiost on 2017-05-24.
 */
public class UserDuplicatedException extends RuntimeException {
    private String username;

    public UserDuplicatedException(String username) {
        this.username = username;
    }
}
