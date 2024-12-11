package com.semi.lynk.function.login.model;

import com.semi.lynk.function.login.model.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpDetails implements UserDetails {
    private LoginDTO loginDTO;

    // 권한 정보 반환 메소드 (잘 모르겠음...)
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        loginDTO.getRole().forEach(role -> authorities.add(() -> role));
//        return authorities;
//    }

    // 기본값
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // 비밀번호 반환 메소드
    @Override
    public String getPassword() {
        return loginDTO.getEmpPwd();
    }

    // 아이디 반환 메소드
    @Override
    public String getUsername() {
        return loginDTO.getEmpNo();
    }

    // 계정 만료 여부 (잘 모르겠음2)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠겨있는 계정 확인 메소드
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 탈퇴 계정 여부 표현 메소드
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 계정 비활성화 여부로 사용자가 사용할 수 없는 상태 (삭제 등)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
