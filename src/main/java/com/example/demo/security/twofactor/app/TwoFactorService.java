package com.example.demo.security.twofactor.app;

import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeRepository;
import com.example.demo.user.UserEmployeeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class TwoFactorService {
    private final UserEmployeeService userEmployeeService;
    private final UserEmployeeRepository userEmployeeRepository;

    public TwoFactorService(UserEmployeeService userEmployeeService,
                            UserEmployeeRepository userEmployeeRepository) {
        this.userEmployeeService = userEmployeeService;
        this.userEmployeeRepository = userEmployeeRepository;
    }

    //Crear una llave secreta para cada usuario para despues generar
    //los codigos otp
    public String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    //Crear el codigo de 6 digitos segun la llave secreta
    public String getTopCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    //Crea informacion para el codigo QR
    public String getGoogleAuthenticationBarCode(String secretKey,
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

    //Crear codigo QR para la leerlo en la app de Google Authenticator
    public BufferedImage createQRCode(String barCodeData,
                                      String filePath,
                                      int height,
                                      int width) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
           bitMatrix = qrCodeWriter.encode(barCodeData, BarcodeFormat.QR_CODE, height, width);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    //Verificacion de codigos de verificacion
    public boolean verificationCode(String secretKey, String codigo) {
        String codigoGenerado = getTopCode(secretKey);
        if(codigoGenerado.equals(codigo)) {
            return true;
        }
        return false;
    }

    public boolean redirectChangePasswordTempory(UserEmployee userEmployee) {
        if(userEmployeeService.verifiedPasswordTemporary(userEmployee)) {
            userEmployeeService.setAuthentication(userEmployeeService.getAuthentication(
                    userEmployee.getUsername(),
                    userEmployee.getPassword(),
                    userEmployeeService.addRole("CHANGE_PASSWORD")
            ));
            return true;
        }else {
            return false;
        }
    }

    public String createBodyEmailConfirmationToken(String token) {
        return "<h1>Codigo de Verificacion</h1>" +
                "<p>Su codigo de es " + token +
                " El codigo expira en 15 minutos";
    }
    public String createBodySmsConfirmationToken(String token) {
        return   ":" + token +
                " El codigo expira en 15 minutos";
    }


    public int updateDoubleApp(boolean value, Long idUser) {
        return userEmployeeRepository.updateDoubleApp(value, idUser);
    }

    public int uddateDoubleAuthenticator(boolean value, Long idUser) {
        return userEmployeeRepository.updateDoubleAuthenticator(value, idUser);
    }

    public int updateDoubleEmail(boolean value, Long idUser) {
        return userEmployeeRepository.updateDoubleEmail(value, idUser);
    }

    public int updateDoubleSms(boolean value, Long idUser) {
        return userEmployeeRepository.updateDoubleSms(value, idUser);
    }

}
