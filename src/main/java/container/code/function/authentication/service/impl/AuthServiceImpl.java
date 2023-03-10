package container.code.function.authentication.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.*;
import container.code.data.dto.TokenRequest;
import container.code.config.JwtUtils;
import container.code.config.RefreshTokenProvider;
import container.code.data.dto.JwtResponse;
import container.code.data.dto.ResponseObject;
import container.code.data.dto.LoginForm;
import container.code.data.dto.UserPrinciple;
import container.code.data.entity.Account;
import container.code.data.repository.AccountRepository;
import container.code.function.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private final String companyEmail = "clatt@gmail.com";

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<ResponseObject> login(LoginForm form) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
                if (authentication != null) {
                    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
                    String accessToken = jwtUtils.createToken(userPrinciple);
                    //String refreshToken = refreshTokenProvider.createRefreshToken(userPrinciple.getEmail()).getToken();
                    String refreshToken = "";
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login success!", new JwtResponse(accessToken, refreshToken)));
                } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
            } catch (Exception e) {
                if (e instanceof DisabledException) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked. Please contact " + companyEmail + " for more information", null));
                } else
                if (e instanceof AccountExpiredException){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "The account has expired. Please contact " + companyEmail + " for more information", null));
                } else
                if (e instanceof AuthenticationException){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
                } else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account is not NULL.", null));

            }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "No username or password.", null));
    }

    @Override
    public ResponseEntity<ResponseObject> loginWithIdToken(String token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TokenRequest tokenRequest = mapper.readValue(token, TokenRequest.class);
            String tokenValue = tokenRequest.getAccessToken();
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenValue);
            String email = decodedToken.getEmail();
            Account account = accountRepository.findByEmail(email);
            if (account != null) {
                UserPrinciple userPrinciple = UserPrinciple.build(account);
                String accessToken = jwtUtils.createToken(userPrinciple);
                //String refreshToken = refreshTokenProvider.createRefreshToken(userPrinciple.getEmail()).getToken();
                String refreshToken = "";
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login success!", new JwtResponse(accessToken, refreshToken)));
            } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
        } catch (Exception e) {
            if (e instanceof DisabledException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked. Please contact " + companyEmail + " for more information", null));
            } else
            if (e instanceof AccountExpiredException){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "The account has expired. Please contact " + companyEmail + " for more information", null));
            } else
            if (e instanceof AuthenticationException){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account is not NULL.", null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account is not NULL.", null));
    }
    public boolean validateEmail(String email) {
        //Regular Expression
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
