package com.example.elgamal_signature.service;

import com.example.elgamal_signature.entity.ElGamalSignature;
import com.example.elgamal_signature.entity.KeyPair;
import com.example.elgamal_signature.entity.PrivateKey;
import com.example.elgamal_signature.entity.PublicKey;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

@Service
public class SignatureGenerator {
    private static BigInteger PrimeGen(){
        int num = 0;
        Random rand = new Random(); // generate a random number
        num = rand.nextInt((int) pow(10,4)) + 1;

        while (!isPrime(num) || num < (int) pow(10,3)) {
            num = rand.nextInt((int) pow(10,4)) + 1;
        }
        return new BigInteger(String.valueOf(num));
    }
    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3;
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2;
        return inputNum % divisor != 0;
    }
//    static int power(int x, int y, int p)
//    {
//        int res = 1;     // Initialize result
//
//        x = x % p; // Update x if it is more than or
//        // equal to p
//
//        while (y > 0)
//        {
//            // If y is odd, multiply x with result
//            if (y % 2 == 1)
//            {
//                res = (res * x) % p;
//            }
//
//            // y must be even now
//            y = y >> 1; // y = y/2
//            x = (x * x) % p;
//        }
//        return res;
//    }

    // Utility function to store prime factors of a number
    static void findPrimefactors(HashSet<Integer> s, int n)
    {
        // Print the number of 2s that divide n
        while (n % 2 == 0)
        {
            s.add(2);
            n = n / 2;
        }

        // n must be odd at this point. So we can skip
        // one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i = i + 2)
        {
            // While i divides n, print i and divide n
            while (n % i == 0)
            {
                s.add(i);
                n = n / i;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2)
        {
            s.add(n);
        }
    }

    // Function to find smallest primitive root of n
    static int findPrimitive(int n)
    {
        HashSet<Integer> s = new HashSet<Integer>();

        // Check if n is prime or not
        if (isPrime(n) == false)
        {
            return -1;
        }

        // Find value of Euler Totient function of n
        // Since n is a prime number, the value of Euler
        // Totient function is n-1 as there are n-1
        // relatively prime numbers.
        int phi = n - 1;

        // Find prime factors of phi and store in a set
        findPrimefactors(s, phi);

        // Check for every number from 2 to phi
        for (int r = 2; r <= phi; r++)
        {
            // Iterate through all prime factors of phi.
            // and check if we found a power with value 1
            boolean flag = false;
            for (Integer a : s)
            {

                // Check if r^((phi)/primefactors) mod n
                // is 1 or not
                if (power(r, phi / (a), n) == 1)
                {
                    flag = true;
                    break;
                }
            }

            // If there was no power with value 1.
            if (flag == false)
            {
                return r;
            }
        }

        // If no primitive root found
        return -1;
    }
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // Driver code
    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    static List<Integer> findPrimitiveRoots(int n) {
        List<Integer> primitiveRoots = new ArrayList<>();
        int phi = n - 1;
        for (int a = 2; a <= phi; a++) {
            if (gcd(a, phi) == 1) {
                boolean isPrimitive = true;
                for (int i = 2; i < phi; i++) {
                    if (power(a, i, n) == 1) {
                        isPrimitive = false;
                        break;
                    }
                }
                if (isPrimitive)
                    primitiveRoots.add(a);
            }
        }
        return primitiveRoots;
    }
    public static int randomAlpha(List<Integer> list){
        int code = (int) Math.floor(((Math.random() * list.size()) + 1));
        return code - 1;
    }
    public static int randomX(){
        return (int) Math.floor(((Math.random() * 1000) + 1));
    }
    static List<Integer> createK(int n) {
        List<Integer> list = new ArrayList<>();
        BigInteger nBig = new BigInteger(String.valueOf(n-1));
        for (int i = 2; i<n-1; i++){
            BigInteger iBig = new BigInteger(String.valueOf(i));
            if (iBig.gcd(nBig).intValue() == 1){
                list.add(i);
            }
        }
        return list;
    }
    public KeyPair KeyGen() throws NoSuchAlgorithmException {
        BigInteger p = PrimeGen();
        int n = p.intValue();
        List<Integer> primitiveRoots = findPrimitiveRoots(n);
        BigInteger alpha = new BigInteger(String.valueOf(primitiveRoots.get(randomAlpha(primitiveRoots))));
        BigInteger x = new BigInteger(String.valueOf(randomX()));
        BigInteger y = alpha.modPow(x, p);
        PrivateKey privateKey = PrivateKey.builder()
                .x(x)
                .build();
        PublicKey publicKey = PublicKey.builder()
                .alpha(alpha)
                .y(y)
                .p(p).build();
        return KeyPair.builder()
                .privateKey(privateKey)
                .publicKey(publicKey)
                .build();
    }
    public ElGamalSignature SignGen(KeyPair keyPair, String text) throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger(toHexString(getSHA(text)), 16);
        BigInteger p = keyPair.getPublicKey().getP();
        BigInteger alpha = keyPair.getPublicKey().getAlpha();
        BigInteger y = keyPair.getPublicKey().getY();
        BigInteger x = keyPair.getPrivateKey().getX();
        BigInteger k = new BigInteger(String.valueOf(createK(p.intValue()).get(randomAlpha(createK(p.intValue())))));
        BigInteger s1 = alpha.modPow(k, p);
        BigInteger s2 = (k.modInverse(p.subtract(new BigInteger("1")))).multiply(m.subtract(x.multiply(s1))).mod(p.subtract(new BigInteger("1")));
        BigInteger v1 = alpha.modPow(m, p);
        BigInteger v2 = y.pow(s1.intValue()).multiply(s1.pow(s2.intValue())).mod(p);
        return new ElGamalSignature(s1, s2);
    }
    public boolean VerifySignature(PublicKey publicKey, String text, ElGamalSignature elGamalSignature) throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger(toHexString(getSHA(text)), 16);
        BigInteger p = publicKey.getP();
        BigInteger alpha = publicKey.getAlpha();
        BigInteger y = publicKey.getY();
        BigInteger s1 = elGamalSignature.getS1();
        BigInteger s2 = elGamalSignature.getS2();
        BigInteger v1 = alpha.modPow(m, p);
        BigInteger v2 = y.pow(s1.intValue()).multiply(s1.pow(s2.intValue())).mod(p);
        return (v1.compareTo(v2) == 0);
    }
}
