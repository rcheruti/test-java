
FROM openjdk:8-alpine

COPY ./target/blz-api.jar .

EXPOSE 8080

# Para detectar limites de memória e CPU em OS Linux
ENV java_config=" -XX:MaxMetaspaceSize=70m -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxHeapFreeRatio=2 -XX:MinHeapFreeRatio=1 "

CMD java $java_config -jar blz-api.jar
