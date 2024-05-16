package com.example.elgamal_signature.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RequestBodyForVerifySignature {
    private String text;
    private ElGamalSignature elGamalSignature;
    private PublicKey publicKey;
}
