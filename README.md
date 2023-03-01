# 스프링부트와 AWS로 혼자 구현하는 웹 서비스

해당 프로젝트는 개인적인 공부를 목적으로 하였기에 불필요한 주석이 소스내에  
다수 존재할 수 있습니다.

## 01. 기술 스택

- JDK 1.8
- Spring boot 2.1.3.RELEASE 
- H2

## 02. 학습 목표

1. Learning Spring `Data JPA`, `Security`, `OAuth`
2. Learning writing `Test Code` with JUnit
3. Learning AWS Cloud Infra and `CI/CD`

## 03. 프로젝트 실행 방법

### 03-1. Exec Docker 

```shell
# build Dockerfile
 docker build -t springboot-webservice-aws:$VERSION .
```

```shell
# checking current docker images
docker images -a
```

```shell
$ docker run -d -p 8080:8080 <image id>
59d7adb22a846e6e7f3b2ba928a0103e521b89c3080a1781be953a3ed597d2e6
```

### 03-2. H2

```
# H2 실행
http://localhost:8080/h2-console
```

## 정리 내용

- [Notion](https://chipped-moat-7da.notion.site/AWS-dd2df40406fd45b4bcf5f09dd693dbe6)

## 참고 자료

- [이동욱님 깃헙](https://github.com/jojoldu/freelec-springboot2-webservice)
- [yes24 - book](http://www.yes24.com/Product/Goods/83849117)
