# Kafka producer fails with `JsonSerializer`

## Symptom

When attempting to publish telemetry, the application returned an error and
could not create the Kafka producer:

```text
org.apache.kafka.common.KafkaException: Failed to construct kafka producer
```

The root cause at the end of the stack trace was:

```text
java.lang.ClassNotFoundException: com.fasterxml.jackson.databind.JavaType
```

## Cause

The project uses Spring Boot 4, which provides Jackson 3 for HTTP
serialization. Jackson 3 classes use the `tools.jackson` package.

The `JsonSerializer` used by Spring Kafka also supports Jackson 2, whose
classes use the `com.fasterxml.jackson` package. These dependencies are
optional in Spring Kafka and therefore were not automatically added to the
application classpath.

Although the application JAR already contained Jackson 3's
`jackson-databind`, it did not provide the class required by the serializer:

```text
com.fasterxml.jackson.databind.JavaType
```

## Solution

The Jackson 2 dependencies required by the producer were explicitly added to
the `pom.xml` file:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

The `jackson-datatype-jsr310` module is required to serialize Java date and
time types, such as the telemetry `Instant` field.

The versions are not declared directly because they are managed by the Spring
Boot BOM.

## Validation

After the change, the project compiled successfully and the application JAR
contained the required dependencies:

```text
jackson-databind-2.21.7.jar
jackson-datatype-jsr310-2.21.7.jar
```

This compatibility should be reviewed when upgrading Spring Boot or Spring
Kafka. These dependencies should only be removed when the configured
serializer no longer requires classes from the `com.fasterxml.jackson`
package.
