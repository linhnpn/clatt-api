package container.code.function.authentication.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import container.code.data.dto.ResponseObject;
import container.code.data.dto.LoginForm;
import container.code.function.authentication.service.AuthService;
import container.code.function.authentication.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<ResponseObject> login(@RequestParam (name="id_token") String token) {
        return authService.loginWithIdToken(token);
    }
}
