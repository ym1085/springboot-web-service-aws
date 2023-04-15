# 스프링부트와 AWS로 혼자 구현하는 웹 서비스

해당 프로젝트는 개인적인 공부를 목적으로 하였기에 소스내에   
불필요한 주석이 다수 존재할 수 있으니 참고 부탁드리겠습니다.

## Tech Spec

- JDK 1.8
- Spring boot 2.1.3.RELEASE 
- H2

## Goal

1. Learning Spring `Data JPA`, `Security`, `OAuth`
2. Learning writing `Test Code` with JUnit
3. Learning AWS Cloud Infra and `CI/CD`

## How to run project?

> Follow the steps below to execute the project

### Running Docker container 

```shell
# Build Dockerfile
docker build -t springboot-webservice-aws:1.0.0 .
```

```shell
# Checking current all docker images
docker images -a
```

```shell
# Running single docker container with id
# Please add the id of the image
docker run -d --name spring-app-server -p 8080:8080 {IMAGE_ID}
```

### H2 Consoloe URL

First, run the spring application. 
And if you enter url below, you can access H2 DB

```
# URL
http://localhost:8080/h2-console
```

## Summary

- [Blog](https://chipped-moat-7da.notion.site/AWS-dd2df40406fd45b4bcf5f09dd693dbe6)

## References

- [Github](https://github.com/jojoldu/freelec-springboot2-webservice)
- [yes24](http://www.yes24.com/Product/Goods/83849117)
