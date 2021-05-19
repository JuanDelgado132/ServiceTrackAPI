FROM jdelgado5443/jdelgado:open-jdk-11_sbt-1.5.2_scala-2.13.6

WORKDIR home/play

COPY project/build.properties home/play/project
COPY project/plugins.sbt home/play/project
RUN sbt update

COPY build.sbt home/play
RUN sbt update

COPY app /home/play/app
COPY cont home/play/conf
RUN sbt compile

RUN sbt clean test stage

CMD home/play/universal/stage/bin/servicetrackapi