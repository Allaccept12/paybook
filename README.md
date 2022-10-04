# paybook

## API
1. 회원가입 - POST : '/api/member/signup'
2. 로그인 - POST : '/api/member/login'
3. 로그아웃 - POST : '/api/member/logout'
4. 가계부 작성 - POST : '/api/financial-ledger'
5. 가계부 수정 - PATCH : '/api/financial-ledger/{financialLedgerId}'
6. 가계부 삭제 및 복구 - PUT : '/api/financial-ledger/{financialLedgerId}'
7. 가계부 전체 리스트 조회 - GET : '/api/financial-ledger'
8. 가계부 단건 조회 - GET : '/api/financial-ledger/{financialLedgerId}'

## 스택  
- Java 11
- SpringBoot - 2.7.4
- JPA
- Spring Data Jpa
- MySQL 5.7.29
- IntelliJ

## 과제를 진행하면서
- test코드 작성하면서 h2 DB를 사용하는데 'values of types "boolean" and "integer" are not comparable' systex 에러가 계속 발생했고, h2에서 boolean타입을 인식하지 못해 생긴 에러 였습니다. 가계부 삭제를 Flag처리 하는데 컬럼 타입을 boolean값을 사용했고 서버를 실제 띄워서 사용할떄(Mysql) 문제가 없었으나 test를 진행하는데 문제가 계속 발생했습니다. MODE를 Mysql로 설정하여도 문제는 계속 되었습니다. 그래서 Converter를 사용해서 DB에 들어갈때는 String 값으로 DB에서 조회될때는 다시 boolean타입으로 컨버팅하여 문제를 해결했습니다. 
- 가계부를 flag처리를 하게되면 가계부 조회를 할때 Entity의 @Where으로 flag처리된 Entity는 조회가 안되게 하였습니다. 문제는 다시 복구할떄 가계부 Entity를 조회해와서 flag값을 변경 시켜줘야하지만 애초에 조회가 안됬고, @Where를 타지 않게하려면 DB에 쿼리를 직접날리는 Native쿼리를 사용해서 문제를 해결했습니다.
- 로그인과 로그아웃을 진행하는데 Redis를 사용해서 토큰을 관리할까하는 생각을 했지만 토큰 하나때문에 레디스를 얹이기에는 오버다라고 생각하여 Token을 DB에 저장하고, 로그아웃시에는 저장되어있는 토큰을 지우는 형태로 로그아웃을 구현했습니다. 
- 마지막으로 import관리에 신경을 기울였습니다. 당연히 사용되지않는 import는 삭제처리 했고, 해당 파일이 어떤 일을 하는지 import만 보고도 어림짐작 가능하는 것을 목표로 패키지나 클래스 파일배치를 진행했습니다. 그 와중에 커스텀하게 제작한 ExceptiondClass들이 여러 군데에 퍼져있는 것은 현재는 해당 패키지에서만 사용되고 있어 사용되어지는 class파일에 import를 없애는 것을 지향했습니다.
- 도커기반 실행을 구현하지 못한부분이 아쉽습니다. 


### [DDL 파일](https://github.com/Allaccept12/paybook/blob/main/src/main/resources/schema.sql)
### [TEST](https://github.com/Allaccept12/paybook/tree/main/src/test/java/com/example/paybook)


