package com.example.bank.aspect;

import com.example.bank.domain.Customer;
import com.example.bank.domain.ROLE;
import com.example.bank.exception.LoanException;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.validation.CreateLoanRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class SecurityAspect {

    private final CustomerRepository customerRepository;

    @Before("execution(* com.example.bank.controller.LoanController.createLoan(..)) || execution(* com.example.bank.controller.LoanController.listLoan(..))")
    public void checkSecurityForAuthorization(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long customerId = null;
        if (args != null && args[0] instanceof CreateLoanRequestBody createLoanRequestBody) {
            customerId = createLoanRequestBody.customerId();
        } else if (args != null && args[0] instanceof Long id ){
            customerId = id;
        }
        if (customerId == null){
            throw new LoanException("Customer Id not found!");
        }
        if (!isUserAuthorized(Long.valueOf(customerId))) {
            throw new LoanException("User not authorized!");
        }
    }

    private boolean isUserAuthorized(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(ROLE.ADMIN.toString()));
        if (isAdmin){
            log.info("Admin user is logged in.");
            return true;
        }
        User user = (User) authentication.getPrincipal();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new LoanException("Customer not found with ID: " + customerId));
        return user.getUsername().equals(customer.getUser().getUsername());

    }
}
