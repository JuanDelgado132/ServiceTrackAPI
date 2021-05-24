FROM hseeberger/scala-sbt:graalvm-ce-21.1.0-java11_1.5.2_2.13.6

WORKDIR home/play
RUN mkdir project
RUN mkdir app
RUN mkdir conf

COPY .jvmopts home/play
COPY project/build.properties ./project
COPY project/plugins.sbt ./project
RUN sbt update

COPY build.sbt .
RUN sbt update

COPY app ./app
COPY conf ./conf
RUN sbt compile

RUN sbt clean test stage

CMD ./target/universal/stage/bin/servicetrackapi -Dplay.evolutions.db.default.autoApply=true -Dplay.http.secret.key=$SECRET