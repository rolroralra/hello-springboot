package com.example.demo.controller;

import com.example.demo.util.CertificateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {
    private CertificateUtil certificateUtil;

    public CertificateController(@Qualifier("CertificateUtil") CertificateUtil certificateUtil) throws Exception {
        this.certificateUtil = certificateUtil;
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> getCertificate(
            @RequestParam String host, @RequestParam int port
    ) throws Exception {
        String certificate = certificateUtil.getCertificate(host, port);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificate);
    }

    @PostMapping()
    public ResponseEntity<Resource> downloadCertificate(
            @RequestParam String host, @RequestParam int port
    ) throws Exception {
        String certificate = certificateUtil.getCertificate(host, port);
        ByteArrayResource resource = new ByteArrayResource(certificate.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + host + ".crt" + "\"")
                .body(resource);
    }

}
