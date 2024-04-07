# Redis

## 왜 Redis를 공부해야 하는가? 
- 가장 손쉽게 사용할 수 있는 In-memory 저장소
- 높은 성능
- 다양한 활용성
- 현대적인 서버 구조에서 세션 관리나 캐시는 빠질 수 없는 구성 요소


## 학습 목표 
- Redis가 무엇인지 특성 중심으로 설명할 수 있게 된다.
- 세션 스토어, 캐시와 같은 Redis의 주요한 활용 방법을 익히고 구현할 수 있게 된다.
- 클러스터 구성, 백업과 장애 복구 등 실제 Redis 운영에 필요한 지식을 갖춘다.
- Redis를 활용하는 다양한 어플리케이션 아키텍처들을 익힌다.


# Redis 의 정의
- Remote Dictionary Server
  - Key-value store 외 String, Hash, Set, List등 다양한 자료구조를 지원
- Storage 의 성격을 띔 : 데이터 저장소 (데이터 관점)
- Database 의 성격을 띔 : 전통적인 DBMS의 역할을 수행 (영속성 관점) NoSQL
- Middleware 의 성격을 띔 : 어플리케이션이 이용할 수 있는 유용한 기능을 제공하는 소프트웨어
  - SortedSet 같은 것을 활용해서 랭킹 같은 걸 아주 쉽고 빠르게 구현 가능 

## Redis로 할 수 있는 것? 
- 아주 빠른 데이터 저장소로 활용
- 분산된 서버들간의 커뮤니케이션 (동기화, 작업 분할 등)
- 내장된 자료구조를 활용한 기능 구현 

### DB. Database, DBMS?
- 데이터를 읽고 쓸 수 있는 기능을 제공하는 소프트웨어
- 어플리케이션이 데이터 저장을 간단히 처리할 수 있도록 해줌
- **관심사의 분리, 계층화**

### In-memory DB? 
- 데이터를 디스크에 저장하지 않음.
- 휘발성인 RAM에 저장.
- 빠른 속도 

### 빠른 속도와 휘발성의 절충
- 용도에 맞게 DB와 Redis를 사용 (세션 데이)
- 혼합해서 사용(Cache)
- Redis의 영속성 확보(백업 등)

## Key-Value store로서의 Redis

### 데이터 저장소의 구조 
- 프로그램 언어에서의 데이터 구조 (Array, List, Map, ...)

- DB의 데이터 모델 관점에서의 구조 - 이 부분에 대해 얘기해 보는 것!
  - (네트워크 모델, 계층형 모델(Tree), 관계형 모델 , Key-value store ...)

### Key-value store 이란?
- 특정 값을 key로 해서 그와 연관된 데이터를 value로 저장 (Map과 같음!)
- 가장 단순한 데이터 저장 방식
- 단순한 만큼 빠르고 성능이 좋음!

#### Key-value 구조의 장점
- 단순성에서 오는 쉬운 구현과 사용성
- Hash를 이용해 값을 바로 읽으므로 속도가 빠름(추가 연산이 필요 없음)
- 분산 환경에서의 수평적 확장성

#### Key-value 구조의 단점
- Key를 통해서만 값을 읽을 수 있음
- 범위 검색 등의 복잡한 질의가 불가능 (SQL between 같은)

#### Key-value 스토어의 활용
- 언어의 자료구조 (Java의 HashMap 등)
- NoSQL DB (Redis, Riak, AWS DynamoDB)
- 단순한 구조의 데이터로 높은 성능과 확장성이 필요할 때 사용

#### External Heap(외부 메모리) 로서의 Redis
- Application이 장애가 나도 Redis의 데이터는 보존(단기)
- Application이 여러 머신에서 돌아도 같은 데이터를 접근 가능 

### Redis 활용
- 서버를 개발하며 Redis를 사용하지 않는 기업을 찾기가 어려울 정도 
- Session Store
- Cache
- Limit Rater
- Job Queue

