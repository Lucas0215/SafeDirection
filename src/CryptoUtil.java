import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class CryptoUtil {

	public String getDBPassword() {
		String res = null;
		try {
			InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("license"), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			String s1 = in.readLine();
			in.readLine();
			String s2 = in.readLine();
			String s3 = in.readLine();
			String s4 = in.readLine();
			String s5 = in.readLine();
			if(s1!=null&&s2!=null&&s3!=null&&s4!=null&&s5!=null)
				res = decryptAES256(s1, s2, s3, s4, s5);
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return res;
	}

	public String getHash(String input) {
		String res = null;
		try {
			InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("license"), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			in.readLine();
			String s1 = in.readLine();
			res = safePasswordHash(input, s1);
			return res;
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return res;
	}
/*
	public String encryptAES256(String msg, String key) {
		try {
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[20];
			random.nextBytes(bytes);
			byte[] saltBytes = bytes;

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			AlgorithmParameters params = cipher.getParameters();

			byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

			byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));

			byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
			System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
			System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
			System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length,
					encryptedTextBytes.length);

			return Base64.getEncoder().encodeToString(buffer);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidKeyException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (NoSuchPaddingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidParameterSpecException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (BadPaddingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IllegalBlockSizeException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return "";
	}
*/
	public String decryptAES256(String msg, String key, String inst1, String inst2, String inst3) {
		try {
			Cipher cipher = Cipher.getInstance(inst1);
			ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

			byte[] saltBytes = new byte[20];
			buffer.get(saltBytes, 0, saltBytes.length);
			byte[] ivBytes = new byte[cipher.getBlockSize()];
			buffer.get(ivBytes, 0, ivBytes.length);
			byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
			buffer.get(encryptedTextBytes);

			SecretKeyFactory factory = SecretKeyFactory.getInstance(inst2);
			PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), inst3);

			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

			byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
			return new String(decryptedTextBytes);
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidKeyException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (NoSuchPaddingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (InvalidAlgorithmParameterException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (BadPaddingException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IllegalBlockSizeException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return "";
	}

	public String safePasswordHash(String password, String salt) {
		MessageDigest md = null;
		password = password + salt;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		
		if (md==null) return "";

		StringBuilder builder = new StringBuilder();
		byte[] bytes = md.digest();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}

		return builder.toString();
	}

}
