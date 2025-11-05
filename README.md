## 📌 프로젝트 개요
📝 프로젝트 소개

이 프로젝트는 소셜 피드 기반 커뮤니티 서비스입니다.
사용자는 회원가입 및 로그인을 통해 서비스를 이용할 수 있으며, 글을 작성하거나 수정/삭제할 수 있고, 댓글과 좋아요, 팔로우 기능을 통해 다른 사용자들과 상호작용할 수 있습니다

## 🚀 주요 기능 (Features)

### 회원 관리

회원가입, 로그인, 로그아웃

사용자 정보 조회 및 수정, 비밀번호 변경, 계정 삭제

### 피드 (게시글)

게시글 등록 / 조회 / 수정 / 삭제

특정 기간 동안 작성된 피드 조회

### 댓글

게시글에 댓글 작성 / 조회 / 수정 / 삭제

### 팔로우

다른 사용자 팔로우 / 팔로우 취소

팔로잉 / 팔로워 조회

팔로우한 사용자의 게시글 모아보기

### 좋아요

게시글 좋아요 토글

댓글 좋아요 토글

좋아요 개수 조회


## ERD

<img width="1146" height="631" alt="image" src="https://github.com/user-attachments/assets/73088e4a-ae73-479a-87a2-2f49fcd5648b" />


## 📌 API 명세서
🧑‍💻 인증 (Auth)

| 엔드포인트     | 메서드  | 설명   | 요청 예시                                                                                                                       | 응답 예시                             | 상태 코드                      |
| --------- | ---- | ---- | --------------------------------------------------------------------------------------------------------------------------- | --------------------------------- | -------------------------- |
| `/signup` | POST | 회원가입 | `{ "userId": "test@example.com", "userName": "홍길동", "birth": "1995-05-21", "password": "Password123!", "nickName": "길동이" }` | `{ "message": "회원가입에 성공하셨습니다." }` | 201 Created / 409 Conflict |
| `/login`  | POST | 로그인  | `{ "userId": "test@example.com", "password": "Password123!" }`                                                              | `{ "message": "로그인에 성공하셨습니다." }`  | 200 OK / 401 Unauthorized  |
| `/logout` | POST | 로그아웃 | 없음                                                                                                                          | `{ "message": "로그아웃 합니다." }`      | 200 OK / 401 Unauthorized  |

📝 피드 (Feeds)

| 엔드포인트              | 메서드    | 설명              | 요청 예시                                  | 응답 예시                                                                                                       | 상태 코드                    |
| ------------------ | ------ | --------------- | -------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ------------------------ |
| `/feeds`           | POST   | 게시글 생성          | `{ "title": "야호1", "content": "야호1" }` | `{ "feedId": 1, "userId": "test@naver.com", "title": "제목", "content": "내용", "message": "게시글 등록에 성공했습니다." }` | 200 OK / 400 Bad Request |
| `/feeds/{pageNum}` | GET    | 게시글 전체 조회 (페이징) | 없음                                     | `{ "posts": [...], "page": 1, "totalPages": 100 }`                                                          | 200 OK / 400 Bad Request |
| `/feeds/{feedId}`  | PUT    | 게시글 수정          | `{ "title": "수정", "content": "수정" }`   | `{ "feedId": 12, "title": "수정된 제목", "content": "수정된 내용" }`                                                  | 200 OK / 400 Bad Request |
| `/feeds/{id}`      | DELETE | 게시글 삭제          | 없음                                     | `{ "message": "게시글이 정상적으로 삭제되었습니다." }`                                                                      | 200 OK / 400 Bad Request |
| `/feedsDate`       | GET    | 특정 기간 피드 조회     | 없음                                     | `[ { "feedId": 1, "title": "제목", "content": "내용" } ]`                                                       | 200 OK / 400 Bad Request |

👤 사용자 (Users)

| 엔드포인트                      | 메서드    | 설명      | 요청 예시                                                                  | 응답 예시                                                                                                    | 상태 코드                    |
| -------------------------- | ------ | ------- | ---------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------ |
| `/users/{id}`              | GET    | 프로필 조회  | 없음                                                                     | `{ "id": 3, "userId": "test@example.com", "username": "홍길동", "nickName": "길동이", "birth": "1995-05-21" }` | 200 OK / 400 Bad Request |
| `/users/{id}`              | PUT    | 사용자 수정  | `{ "userName": "김철수", "nickName": "철수짱", "birth": "1993-11-03" }`      | `{ "id": 3, "userId": "test@example.com", "username": "김철수", "nickName": "철수짱" }`                        | 200 OK / 400 Bad Request |
| `/users/{userId}/password` | PUT    | 비밀번호 수정 | `{ "currentPassword": "Password123!", "newPassword": "NewPassw0rd!" }` | `{ "id": 3, "userId": "test@example.com" }`                                                              | 200 OK / 400 Bad Request |
| `/`                        | DELETE | 사용자 삭제  | `{ "userId": "test@example.com", "password": "NewPassw0rd!" }`         | `{ "message": "계정을 탈퇴하셨습니다." }`                                                                          | 200 OK / 400 Bad Request |

