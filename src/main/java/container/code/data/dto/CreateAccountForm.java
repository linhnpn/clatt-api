package container.code.data.dto;

import lombok.Data;

@Data
public class CreateAccountForm {
    private String username;
    private String password;
    private String role;
    private String dateOfBirth;
    private String email;
    private String fullname;
    private String gender;
    private String phone;
    private String addressDescription;
    private Integer districtId;
}
