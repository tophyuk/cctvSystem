# cctvSystem
backend : SpringBoot + spring security + Thymeleaf + REST API
<br>
frontend : Javascript + jQuery

## 🖥️ 프로젝트 소개
CCTV 영상 주소 또는 AI 분석 화면 주소를 가지고 와서 실시간 모니터링 역할을 하며 AI분석하는 단말기와 REST API 통신을 통해 데이터를 전달 받습니다.
<br>
사용되는 시스템마다 다른 기준과 방식으로 경보를 발생시킵니다. 화면에는 프론트엔드와 소켓통신을 통해 경보 데이터를 전달 받으면 화면에 팝업을 나타냅니다.
<br>
이외에도 연도/월/시간별 통계 데이터 및 그래프 표출을 하였으며 해당 데이터를 엑셀로 다운로드 할 수 있도록 구현하였습니다.

<br>

## 🕰️ 개발 기간
* 23.05.26일 - 23.07.14일

### 🧑‍🤝‍🧑 맴버구성
 - 팀원1 : 정상혁 - 기능 전체 개발

### ⚙️ 개발 환경
- `Java 17`
- **IDE** : IntelliJ
- **Framework** : Springboot(3.1)
- **Database** : MySQL
- **ORM** : JPA

## 📌 주요 기능
#### 로그인
- 아이디 및 패스워드 확인
- 로그인 시 세션(Session) 생성
#### 회원가입
- 아이디 중복 체크
#### 대시보드 페이지
- 현 카메라별 상황 및 통계 현황 한 눈에 표시
#### CCTV화면 페이지
- CCTV 영상 주소를 통한 영상 호출
- Edge 단말기와 RESRT API 통신을 통해 AI분석 결과가 경보 발생 시에 DB에 데이터 주입
- 해당 밀집도가 특정 위험 레벨에 도달 시에 소켓 통신을 통한 팝업 및 위험 경보 표시
#### 통계 페이지
- 경보 데이터의 연도별/월별/시간별 경보 알림 이력을 그래프화 + 게시판형태 데이터 테이블화
- object별 그래프화
- 통계 데이터 엑셀 다운로드 기능
#### 사용 화면 예시
![screenshot](https://github.com/tophyuk/cctvSystem/assets/89001300/bc478e6f-e267-4afd-b7da-97c8a2bae0b0)

![screenshot2](https://github.com/tophyuk/cctvSystem/assets/89001300/af09604f-8e93-4082-9b2a-86d133def51b)

위 화면은 In/Out 인원을 파악하여 밀집도 계산을 통해 위험경보를 발생시킵니다.
<br>
2023년도 치맥페스티벌에 사용되었던 군중 계수 시스템에 적용되었습니다.
