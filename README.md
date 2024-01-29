### What?
- Spring Security 와 JWT를 활용한 간단한 REST API
- SpringBoot 3에 맞는 Security 설정
  * 버전 2와 다른 점: Security Filter Chain 설정 시 람다식으로 표현해줘야 함
  * 기존: .csrf().disable() -> 변경: .csrf(csrf -> csrf.disable())
  * .csrf(AbstractHttpConfigurer::disable) <- IntelliJ에선 이렇게 추천해줌
- 응답 객체를 커스텀하여 응답 받는 클라이언트에서 시인성 좋게 구현

### Review
- REST API 구성을 위해 Postman 활용도 많이 늘었음
- 처음에 적용하기 정말 머리에 그려지지가 않았으나, 강의보고 따라치고, 원리를 그려보고, 3개 YouTube 강의를 들어보니 처음보다는 이해가 됨
- 현재 로그인 시 토큰이 주어지만, Refresh Token 발급은 구현되지 않은 상태로 추후 개선 필요
