package com.kiot.cctvSystem.dto.member;

import com.kiot.cctvSystem.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MemberEditDto {

    private Long id;

    private String memberId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 모두 포함되어야 합니다.")
    private String name;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String telNum;
    @Enumerated(EnumType.STRING)
    private Role role;


    public MemberEditDto(Long id, String memberId, String name, String email, String telNum, Role role) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.telNum = telNum;
        this.role = role;
    }
}
