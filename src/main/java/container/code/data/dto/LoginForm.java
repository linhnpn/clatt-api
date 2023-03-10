package container.code.data.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
@Data
public class LoginForm {
    private String username;

    private String password;

}
