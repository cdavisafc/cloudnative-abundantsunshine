# cloud-native

## Environment

### Kubernetes <= 1.15.11
Reason: **apps/v1beta1**, and **apps/v1beta2** API versions is no longer served. 
Find more details [here](https://kubernetes.io/blog/2019/07/18/api-deprecations-in-1-16/).

### Java <= 8
Reason: **JAXB** is removed in Java 9, 10, 11 and 12. 
Find more details [here](https://www.jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/).
