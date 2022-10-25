ARG BASE_IMAGE=senzing/senzing-base
FROM ${BASE_IMAGE}

ENV REFRESHED_AT=2022-10-25

LABEL Name="senzing/senzing-listener-example" \
      Maintainer="support@senzing.com" \
      Version="0.0.1"

HEALTHCHECK CMD ["/app/healthcheck.sh"]

# Run as "root" for system installation.

USER root

ARG MAVEN_VERSION=3.8.6

RUN apt-get update \
 && apt-get -y install \
      python3 \
      python3-pip \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# Install packages via PIP.

COPY requirements.txt ./
RUN pip3 install --upgrade pip \
 && pip3 install -r requirements.txt \
 && rm requirements.txt

# Install OpenJDK-11
RUN apt-get update  \
 && apt-get install -y openjdk-11-jre-headless  \
 && apt-get clean;

RUN curl -O -k https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz


# Install packages via apt.

# Copy files from repository.

COPY ./rootfs /

# Make non-root container.

USER 1001

# Runtime execution.

WORKDIR /app
CMD ["java -jar target/hello-world-app-0.1.jar"]
