package com.example.productmanagementproject.service;

import com.example.productmanagementproject.dto.AdminLoginRequest;
import com.example.productmanagementproject.dto.AdminSignUpRequest;
import com.example.productmanagementproject.entity.Admin;
import com.example.productmanagementproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void signUp(AdminSignUpRequest request) {

        // 1. 이메일 중복 검사
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 관리자 생성 후 저장
        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPassword
        );

        adminRepository.save(admin);
    }

    //로그인
    @Transactional(readOnly = true)
    public Long login(AdminLoginRequest request) {

        // 1. 이메일로 관리자 조회
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // 3. 로그인 성공한 관리자 id 반환
        return admin.getId();
    }
}
