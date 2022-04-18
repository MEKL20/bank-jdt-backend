package com.bank.jdt.RESTCustomer.Service;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Repository.CustomerRepository;
import com.bank.jdt.RESTSaving.Service.SavingService;
import com.bank.jdt.Utils.JwtUtil;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SavingService savingService;

    Date SEVENTEEN_YEARS_AGO = new Timestamp(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 17);

    public CustomerService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, SavingService savingService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.savingService = savingService;
    }

    public ResponseEntity<String> loginCustomer(String username, String password) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                UserDetails userDetails = userDetailsService
                        .loadUserByUsername(username);

                final String jwt = jwtUtil.generateToken(userDetails);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Customer", customer);
                jsonObject.put("Token", jwt);

                return new ResponseEntity<>(jwt, HttpStatus.OK);

            } catch (BadCredentialsException e) {
                return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    public Customer addCustomer(Customer customer) {

        if (customerRepository.findByIdentityCard(customer.getIdentityCard()) != null) {
            throw new Exception("Identity Card " + customer.getIdentityCard() + " has already taken");
        }
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new Exception("Username " + customer.getUsername() + " has already taken");
        }
        if (!Pattern.compile(EMAIL_PATTERN).matcher(customer.getEmail()).matches()) {
            throw new Exception("Email " + customer.getEmail() + " is invalid");
        }
        if (!Pattern.compile(PASSWORD_PATTERN).matcher(customer.getPassword()).matches()) {
            throw new Exception("Password " + customer.getPassword() + " doesn't match");
        }
        if (customer.getDatebirth().after(SEVENTEEN_YEARS_AGO)) {
            throw new Exception("You are too young");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.setCreated_at(new Timestamp(System.currentTimeMillis()));
        customerRepository.save(customer);
        savingService.addSaving(customer);

        return customer;
    }

    @SneakyThrows
    public Customer updateCustomer(String username, Customer customer) {
        if (customerRepository.findByIdentityCard(customer.getIdentityCard()) != null) {
            throw new Exception("Identity Card " + customer.getIdentityCard() + " has already taken");
        }
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new Exception("Username " + customer.getUsername() + " has already taken");
        }
        if (!Pattern.compile(EMAIL_PATTERN).matcher(customer.getEmail()).matches()) {
            throw new Exception("Email " + customer.getEmail() + " is invalid");
        }
        if (!Pattern.compile(PASSWORD_PATTERN).matcher(customer.getPassword()).matches()) {
            throw new Exception("Password " + customer.getPassword() + " doesn't match");
        }
        if (customer.getDatebirth().after(SEVENTEEN_YEARS_AGO)) {
            throw new Exception("You are too young");
        }

        Customer existingCustomer = customerRepository.getByUsername(username);
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        existingCustomer.setName(customer.getName());
        existingCustomer.setPassword(encodedPassword);
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setDatebirth(customer.getDatebirth());
        return customerRepository.save(existingCustomer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }

}