💬 댓글 (Comments)

| 엔드포인트                      | 메서드    | 설명    | 요청 예시                     | 응답 예시                                              | 상태 코드                                             |
| -------------------------- | ------ | ----- | ------------------------- | -------------------------------------------------- | ------------------------------------------------- |
| `/feeds/{feedId}/comments` | POST   | 댓글 작성 | `{ "content": "댓글 내용" }`  | `{ "id": 1, "feedId": 2, "content": "댓글 내용" }`     | 201 Created / 400 Bad Request                     |
| `/feeds/{feedId}/comments` | GET    | 댓글 조회 | 없음                        | `[ { "id": 1, "feedId": 2, "content": "댓글 내용" } ]` | 200 OK / 404 Not Found                            |
| `/comments/{commentId}`    | PATCH  | 댓글 수정 | `{ "content": "수정된 댓글" }` | `{ "id": 1, "feedId": 2, "content": "수정된 댓글" }`    | 200 OK / 400 Bad Request / 403 Forbidden          |
| `/comments/{commentId}`    | DELETE | 댓글 삭제 | 없음                        | 없음                                                 | 204 No Content / 401 Unauthorized / 403 Forbidden |


🔗 팔로우 (Follow)

| 엔드포인트                | 메서드    | 설명              | 요청 예시                    | 응답 예시                                                                | 상태 코드                      |
| -------------------- | ------ | --------------- | ------------------------ | -------------------------------------------------------------------- | -------------------------- |
| `/follows`           | GET    | 전체 팔로잉 조회       | 없음                       | `{ "followers": [ {"id": "test1", "name": "홍길동"} ] }`                | 200 OK / 400 Bad Request   |
| `/follows/followers` | GET    | 나의 팔로워 조회       | `{ "userID": "test12" }` | `{ "followings": [ {"id": "test1", "name": "홍길동"} ] }`               | 200 OK / 400 Bad Request   |
| `/follows/{id}`      | GET    | 팔로잉 단건 조회       | `{ "userID": "test12" }` | `{ "id": "test123", "name": "이영희" }`                                 | 200 OK / 404 Not Found     |
| `/follows/{id}`      | POST   | 팔로잉 추가          | `{ "userID": "test12" }` | `{ "message": "팔로잉 성공적으로 추가되었습니다." }`                                | 201 Created / 409 Conflict |
| `/follows/{id}`      | DELETE | 팔로잉 취소          | `{ "userID": "test12" }` | `{ "message": "팔로잉이 성공적으로 삭제되었습니다." }`                               | 200 OK / 404 Not Found     |
| `/feeds/following`   | GET    | 팔로우한 유저들의 피드 조회 | 없음                       | `[ { "feedId": 2, "userId": "test3@naver.com", "title": "첫 게시물" } ]` | 200 OK / 401 Unauthorized  |

❤️ 좋아요 (Likes)

| 엔드포인트                        | 메서드  | 설명           | 요청 예시 | 응답 예시                                                                      | 상태 코드  |
| ---------------------------- | ---- | ------------ | ----- | -------------------------------------------------------------------------- | ------ |
| `/feeds/{id}/likes`          | POST | 피드 좋아요 (토글)  | 없음    | `{ "userId": 1, "feedId": 1, "createdAt": "2025-08-19T18:36:33.2326146" }` | 200 OK |
| `/feeds/{id}/likes/count`    | GET  | 피드 좋아요 개수 조회 | 없음    | `{ "feedId": 1, "count": 10 }`                                             | 200 OK |
| `/comments/{id}/likes`       | POST | 댓글 좋아요 (토글)  | 없음    | `{ "userId": 1, "commentId": 1 }`                                          | 200 OK |
| `/comments/{id}/likes/count` | GET  | 댓글 좋아요 개수 조회 | 없음    | `{ "commentId": 1, "count": 10 }`                                          | 200 OK |

