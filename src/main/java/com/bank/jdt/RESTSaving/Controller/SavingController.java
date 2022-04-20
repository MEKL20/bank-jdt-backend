package com.bank.jdt.RESTSaving.Controller;

import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Service.SavingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/saving")
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService){
        this.savingService=savingService;
    }

    @GetMapping("/{username}")
    public Saving getSaving(@PathVariable String username){
        return savingService.getSaving(username);
    }

    @PostMapping("/{username}/withdraw")
    public ResponseEntity savingWithdraw(@PathVariable String username, @RequestBody Saving saving){
        return savingService.savingWithdraw(username, saving);
    }

    @PostMapping("/{username}/topup")
    public ResponseEntity savingTopup(@PathVariable String username, @RequestBody Saving saving){
        return savingService.savingTopup(username, saving);
    }

    @PostMapping("/{username}/transfer")
    public ResponseEntity savingTransfer(@PathVariable String username, @RequestBody Saving saving){
        return savingService.savingTransfer(username, saving);
    }
}
