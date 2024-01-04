# First Spring Boot Project - MyFirst Blog (Second)

## 1. 진행 및 결과물 (2024-01-04 16:00 까지 완료 내용)

**필수 미션**

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/ee8d76fe-1978-4927-a813-369837a50984)

> 1. 완료
> 2. 완료
> 3. 완료

**선택 미션**

> 1. 검색 필터링 및 정렬 - 완료
> 2. 글 본문에 마크다운 에디터 적용 (Toast UI) - 완료
> 3. 토스트 UI 에디터에 이미지 파일 업로드 기능 적용 - X
> 4. 카카오 로그인 - X
> 5. 스벨트 킷으로 구현 - X
> 6. 배포, 도메인 연결, 젠킨스 CI/CD - X
> 7. 배포, 도메인 연결, 쿠버네티스, 깃허브 액션, 무중단 CI/CD - X
> 8. 정산기능 구현 (토스페이먼츠) - X

### 1) Blog List

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/e0634876-3283-4fdc-aa11-f42c1a01632c)

- 우선 전반적으로 다시 깔끔하게 프론트를 적용했습니다.
- 테일윈드 및 부트 스트랩4 등 이용했으며 심심치 않게 폰트 어썸을 이용했습니다.

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/614ffc50-a99b-4b15-8884-b4e3b97090db)

- 전에 구현하지 못했던 검색 기능을 드디어 구현했습니다.
- 첫 번째 프로젝트 때 충분히 적용시킬 수 있었지만, 구현을 시도해보니 html 부분 타임리프 문제 였던 것을 확인할 수 있었습니다.
- 정렬 기능은 요구사항에 나와있는 대로 최신순, 오래된 순, 추천 순, 역 추천순을 구현했습니다.
- 단, 조회순을 구현했어야 했지만 시간 상 제외했던 것 같습니다. (프로젝트 후에 꼭 구현 해보겠습니다 😊)

  ![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/53b3a985-dead-40af-b308-3f85cb58299c)

- 왼쪽 상단 바 부분은 첫 번째 프로젝트와 크게 다를 바 없습니다.
- 'My Account' 부분과 'Liked List' 카테고리를 계획하고 있었지만, Liked List는 시간 상 완료하지 못했습니다...

### 2) My Account (시간 가장 많이 걸린 구간)

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/f4fe3b75-846e-43b9-8fbf-b5297303baee)

- 내 정보를 확인할 수 있는 공간입니다.
- 간단히 회원가입 때 진행했던 Username, Email, 맴버쉽 상태를 확인할 수 있습니다.
- 맴버쉽 상태는 회원가입 땐 무조건 무료 회원으로 만들었으며 내 정보란에서만 맴버쉽 상태 확인 할 수 있게 끔 만들었습니다.

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/a0240c0b-3db8-477d-a229-2e9187026869)

- for-free 클릭 시, SITEUSER 의 ROLE_PAID == false 처리
- Membership 클릭 시, SITEUSER 의 ROLE_PAID == true 처리
- 상태를 변경하고 데이터베이스엔 ROLE_PAID 상태가 잘 변경이 되지만, 상태 변경 후 로그아웃 후 다시 로그인 해야 게시글 제한이 되어 리다이렉트를 로그아웃으로 처리했습니다.

### 3) Create Blog

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/6b37ca10-da9a-4be1-a7ea-5414a72f7479)

- 블로그 생성 부분에 Toast UI를 추가 시켰습니다.
- 본래는 마크 다운만 적용 가능했지만 이번 프로젝트에선 Toast UI를 활용함으로써 블로그 제작에 있어서 다양성을 추가했습니다.
- 사진 추가 기능은 프로젝트 시간 상 완료하지 못했습니다.

### 4) Blog Detail

![image](https://github.com/bukwon/Medium_Mission_BuKwon/assets/148305892/54157b9f-ab17-4f05-9ef2-62ea644d4125)

- 첫 번째 블로그 진행했을 때 프론트가 너무 지저분해 보였기 떄문에 다시 리뉴얼을 진행했습니다.
- 전 프로젝트와 차이점이 있다면 추천 기능, 삭제 기능, Toast UI 추가 등 있겠습니다.

## 2. 아직 수행 못한 기능

### 1) Account Session

  1) 카카오 로그인

### 2) Blog List

  1) 조회 수 정렬 기능
  2) 조회 순 게시물에 명시

### 3) Create Blog

  1) 사진 추가 기능

### 4) Blog Detail

  1) 조회수 확인 가능
  2) 추천 다시 취소

### 5) My Account

  1) 정보 변경 기능
  2) 정산 기능

### 5) etc

  1) 스벨트 킷 적용
  2) 배포, 도메인 연결, 쿠버네티스, 깃허브액션, CI/CD
  3) 정산기능

## 3. 총총

- 프로젝트 기간 : 12/26 13:00 ~ 1/4 16:00
- 프로젝트 명 : MediumProject - 2회 차 (Create My First Blog)
- 새로 배운 기능 :
  > 👉 백엔드 : Membership 정보 변경에 관한 권한 추가 기능 & 변경
  
  > 👉 프론트 : Toast UI
- 아쉬운 점 :
  > 👉 저번 주에 내가 코로나 걸렸기에 한 일주일 정도는 프로젝트 하지 못했던 것 같다. 걸린 건 어쩔 수 없지만, 그것만 아니였다면 구현을
  > 몇 개 더 구현할 수 있었지 않나 싶다. 카카오 로그인은 정말 구현해보고 싶은 기능 중 하나기에 이번 기회에 못했던 것은 너무
  > 아쉬움이 남았다.
  > 그리고 아직 기본기가 부족하다고 느꼈다. 스프링 부트는 끝 없는 공부긴 하지만, 프로젝트 하는 입장으로써 검색 및 gpt의존이 좀 있었지
  > 않았나 아쉬움이 남았다.
- 결론 :
  > 👉 아쉬움이 많았지만 그 만큼 얻어가는 것도 많았던 것 같다. 고민이 길어져 가장 시간이 많이 걸린 부분 중 하나였던 필수미션 2는
  > 스터디 같은 조원 분인 민구님 도움이 있었기에 당일 만에 끝낼 수 있었다. 가장 어려웠던 부분이 지나가자 나머지 기능은 검색만으로
  > 충분히 할 수 있었지만, 필수 기능만 끝내려고 한 것 치곤 기능을 더 구현할 수 있어서 굉장히 만족스럽다. 이번 프로젝트 끝나곤 복습이 필수라 생각했다.
  > 프로젝트가 끝났으니 이제 인프런 킬 때가 된 것 같다.🖋️
