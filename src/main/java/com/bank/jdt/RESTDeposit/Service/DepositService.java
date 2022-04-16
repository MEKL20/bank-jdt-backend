package com.bank.jdt.RESTDeposit.Service;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Repository.DepositRepository;
import lombok.SneakyThrows;
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

    Random random = new Random();
    Timestamp now = new Timestamp(System.currentTimeMillis());
    Timestamp THREE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 3);
    Timestamp SIX_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6);
    Timestamp NINE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 9);
    Timestamp TWELVE_MONTH_LATER = new Timestamp(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 12);

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    @SneakyThrows
    public Deposit addDeposit(Deposit deposit) {
        long accountDeposit = 0;
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
                throw new Exception("WRONG PERIODE");
        }

        return depositRepository.save(deposit);
    }

    public Deposit withdrawDeposit(long id) {
        Deposit deposit = depositRepository.getById(id);
        deposit.setBalance(0);
        deposit.setActive(false);

        return depositRepository.save(deposit);
    }

    public Optional<Deposit> getDeposit(long id) {
        return depositRepository.findById(id);
    }

    public List<Deposit> getDeposits() {
        return depositRepository.findAll();
    }

    public Optional<Deposit> getDepositByCustomerId(long customerId) {
        return depositRepository.findDepositByCustomerId(customerId);
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
            newDeposit.setActive(false);
            newDeposit.setBalance(0);
            depositRepository.save(newDeposit);
            System.out.println(newDeposit.getPeriod());
        }
    }
}
