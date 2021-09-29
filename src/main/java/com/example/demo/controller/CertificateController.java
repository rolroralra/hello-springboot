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
    private final CertificateUtil certificateUtil;

    public CertificateController(@Qualifier("CertificateUtil") CertificateUtil certificateUtil) {
        this.certificateUtil = certificateUtil;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getCertificate(
            @RequestBody Map<String, Object> requestBodyMap
            ) throws Exception {

        String host = (String) requestBodyMap.get("host");
        int port = (Integer) (requestBodyMap.getOrDefault("port", 443));

        String certificate = certificateUtil.getCertificate(host, port);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificate);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getCertificate(
            @RequestParam String host, @RequestParam(required = false, defaultValue = "443") int port
    ) throws Exception {

        String certificate = certificateUtil.getCertificate(host, port);

        return ResponseEntity.ok()
//                .contentType(MediaType.TEXT_PLAIN)
                .body(certificate);
    }

    @RequestMapping(path = "/download", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> downloadCertificate(
            @RequestParam String host, @RequestParam(required = false, defaultValue = "443") int port
    ) throws Exception {

        Resource certificateResource = createCertificateResource(host, port);

        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + host + ".crt" + "\"")
                .body(certificateResource);
    }

    @PostMapping(path = "/download", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> downloadCertificate(
            @RequestBody Map<String, Object> requestBodyMap
    ) throws Exception {

        String host = (String) requestBodyMap.get("host");
        int port = (Integer) (requestBodyMap.getOrDefault("port", 443));

        Resource certificateResource = createCertificateResource(host, port);

        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + host + ".crt" + "\"")
                .body(certificateResource);
    }

    private Resource createCertificateResource(String host, int port) throws Exception {
        String certificate = certificateUtil.getCertificate(host, port);

        return new ByteArrayResource(certificate.getBytes(StandardCharsets.UTF_8));
    }
}
