package com.bank.jdt.RESTSaving.Controller;

import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Service.SavingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saving")
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService){
        this.savingService=savingService;
    }

    @GetMapping("/{id}")
    public Saving getSaving(@PathVariable long id){
        return savingService.getSaving(id);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity savingWithdraw(@PathVariable long id, @RequestBody Saving saving){
        return savingService.savingWithdraw(id, saving);
    }

    @PostMapping("/{id}/topup")
    public ResponseEntity savingTopup(@PathVariable long id, @RequestBody Saving saving){
        return savingService.savingTopup(id, saving);
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity savingTransfer(@PathVariable long id, @RequestBody Saving saving){
        return savingService.savingTransfer(id, saving);
    }
}
