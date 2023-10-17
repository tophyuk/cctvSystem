package com.kiot.cctvSystem.repository;

import com.kiot.cctvSystem.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 테스트

    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByIdAndDeleteYn(Long id, Character deleteYn);

    @Query(value = "SELECT * FROM Member m " +
            "WHERE m.delete_yn = :deleteYn " +
            "AND (:searchType IS NULL OR " +
            "    (:searchType = 'memberId' AND m.member_id LIKE CONCAT('%', :searchKeyword, '%')) OR " +
            "    (:searchType = 'name' AND m.name LIKE CONCAT('%', :searchKeyword, '%')))",
            nativeQuery = true)
    Page<Member> selectAllMembers(@Param("deleteYn") Character deleteYn, @Param("searchType") String searchType,
                                   @Param("searchKeyword") String searchKeyword, Pageable pageable);

    boolean existsByMemberId(String memberId);

}
