package com.example.demo.controller;

import com.example.demo.util.CertificateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {
    private CertificateUtil certificateUtil;

    public CertificateController(@Qualifier("CertificateUtil") CertificateUtil certificateUtil) {
        this.certificateUtil = certificateUtil;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getCertificate(
            @RequestBody Map<String, Object> requestBodyMap
            ) throws Exception {

        String host = (String) requestBodyMap.get("host");
        int port = (Integer) (requestBodyMap.get("port"));

        String certificate = certificateUtil.getCertificate(host, port);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificate);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getCertificate(
            @RequestParam String host, @RequestParam int port
    ) throws Exception {

        String certificate = certificateUtil.getCertificate(host, port);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificate);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> downloadCertificate(
            @RequestBody Map<String, Object> requestBodyMap
    ) throws Exception {

        String host = (String) requestBodyMap.get("host");
        int port = (Integer) (requestBodyMap.get("port"));

        String certificate = certificateUtil.getCertificate(host, port);

        ByteArrayResource resource = new ByteArrayResource(certificate.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + host + ".crt" + "\"")
                .body(resource);
    }

    @PostMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
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
