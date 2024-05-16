package com.example.elgamal_signature.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicKey {
    private BigInteger alpha;
    private BigInteger y;
    private BigInteger p;
}
