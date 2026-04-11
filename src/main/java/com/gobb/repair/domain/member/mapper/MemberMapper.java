package com.gobb.repair.domain.member.mapper;

import com.gobb.repair.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 로그인용 단건 조회
    Member findByUserId(String userId);

    // Refresh Token 업데이트
    int updateRefreshToken(Member member);

    // Refresh Token으로 조회
    Member findByRefreshToken(String refreshToken);

    // 로그인 성공 시 visit, loginDate 업데이트
    int updateLoginInfo(Member member);
}