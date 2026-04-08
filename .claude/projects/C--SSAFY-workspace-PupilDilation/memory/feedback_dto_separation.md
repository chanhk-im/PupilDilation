---
name: DTO 분리가 실제 필요해짐
description: 내부 필드가 동일해도 별도 DTO 유지 — 나중에 필드 변경 시 분리 비용이 더 높음
type: feedback
---

**규칙**: 같은 필드 구조여도 service 메서드마다 별도 DTO 생성 (ClubCreateResponse, FindAllResponseElement 등)

**Why**: 실제 프로젝트 진행 중 "처음에는 동일해서 재사용했다가, 나중에 요구사항 변경으로 필드 추가가 API마다 다르게 필요해져서 결국 분리해야 했던" 경험이 반복됨. 초기에 분리하는 것이 리팩토링 비용 절감.

**How to apply**: DTO 네이밍과 생성은 현 컨벤션 문서대로 진행. 필드가 동일하더라도 각 api/service 메서드마다 별도 생성.