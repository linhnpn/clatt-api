package container.code.function.authentication.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.*;
import container.code.data.dto.*;
import container.code.config.JwtUtils;
import container.code.config.RefreshTokenProvider;
import container.code.data.entity.Account;
import container.code.data.entity.Address;
import container.code.data.entity.District;
import container.code.data.repository.AccountRepository;
import container.code.data.repository.AddressRepository;
import container.code.data.repository.DistrictRepository;
import container.code.function.account.service.filestorage.FileStorage;
import container.code.function.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private final String companyEmail = "clatt@gmail.com";

    @Value("${default.profile.img}")
    private String defaultImage;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private FileStorage fileStorage;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;
    @Autowired
    private AddressRepository addressRepository;
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
                Account account = accountRepository.findByUsername(form.getUsername());
                AccountDTO accountDTO = mapToAccountDTO(account);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login success!", new JwtResponse(accessToken, accountDTO)));
            } else
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
        } catch (Exception e) {
            if (e instanceof DisabledException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked. Please contact " + companyEmail + " for more information", null));
            } else if (e instanceof AccountExpiredException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "The account has expired. Please contact " + companyEmail + " for more information", null));
            } else if (e instanceof AuthenticationException) {
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
                AccountDTO accountDTO = mapToAccountDTO(account);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login success!", new JwtResponse(accessToken, accountDTO)));
            } else
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
        } catch (Exception e) {
            if (e instanceof DisabledException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked. Please contact " + companyEmail + " for more information", null));
            } else if (e instanceof AccountExpiredException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "The account has expired. Please contact " + companyEmail + " for more information", null));
            } else if (e instanceof AuthenticationException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Wrong username or password.", null));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account is not NULL.", null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account is not NULL.", null));
    }

    private District findDistrict(Integer id) {
        District district = districtRepository.findById(id).orElseThrow(() -> new NotFoundException("District not found"));
        return district;
    }

    @Override
    public ResponseEntity<ResponseObject> createAccount(CreateAccountForm form) {
        if (accountRepository.findByUsername(form.getUsername()) == null)
            try {
                Account account = new Account();
                account.setUsername(form.getUsername());
                account.setPassword(passwordEncoder.encode(form.getPassword()));
                account.setEmail(form.getEmail());
                account.setFullname(form.getFullname());
                account.setFcmToken("randomFcm");
                account.setProfilePicture(defaultImage);
                account.setPhone(form.getPhone());
                account.setDateOfBirth(LocalDateTime.parse(form.getDateOfBirth()));
                account.setGender(form.getGender());
                form.setRole(form.getRole().trim());
                if (form.getRole().equals("") || form.getRole() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "There is no role.", null));
                } else {
                    if (!form.getRole().equals("employee")) form.setRole("renter");
                    account.setRole(form.getRole());
                    Account savedAccount = accountRepository.save(account);

                    Address address = new Address();
                    address.setAccountId(savedAccount.getId());
                    address.setDescription(form.getAddressDescription());
                    address.setDistrictId(form.getDistrictId());
                    address.setDistrict(findDistrict(form.getDistrictId()));
                    address.setAccount(savedAccount);
                    addressRepository.save(address);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Create account successfully.", null));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur.", null));
            }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Username is existed.", null));
    }

    @Override
    public ResponseEntity<ResponseObject> updateAccount(MultipartFile file, String description, Integer id) {
        try {
            Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
            String url = "";
            if (file != null) {
                url = fileStorage.uploadFile(file);
                account.setProfilePicture(url);
            }
            if (description != null && !description.isEmpty()) {
                account.setBio(description);
            }
            accountRepository.save(account);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Update account successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Update account failed!", null));
        }
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

    public AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setRole(account.getRole());
        accountDTO.setAmount(account.getAmount());
        accountDTO.setFullname(account.getFullname());
        accountDTO.setDateOfBirth(account.getDateOfBirth());
        accountDTO.setGender(account.getGender());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setBio(account.getBio());
        accountDTO.setProfilePicture(account.getProfilePicture());
        accountDTO.setFcmToken(account.getFcmToken());

        Address address = account.getAddress();
        String location = "";
        location = address.getDescription() + ", " + address.getDistrict().getName() + ", " + address.getDistrict().getProvince().getName();

        accountDTO.setLocation(location);

        return accountDTO;
    }

    public AccountDTO mapToAccountDTO(Map<String, Object> object) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId((Integer) object.get("id"));
        accountDTO.setUsername((String) object.get("username"));
        accountDTO.setRole((String) object.get("role"));
        accountDTO.setAmount((Integer) object.get("amount"));
        accountDTO.setFullname((String) object.get("fullname"));
        accountDTO.setDateOfBirth((LocalDateTime) object.get("date_of_birth"));
        accountDTO.setGender((String) object.get("gender"));
        accountDTO.setPhone((String) object.get("phone"));
        accountDTO.setEmail((String) object.get("email"));
        accountDTO.setBio((String) object.get("bio"));
        accountDTO.setProfilePicture((String) object.get("profilePicture"));
        accountDTO.setFcmToken((String) object.get("fcmToken"));
        accountDTO.setLocation(object.get("address_des") + ", " +
                object.get("districtName") + ", " +
                object.get("province"));
        return accountDTO;
    }
}
