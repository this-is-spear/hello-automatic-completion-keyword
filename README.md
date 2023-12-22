## 개요

책 처럼 `천 만 명의 사용자를 기준으로 설계할 일이 있을지` 고민 했을 때, 적은 사용자 수부터 함께 성장하는 서비스라고 생각하게 됐고, `적은 사용자 수부터 함께 성장 할 수 있는 서비스는 어떻게 만들어야 할지` 고민하게 됐습니다.처음 고민하게 된 시스템 구조는 다음과 같습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/a5a90ad0-5202-40f0-9b4e-9e4c065f28c4)


단일 서버로 구성된 경우 다음과 같은 장단점이 존재하게 됩니다.

- 장점 : 개발 빠름, 단순함
- 단점 : 대부분 영역이 단일실패지점

확장성과 가용성을 고려하면 다음처럼 스케일링이 가능해집니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/cb51ea34-6680-426e-b282-3bf2c245f312)

## 내부 로직

검색어 자동 완성 기능은 데이터 수집 서비스와 데이터 질의 서비스로 나뉘게 됩니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/41f12297-6752-49b9-9f7c-ce117c961bfd)

## 데이터 수집 서비스

데이터 수집 서비스는 검색어의 빈도수를 측정하게 되는데, 빈도수 데이터를 정제해 IO 횟수를 줄이는 게 관건입니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/041c7b90-9d11-40a7-afbd-8e5cba879c62)

## 데이터 질의 서비스

검색 기능 알고리즘을 고민 해 볼 수 있는데 두 가지 정도 고민해봤습니다.

### 이분 탐색을 활용한 방법

이분 탐색을 활용하면 특정 `prefix` 영역을 빠르게 찾을 수 있습니다. 왼쪽 영역과 오른쪽 영역을 찾는데 평균 `logN` 정도가 소요됩니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/aaa4f8d0-5946-42bf-b42e-583fdff9126c)

그러나 문제는 `prefix`를 확인하는 과정입니다. `prefix` 가 길어질 수록 느려진다는 단점이 있습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/5debcd39-d9c0-4c5e-b394-12c740c2e83d)

즉, 저장된 데이터 크기가 `N` 이고 검색어 길이가 `K` 라면 `K * logN` 이라는 시간 복잡도가 발생하게 됩니다.

### 트라이 자료구조

트라이 구조를 활용하게 되면 검색어 길이인 시간복잡도로 데이터를 검색 할 수 있습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/96556b97-f781-477b-85a1-713e41c85f2f)

속도가 빠른 만큼 추가적인 메모리 공간이 필요합니다. 각각의 연결을 포함하는 정보와 끝 지점인지 확인하는 정보를 저장해야 합니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/446486ec-2eec-4db0-929d-dc91343a8038)

### 비교표

저장된 데이터 크기가 `N` 이고 검색어 길이가 `K` 라면 다음과 같은 차이가 발생합니다.

|  | 이분탐색 | 트라이 |
| --- | --- | --- |
| 삽입 시간복잡도 | 1 | K |
| 검색 시간복잡도 | K * log N | K |
| 공간 복잡도 | N | N * K |

### 자료구조 갱신 방법

자동 완성에서 트라이 자료구조는 좋은 선택처럼 보입니다. 그러나 갱신하는 과정에서 조회 성능에 영향을 미칠 수 있는데 두 가지 방법을 다음처럼 비교 할 수 있습니다.

|  | 사용하는 자료구조 갱신 | 새로운 자료구조 생성 후 교체 |
| --- | --- | --- |
| 장점 | - 실시간 변화를 확인 할 수 있다.
- 추가하는 만큼의 공간만 필요하다. | - 조회 성능에 영향을 미치지 않는다. |
| 단점 | - 조회 성능에 영향을 미친다. | - 최신화 속도가 느려진다.
- 두 배의 공간이 필요하다. |

상황에 맞게 적절히 선택하는 게 좋아보입니다.

## 결말

