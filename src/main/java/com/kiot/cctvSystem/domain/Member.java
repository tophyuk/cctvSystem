package com.kiot.cctvSystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@Getter
public class Member extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 12)
    private String memberId;

    @Column(length = 10, nullable = false)
    @Size(min = 2, max = 10)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20)
    private String telNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 1)
    @ColumnDefault("'N'")
    private Character deleteYn;

    @Builder
    public Member(long id, String memberId, String name, String password, String email, String telNum, Role role, Character deleteYn) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.telNum = telNum;
        this.role = role;
        this.deleteYn = deleteYn;
    }

    public void updateMember(String name, String email, String telNum, Role role){
        this.name = name;
        this.email = email;
        this.telNum = telNum;
        this.role = role;
    }

    public void deleteMember(Character deleteYn) {
        this.deleteYn = deleteYn;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}
