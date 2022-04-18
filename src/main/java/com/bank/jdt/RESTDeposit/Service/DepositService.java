package com.bank.jdt.RESTDeposit.Service;

import com.bank.jdt.RESTCustomer.Repository.CustomerRepository;
import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Repository.DepositRepository;
import com.bank.jdt.RESTReporting.Service.ReportingService;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DepositService {
    private final DepositRepository depositRepository;
    private final SavingRepository savingRepository;
    private final CustomerRepository customerRepository;
    private final ReportingService reportingService;

    Random random = new Random();
    Timestamp now = new Timestamp(System.currentTimeMillis());
    Timestamp THREE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 3);
    Timestamp SIX_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6);
    Timestamp NINE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 9);
    Timestamp TWELVE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 12);

    public DepositService(DepositRepository depositRepository, SavingRepository savingRepository, CustomerRepository customerRepository, ReportingService reportingService) {
        this.depositRepository = depositRepository;
        this.savingRepository = savingRepository;
        this.customerRepository = customerRepository;
        this.reportingService = reportingService;
    }

    @SneakyThrows
    public ResponseEntity<String> addDeposit(String username, Deposit deposit) {
        long accountDeposit;
        do {
            int number = random.nextInt(999999);
            accountDeposit = Long.parseLong(123 + String.format("%06d", number));
        } while (depositRepository.findDepositByAccountDeposit(accountDeposit).isPresent());

        deposit.setAccountDeposit(accountDeposit);
        deposit.setCreatedAt(now);
        switch (deposit.getPeriod()) {
            case 3:
                deposit.setExpiredAt(THREE_MONTH_LATER);
                break;
            case 6:
                deposit.setExpiredAt(SIX_MONTH_LATER);
                break;
            case 9:
                deposit.setExpiredAt(NINE_MONTH_LATER);
                break;
            case 12:
                deposit.setExpiredAt(TWELVE_MONTH_LATER);
                break;
            default:
                return new ResponseEntity<>("Wrong Periode", HttpStatus.BAD_REQUEST);
        }

        try {
            if (deposit.getBalance() < 10000) {
//                throw new Exception("Minimum Deposit 10,000");
                return new ResponseEntity<>("Minimum Deposit 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving source = savingRepository.findByUsername(username);
            source.setBalance(source.getBalance() - deposit.getBalance());
            if (source.getBalance() < 0) {
//                throw new Exception("Balance From Main Account Is Not Enough");
                return new ResponseEntity<>("Balance From Main Account Is Not Enough", HttpStatus.BAD_REQUEST);
            }

            savingRepository.save(source);
            reportingService.reportingDepositOut(source.getAccountSaving(), deposit.getBalance(), deposit.getAccountDeposit());
//            return depositRepository.save(deposit);
            return new ResponseEntity(depositRepository.save(deposit), HttpStatus.OK);

        } catch (Exception err) {
            return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
        }

    }

    @SneakyThrows
    public ResponseEntity<String> withdrawDeposit(long accountDeposit) {
        Deposit deposit = depositRepository.getDepositByAccountDeposit(accountDeposit);
        Saving source = savingRepository.findByCustomerId(deposit.getCustomer().getId());
        JSONObject jsonObject = new JSONObject();
        switch (deposit.getPeriod()) {
            case 3:
                source.setBalance(source.getBalance() + (deposit.getBalance() * 95 / 100));
                savingRepository.save(source);
                deposit.setBalance(0);
                deposit.setActive(false);
                reportingService.reportingDepositIn(deposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                jsonObject.put("Message", "Success with 5% penalty");
                jsonObject.put("Withdraw", depositRepository.save(deposit));
                return new ResponseEntity(jsonObject, HttpStatus.OK);
            case 6:
                source.setBalance(source.getBalance() + (deposit.getBalance() * 90 / 100));
                savingRepository.save(source);
                deposit.setBalance(0);
                deposit.setActive(false);
                reportingService.reportingDepositIn(deposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                savingRepository.save(source);
                jsonObject.put("Message", "Success with 10% penalty");
                jsonObject.put("Withdraw", depositRepository.save(deposit));
                return new ResponseEntity(jsonObject, HttpStatus.OK);
            case 9:
                source.setBalance(source.getBalance() + (deposit.getBalance() * 85 / 100));
                savingRepository.save(source);
                deposit.setBalance(0);
                deposit.setActive(false);
                reportingService.reportingDepositIn(deposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                jsonObject.put("Message", "Success with 15% penalty");
                jsonObject.put("Withdraw", depositRepository.save(deposit));
                return new ResponseEntity(jsonObject, HttpStatus.OK);
            case 12:
                source.setBalance(source.getBalance() + (deposit.getBalance() * 80 / 100));
                savingRepository.save(source);
                deposit.setBalance(0);
                deposit.setActive(false);
                reportingService.reportingDepositIn(deposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                jsonObject.put("Message", "Success with 20% penalty");
                jsonObject.put("Withdraw", depositRepository.save(deposit));
                return new ResponseEntity(jsonObject, HttpStatus.OK);
            default:
                return new ResponseEntity<>("Disbursement failed", HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Deposit> getDeposit(long accountDeposit) {
        return depositRepository.findDepositByAccountDeposit(accountDeposit);
    }

    public List<Deposit> getDeposits() {
        return depositRepository.findAll();
    }

    public List<Deposit> getDepositsByUsername(String username) {
        long customerId = customerRepository.findByUsername(username).get().getId();
        return depositRepository.findDepositsByCustomerId(customerId);
    }

    public void deleteDeposit(long id) {
        depositRepository.deleteById(id);
    }

    //    Expired Checker Engine
    @Scheduled(cron = "0 * * ? * *")
    public void depositExpirationChecker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Deposit> expiredDeposit = depositRepository.findExpiredDeposit(sdf.format(now));
        for (Deposit newDeposit : expiredDeposit) {
            Saving source = savingRepository.findByCustomerId(newDeposit.getCustomer().getId());
            switch (newDeposit.getPeriod()) {
                case 3:
                    source.setBalance(source.getBalance() + (newDeposit.getBalance() * 105 / 100));
                    savingRepository.save(source);
                    newDeposit.setActive(false);
                    newDeposit.setBalance(0);
                    reportingService.reportingDepositIn(newDeposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                    depositRepository.save(newDeposit);
                    break;
                case 6:
                    source.setBalance(source.getBalance() + (newDeposit.getBalance() * 1010 / 100));
                    savingRepository.save(source);
                    newDeposit.setActive(false);
                    newDeposit.setBalance(0);
                    reportingService.reportingDepositIn(newDeposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                    depositRepository.save(newDeposit);
                    break;
                case 9:
                    source.setBalance(source.getBalance() + (newDeposit.getBalance() * 115 / 100));
                    savingRepository.save(source);
                    newDeposit.setActive(false);
                    newDeposit.setBalance(0);
                    reportingService.reportingDepositIn(newDeposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                    depositRepository.save(newDeposit);
                    break;
                case 12:
                    source.setBalance(source.getBalance() + (newDeposit.getBalance() * 120 / 100));
                    savingRepository.save(source);
                    newDeposit.setActive(false);
                    newDeposit.setBalance(0);
                    reportingService.reportingDepositIn(newDeposit.getAccountDeposit(), source.getBalance(), source.getAccountSaving());
                    depositRepository.save(newDeposit);
                    break;
                default:
                    System.out.println("Disbursement failed");
                    break;
            }
            System.out.println(newDeposit.getPeriod());
        }
    }
}
