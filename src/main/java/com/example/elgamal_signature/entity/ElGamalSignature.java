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
public class ElGamalSignature {
    private BigInteger s1;
    private BigInteger s2;
}
