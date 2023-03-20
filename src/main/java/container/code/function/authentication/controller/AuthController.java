package container.code.function.authentication.controller;

import com.google.firebase.auth.FirebaseAuth;
import container.code.data.dto.CreateAccountForm;
import container.code.data.dto.ResponseObject;
import container.code.data.dto.LoginForm;
import container.code.function.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = {"http://localhost:3030", "https://clatt-api.monoinfinity.net",
        "https://cleaning-house-service.vercel.app", "http://localhost:8080"}, allowCredentials = "true")
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

    @PostMapping(value = "/updateAccount", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('admin', 'renter', 'employee')")
    public ResponseEntity<ResponseObject> updateAccount(
            @RequestPart(required = false) MultipartFile file,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer accountId) {
        return authService.updateAccount(file, description, accountId);
    }
}
