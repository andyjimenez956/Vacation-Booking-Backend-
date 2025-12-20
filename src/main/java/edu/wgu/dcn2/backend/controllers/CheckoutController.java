package edu.wgu.dcn2.backend.controllers;

import edu.wgu.dcn2.backend.services.CheckoutService;
import edu.wgu.dcn2.backend.services.Purchase;
import edu.wgu.dcn2.backend.services.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@Valid @RequestBody Purchase purchase) {
        PurchaseResponse response = checkoutService.placeOrder(purchase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchase")
    public String purchaseInfo() {
        return "Use POST /api/checkout/purchase to place an order.";
    }

}
