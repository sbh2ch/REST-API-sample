package kr.goci.kosc.account;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by kiost on 2017-05-24.
 */
public class AccountDto {
    @Data
    public static class Create {
        @NotEmpty
        @Size(min = 5)
        private String username;

        @NotEmpty
        @Size(min = 5)
        private String password;
    }

    @Data
    public static class Response {
        private Long id;
        private String username;
        private String fullName;
        private Date joined;
        private Date updated;
    }

    @Data
    public static class Update {
        private String username;
        private String password;
    }
}
