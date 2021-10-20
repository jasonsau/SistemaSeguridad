package com.example.demo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SpringBootApplication
@EnableScheduling
public class SistemaSeguridadApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IOException, WriterException {
		SpringApplication.run(SistemaSeguridadApplication.class, args);

		System.out.println(generateSecretKey());

		String secretKey = "PM23CN6VVDAAL52L364N5SBM6AEDNGZJ";

		System.out.println(getGoogleAuthenticationBarCode(secretKey, "jason.guerra253@gmail.com", "Prueba"));

	}

	public static String  getTotpCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	public static String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}

	public static String getGoogleAuthenticationBarCode(String secretKey,
														String account,
														String issuer) {
		try {
			return "otpauth://totp/" + URLEncoder.encode(issuer + ":" + account, "UTF-8")
					.replace("+", "%20") + "?secret=" + URLEncoder.encode(secretKey, "UTF-8")
					.replace("+", "%20") + "&issuer=" + URLEncoder.encode(issuer, "UTF-8")
					.replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void createQRCode(String barCodeData,
									String filePath,
									int height,
									int width) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(barCodeData,
				BarcodeFormat.QR_CODE,
				width,
				height);

		try(FileOutputStream out = new FileOutputStream(filePath)) {
			MatrixToImageWriter.writeToStream(matrix, "png", out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


}




