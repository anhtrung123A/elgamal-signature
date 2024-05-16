package com.example.elgamal_signature.controller;

import com.aspose.words.Document;
import com.example.elgamal_signature.entity.ElGamalSignature;
import com.example.elgamal_signature.entity.KeyPair;
import com.example.elgamal_signature.entity.RequestBodyForSignGen;
import com.example.elgamal_signature.entity.RequestBodyForVerifySignature;
import com.example.elgamal_signature.service.SignatureGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Log
@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin
public class Controller {
    private final SignatureGenerator signatureGenerator;
    @GetMapping(path = "/hello")
    public String sayHello(){
        return "Hello world!";
    }
    @GetMapping(path = "/get-key-pair")
    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        return signatureGenerator.KeyGen();
    }
    @PostMapping(path = "/get-signature")
    public ElGamalSignature getSignature(@RequestBody RequestBodyForSignGen requestBodyForSignGen) throws NoSuchAlgorithmException {
        return signatureGenerator.SignGen(requestBodyForSignGen.getKeyPair(), requestBodyForSignGen.getText());
    }
    @PostMapping(path = "/verify-signature")
    public boolean verifySignature(@RequestBody RequestBodyForVerifySignature requestBodyForVerifySignature) throws NoSuchAlgorithmException {
        return signatureGenerator.VerifySignature(requestBodyForVerifySignature.getPublicKey(), requestBodyForVerifySignature.getText(), requestBodyForVerifySignature.getElGamalSignature());
    }
    @GetMapping(path = "/abc")
    public void lol(){
        try {
            Document doc = new Document("C:\\Users\\phamt\\OneDrive\\Desktop\\123.docx");
            log.info(doc.getStyles().get(0).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