### 제공하려는 방법에 따라 시스템 설계가 금변함을 체감

자료구조를 직접 구현하면서 느낀건 제공하려는 방법에 따라 데이터 관리 방법이 급변하게 변화한다는 것입니다.

### 어깨너머 검색엔진 배우기

이번 기회에 구글과 네이버의 검색 엔진을 엿보게 됐습니다. 구글과 네이버는 각각 120ms, 10ms 내외로 응답 데이터를 전달하고 있었습니다.

### 어깨너머 검색엔진 배우기 - 구글

데이터의 방대함과 글로벌 서비스라는 점, 그리고 전달되는 응답 데이터 양을 봤을 때 120ms 정도 걸리는게 이해가 갔습니다. 아니면 120ms 내외로 정책이 설정되어 있는지 궁금해지기도 했습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/cf67c540-f238-4635-856f-c836dc43a311)

구글의 응답 데이터는 xhr 형식이었습니다. 

```
)]}'
[[
  ["안녕하세요 노래 가사",35,[512,650,39],{...}],
  ["안녕하세요",46,[512,433],{...}],
  ["안녕하세요 저는 귀하의 장치의 운영 체제를 해킹한 전문 프로그래머입니다",0,[512]],
  ["안녕하세요 일본어",0,[512]],
  ["안녕하세요 노래",46,[512,340],{...}],
  ["안녕하세요 영어로",0,[512]],
  ["안녕하세요 짤",0,[512]],
  ["안녕하세요 중국어",0,[512]],
  ["안녕하세요 감사해요 잘있어요 다시만나요",0,[512]],
  ["안녕하세요 저는 트위치에서 방송을 하고 있는 스트리머 케인입니다",0,[512]
]],
...]
```

### 어깨너머 검색엔진 배우기 - 네이버

네이버는 구글보다 5배 빠른 성능을 보이고 있습니다. 빠른 속도를 원하는 한국인의 특성을 잘 활용한 모습을 볼 수 있었습니다. 

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/75658c25-02c0-4a5f-8fbb-6daaf92b1ed9)

그러나 아래 그림처럼 처음 데이터가 조회되는 경우 한 순간 느려지는 상황도 보였습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/cb55d946-b5c6-4bc9-b9a3-39e81d297683)

또한 다시 동일한 요청을 보내면 응답 데이터를 캐싱해서 보다 빠른 속도로 전달하는 모습도 확인했습니다.

![image](https://github.com/this-is-spear/hello-automatic-completion-keyword/assets/92219795/95e1bcdf-2a67-48a9-81d0-83db8e2d50a9)

> 브라우저에 저장되는 건 한 번 요청한 쿼리를 다시 실행하지 않는다는 점에서 확인 가능했습니다.

네이버의 응답 데이터는 json 형식이었습니다.

```
_jsonp_8({
"query" : ["안녕하세요"],
"answer" : [],
"intend" : [],
"items" : [
  [["안녕하세요 일본어", "0"],
  ["안녕하세요 노래", "0"],
  ["안녕하세요 적당히 바람이", "0"],
  ["안녕하세요 베트남어", "0"],
  ["안녕하세요", "0"],
  ["안녕하세요 영화", "0"],
  ["안녕하세요 일본어로", "0"],
  ["안녕하세요 중국어", "0"],
  ["안녕하세요 영어로", "0"],
  ["안녕하세요 러시아어", "0"]]
]
})

```

### 마지막으로

이번 기회에 직접 구현하면서 시간 복잡도를 직접 계산 하며 구조를 파악 할 수 있어서 유익했고, `한글 자동 완성은 어떻게 하지?`, `오타가 발생해도 교정하는 로직은 어떻게 짜지?`, `스펠 단위가 아니라 단어 단위로 자동 완성을 구현 할 수 있지 않을까?`, `트라이 자료구조가 갱신될 때 캐시 데이터는 어떻게 갱신할까?` 등의 고민을 할 수 있어서 굉장히 뜻깊은 시간이었습니다.
