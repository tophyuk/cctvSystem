package com.kiot.cctvSystem.dto.member;

import com.kiot.cctvSystem.domain.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class MemberDto implements UserDetails{

    private Long id;
    @NotBlank(message = "사용자 명은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,12}$", message = "아이디는 특수문자를 제외한 4~12자리여야 합니다.")
    private String memberId;
    private String name;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 모두 포함되어야 합니다.")
    private String password;
    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 모두 포함되어야 합니다.")
    private String password2;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String telNum;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Character deleteYn;

    public MemberDto(Long id, String memberId, String name, String password, String email, String telNum, Role role, Character deleteYn) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.telNum = telNum;
        this.role = role;
        this.deleteYn = deleteYn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getKey()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
