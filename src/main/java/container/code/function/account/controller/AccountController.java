package container.code.function.account.controller;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Account;
import container.code.function.account.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/getAllAccount")
    @PreAuthorize("hasAuthority('admin')")
    public List<Account> getAllAccount() {
        return accountService.getAllAccount();
    }

    @GetMapping("/getAccountById")
    @PreAuthorize("hasAuthority('admin')")
    public Optional<Account> getAccountById(
            @Parameter(description = "Enter account Id") @RequestParam(name = "id") @Min(value = 2, message = "Id must be equal or greater than 1") Integer Id
    ) {
        return accountService.getAccountById(Id);
    }

    @GetMapping("/getAccountByRole")
    @PreAuthorize("hasAuthority('admin')")
    public List<Account> getAccountByRole(
            @Parameter(description = "Enter account role") @RequestParam(name = "role") String role) {
        return accountService.getAccountByRole(role);
    }

    @PutMapping("/banAccount")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseObject> banAccount(
            @RequestParam(name = "account_id") Integer accountId) {
        return accountService.banAccount(accountId);
    }

    @PutMapping("/update-fcm-token")
    @PreAuthorize("hasAnyAuthority('admin', 'employee', 'renter')")
    public ResponseEntity<ResponseObject> updateFcmToken(
            @RequestParam(name = "account_id") Integer accountId,
            @RequestParam(name = "fcmToken") String fcmToken) {
        return accountService.updateFcmToken(accountId, fcmToken);
    }

    @GetMapping("/getAccountsForOrder")
    @PreAuthorize("hasAuthority('renter')")
    public ResponseEntity<ResponseObject> getAccountsForOrder(
            @RequestParam(name = "work_date") LocalDateTime date) {
        return accountService.getAccountsForOrder(date);
    }
}
