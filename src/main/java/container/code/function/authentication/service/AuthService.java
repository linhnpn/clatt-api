package container.code.function.authentication.service;

import container.code.data.dto.CreateAccountForm;
import container.code.data.dto.ResponseObject;
import container.code.data.dto.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    ResponseEntity<ResponseObject> login(LoginForm form);

    ResponseEntity<ResponseObject> loginWithIdToken(String token);

    ResponseEntity<ResponseObject> createAccount(CreateAccountForm form);

    ResponseEntity<ResponseObject> updateAccount(MultipartFile file, String description, Integer accountId);
}
