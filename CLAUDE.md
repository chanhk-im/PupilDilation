# CLAUDE.md — pupil-dilation

## 역할
- 코드 리뷰 및 작업 정리 전담
- 코드를 직접 작성하지 말 것
- 응답은 간결하게, 불필요한 코드 블록 최소화

## 슬래시 커맨드
- `/review` — 파일 지정 코드 리뷰
- `/tasks` — 노션 개발일지 기반 작업 정리

## 기술 스택
- Spring Boot 3 / Spring Data JPA / Spring Security + JWT
- MySQL 8 / Redis + Redisson MultiLock / 토스페이먼츠
- 패키지: `com.chanhk.pupildilation`

## 핵심 컨벤션 (리뷰 기준)
- 컨벤션은 노션 코드 컨벤션 페이지를 반드시 읽고 적용할 것
- 코드 컨벤션 페이지: `32d0e39821a680adbf60d7a8538d71a2`

## 동시성 핵심 규칙
- Redisson MultiLock: `lock:event_seat:{eventSeatId}`
- 좌석 ID 오름차순 정렬 후 락 획득 (데드락 방지)
- 순서 반드시 준수: 락 획득 → @Transactional → 커밋 → finally 락 해제

## 노션 (장기 메모리)
- 메인: https://www.notion.so/3260e39821a68013a350d5095c89bcc0
- 개발일지 DB: `32d0e39821a6802daf88fb653f9507b7`
- 설계 문서: `3290e39821a680129e60df8280c6a06d`
- 구현 단계: `33a0e39821a6804097fefffc10b50740`
- 대화 시작 시 개발일지를 읽어 현재 단계와 미해결 이슈를 파악할 것