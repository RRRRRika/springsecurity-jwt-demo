package com.rika.demo.controller;

import com.rika.demo.Service.AuthenticationService;
import com.rika.demo.entity.net.Req;
import com.rika.demo.entity.net.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Resp> login(@RequestBody Req req) {
        return ResponseEntity.ok(authenticationService.login(req));
    }

    @PostMapping("/signup")
    public ResponseEntity<Resp> signUp(@RequestBody Req req) {
        return ResponseEntity.ok(authenticationService.register(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Resp> logout() {
        return ResponseEntity.ok(authenticationService.logout());
    }
}
