package org.ck.holiday.securityservice;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GenerateKeyPair {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        var keyPair = keyPairGenerator.generateKeyPair();

        byte[] pub = keyPair.getPublic().getEncoded();
        byte[] pri = keyPair.getPrivate().getEncoded();

        PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream("pub.pem")));
        PemObject pemObject = new PemObject("PUBLIC KEY", pub);
        pemWriter.writeObject(pemObject);
        pemWriter.close();

        pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream("pri.pem")));
        pemObject = new PemObject("PRIVATE KEY", pri);
        pemWriter.writeObject(pemObject);
        pemWriter.close();

    }
}
