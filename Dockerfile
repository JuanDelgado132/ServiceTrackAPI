FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1

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