package container.code.function.authentication.controller;

import com.google.firebase.auth.FirebaseAuth;
import container.code.data.dto.CreateAccountForm;
import container.code.data.dto.ResponseObject;
import container.code.data.dto.LoginForm;
import container.code.function.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping("/login")
public class AuthController {
    private FirebaseAuth firebaseAuth;
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginForm form) {
        return authService.login(form);
    }

    @PostMapping("/withIdToken")
    public ResponseEntity<ResponseObject> login(@RequestParam(name = "id_token") String token) {
        return authService.loginWithIdToken(token);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<ResponseObject> createAccount(
            @RequestBody CreateAccountForm form) {
        return authService.createAccount(form);
    }
}
