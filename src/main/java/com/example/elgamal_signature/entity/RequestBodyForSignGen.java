package com.example.elgamal_signature.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestBodyForSignGen {
    private String text;
    private KeyPair keyPair;
}
