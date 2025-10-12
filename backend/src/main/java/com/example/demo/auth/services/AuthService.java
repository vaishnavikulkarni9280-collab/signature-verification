package com.example.demo.auth.services;

import com.example.demo.auth.models.User;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.security.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Updated register method to handle encrypted signature image
    public String registerUser(String name, String email, String mobile, String department, String year,
                               String password, String category, MultipartFile signatureImage) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "User already exists!";
        }

        User newUser = new User(name, email, mobile, department, year,
                passwordEncoder.encode(password), category);

        // ✅ Encrypt and store the signature image if uploaded
        if (signatureImage != null && !signatureImage.isEmpty()) {
            try {
                byte[] encryptedSignature = EncryptionUtil.encryptBytes(signatureImage.getBytes());
                newUser.setSignatureImage(encryptedSignature);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error uploading signature image!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Encryption error while saving signature!";
            }
        }

        userRepository.save(newUser);
        return "User registered successfully!";
    }

    // ✅ Login logic (same as before)
    public Map<String, String> getUserDetails(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            Map<String, String> userData = new HashMap<>();
            userData.put("email", user.getEmail());
            userData.put("category", user.getCategory());
            return userData;
        }
        return null; // Invalid credentials
    }
}
