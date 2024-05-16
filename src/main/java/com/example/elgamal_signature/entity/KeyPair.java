package com.example.elgamal_signature.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KeyPair {
    private PrivateKey privateKey;
    private PublicKey publicKey;
}
