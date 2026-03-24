---
name: Feat issue template
about: 새로운 기능 추가
title: "[FEAT]"
labels: feat
assignees: chanhk-im

---

## 작업 목적
모든 엔터티에서 공통으로 사용하는 생성일시/수정일시를 
중복 없이 관리하기 위한 BaseEntity 추상 클래스를 생성한다.

## 구현 내용
- [ ] `BaseEntity` 추상 클래스 생성
  - `createdAt` (LocalDateTime)
  - `updatedAt` (LocalDateTime)
- [ ] `@MappedSuperclass` 적용
- [ ] `@EntityListeners(AuditingEntityListener.class)` 적용
- [ ] `Application` 클래스에 `@EnableJpaAuditing` 추가

## 적용 대상
User, Club, Venue, Event, Seat, EventSeat, Reservation, Payment, Refund

## 참고
- setter 없이 JPA Auditing으로만 값 주입
- 기본 생성자는 `protected`
