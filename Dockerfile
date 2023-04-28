FROM openjdk:17

ARG VCS_REF
LABEL maintainer="Sergey Royz <zjor.se@gmail.com>" \
  org.label-schema.vcs-ref=$VCS_REF \
  org.label-schema.vcs-url="git@github.com:zjor/wishlist-bot.git"

EXPOSE 8080

ADD "target/wishlist-bot.jar" "service.jar"

CMD ["sh", "-c", "java --enable-preview -jar service.jar"]