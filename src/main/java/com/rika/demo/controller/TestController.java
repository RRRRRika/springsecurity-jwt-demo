package com.rika.demo.controller;

import com.rika.demo.entity.net.Resp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @RequestMapping("/test/test")
    public ResponseEntity<Resp> testRole() {
        return ResponseEntity.ok(new Resp("this url needs authentication.", null));
    }

    @RequestMapping("/test/user")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Resp> testUser() {
        return ResponseEntity.ok(new Resp("this url needs role 'USER' or 'ADMIN'", null));
    }

    @RequestMapping("/test/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Resp> testAdmin() {
        return ResponseEntity.ok(new Resp("this url needs role 'ADMIN'", null));
    }
}
