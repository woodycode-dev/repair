# 🔧 Repair - 이륜차 부품 재고·주문 관리 API

> JWT 인증 기반 로그인과 제조사·차종·부품·주문 CRUD를 구현한 REST API 서버입니다.

---

## 🛠 기술 스택

| 구분 | 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.x |
| ORM | MyBatis |
| DB | MySQL 8.0 |
| 인증 | JWT (Access Token + Refresh Token) |
| 문서화 | Swagger (springdoc-openapi) |
| 빌드 | Gradle |

---

## 📌 주요 기능

### 부품 관리
- 부품분류 / 부품 / 부품상세 CRUD
- 부품상세 일괄 등록·수정
- 제조사, 차종, 분류코드별 검색 (키워드 검색 포함)

### 주문·거래 관리
- 주문서 + 주문항목 일괄 등록 (트랜잭션 처리)
- 주문항목 상태 전환 관리
    - 10(주문접수) → 11(주문확정) → 20(출고완료) → 50(판매완료)
    - 20(출고완료) → 60(반품접수) → 61(반품확정) → 62(반품완료)
- 출고 및 반품 시 거래내역 자동 생성

### 인증
- JWT 로그인 / 로그아웃
- Access Token + Refresh Token 방식
- 로그인 이력 저장 (OS, Browser 정보 포함)

### 이력 관리
- 데이터 변경 이력 자동 저장 (등록/수정/삭제)
- 수정 시 변경된 필드만 감지하여 이력 기록
- 파일 로그 + DB 로그 이중 관리

---

## 📂 패키지 구조
```
com.gobb.repair
├── domain
│   ├── item        # 부품 관련
│   ├── trade       # 거래 관련
│   ├── member      # 관리자
│   └── log         # 이력 로그
├── common
│   ├── response    # 공통 응답
│   ├── exception   # 예외 처리
│   └── util        # 유틸
└── config          # 설정
```

---

## 🗄 테이블 구조

### 공통
| 테이블 | 설명 |
|---|---|
| CM_CODE | 공용 코드 관리 |
| CM_DATA_LOG | 데이터 변경 이력 |
| CM_MEMBERSHIP | 관리자 정보 |
| CM_MEMBERSHIP_LOG | 로그인 이력 |

### 부품
| 테이블 | 설명 |
|---|---|
| CM_BIKE_ITEM_CD | 부품분류 |
| CM_BIKE_ITEM | 부품 |
| CM_BIKE_ITEM_DTL | 부품상세 |
| CM_MAKER | 제조사 |
| CM_BIKE | 차종 |

### 거래
| 테이블 | 설명 |
|---|---|
| TR_ORDER | 거래주문서 |
| TR_ORDER_DTL | 거래주문항목 |
| TR_ORDER_LIST | 거래내역 |

### 조직
| 테이블 | 설명 |
|---|---|
| RP_AGENT | 에이전트 |
| RP_CLIENT | 정비업체 |

---

## 🔑 API 목록

### 인증
| Method | URL | 설명 |
|---|---|---|
| POST | /api/auth/login | 로그인 |
| POST | /api/auth/refresh | Access Token 재발급 |
| POST | /api/auth/logout | 로그아웃 |

### 부품
| Method | URL | 설명 |
|---|---|---|
| GET | /api/bike-item-cd | 부품분류 목록 |
| POST | /api/bike-item-cd | 부품분류 등록 |
| PUT | /api/bike-item-cd/{id} | 부품분류 수정 |
| DELETE | /api/bike-item-cd/{id} | 부품분류 삭제 |
| GET | /api/bike-item | 부품 목록 (검색) |
| POST | /api/bike-item | 부품 등록 |
| PUT | /api/bike-item/{id} | 부품 수정 |
| DELETE | /api/bike-item/{id} | 부품 삭제 |
| GET | /api/bike-item/{id}/units | 부품상세 목록 |
| POST | /api/bike-item/{id}/units/batch | 부품상세 일괄 등록 |
| PUT | /api/bike-item/{id}/units/batch | 부품상세 일괄 수정 |
| DELETE | /api/bike-item/{id}/units/{unitId} | 부품상세 삭제 |

### 거래
| Method | URL | 설명 |
|---|---|---|
| GET | /api/trade-order | 주문 목록 |
| POST | /api/trade-order | 주문 등록 (항목 포함) |
| GET | /api/trade-order/{id} | 주문 단건 조회 |
| DELETE | /api/trade-order/{id} | 주문 삭제 |
| GET | /api/trade-order/{id}/detail | 주문항목 목록 |
| GET | /api/trade-order/{id}/detail/{detailId} | 주문항목 단건 조회 |
| PUT | /api/trade-order/{id}/detail/{detailId}/status | 주문항목 상태 변경 |

---

## ⚙️ 실행 방법

### 1. 환경 설정
```bash
# application.yml.example 복사
cp src/main/resources/application.yml.example src/main/resources/application.yml
# application.yml 수정 (DB 접속 정보, JWT secret 등)
```

### 2. MySQL 실행 (Docker)
```bash
docker run -d \
  --name repair_mysql \
  -e MYSQL_ROOT_PASSWORD=root1234 \
  -e MYSQL_DATABASE=repair_db \
  -e MYSQL_USER=repair_user \
  -e MYSQL_PASSWORD=repair1234 \
  -p 3306:3306 \
  mysql:8.0
```

### 3. 서버 실행
```bash
./gradlew bootRun
```

### 4. Swagger 접속
서버 실행 후 아래 URL로 접속
http://localhost:8080/swagger-ui/index.html
> 로그인 후 발급받은 Access Token을 Authorize 버튼에 입력하여 API 테스트 가능

---

## 📝 설계 의도

- **도메인형 패키지 구조** — 비즈니스 단위로 코드를 묶어 유지보수성 향상
- **MyBatis 선택** — SQL 직접 제어가 필요한 ERP 특성상 JPA보다 적합
- **데이터 변경 이력 관리** — 리플렉션 기반으로 변경된 필드만 감지하여 이력 저장
- **트랜잭션 처리** — 주문 등록 시 주문서·주문항목 일괄 처리로 데이터 정합성 보장
- **JWT 인증** — Access Token + Refresh Token 방식으로 보안성과 사용성 균형