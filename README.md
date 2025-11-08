Structure du projet Maven : 

<img width="535" height="780" alt="image" src="https://github.com/user-attachments/assets/71ec206d-9506-446b-bc9a-ee3ee90df38d" />

Serveur SOAP démarré :

<img width="1620" height="627" alt="image" src="https://github.com/user-attachments/assets/aa784332-dd7b-406b-8db8-bbae3ba7f8ef" />

SOAPUI :

<img width="1918" height="452" alt="image" src="https://github.com/user-attachments/assets/34461b51-9eb6-4167-8610-3464fc7f376d" />
<img width="1918" height="555" alt="image" src="https://github.com/user-attachments/assets/bfdb0e81-4ea4-4d3e-9107-b34c291439f9" />



WSDL dans navigateur :
   URL :  http://localhost:8081/services/hello?wsdl

  <img width="1782" height="917" alt="image" src="https://github.com/user-attachments/assets/ceb6046d-f6d4-43fa-aa30-682997778299" />


pom.xml : 

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.acme.cxf</groupId>
    <artifactId>soap-cxf-service</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <!-- JDK (mets 25 si tu compiles en JDK 25) -->
        <java.version>17</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <!-- Versions -->
        <cxf.version>4.0.5</cxf.version>
        <jaxb.api.version>4.0.2</jaxb.api.version>
        <jaxb.runtime.version>4.0.5</jaxb.runtime.version>
        <wss4j.version>3.0.2</wss4j.version>
        <slf4j.version>2.0.13</slf4j.version>
    </properties>

    <dependencies>
        <!-- CXF SOAP JAX-WS -->
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-frontend-jaxws</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <!-- HTTP transport + Jetty embarqué -->
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-transports-http</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-transports-http-jetty</artifactId>
            <version>${cxf.version}</version>
        </dependency>

        <!-- WS-Security (CXF + WSS4J) -->
        <dependency>
            <groupId>org.apache.cxf</groupId>
            <artifactId>cxf-rt-ws-security</artifactId>
            <version>${cxf.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.wss4j</groupId>
            <artifactId>wss4j-ws-security-common</artifactId>
            <version>${wss4j.version}</version>
        </dependency>

        <!-- JAXB Jakarta -->
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>${jaxb.api.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>${jaxb.runtime.version}</version>
        </dependency>

        <!-- JAX-WS + JWS annotations (Jakarta) -->
        <dependency>
            <groupId>jakarta.xml.ws</groupId>
            <artifactId>jakarta.xml.ws-api</artifactId>
            <version>3.0.1</version>
        </dependency>
        <dependency>
            <groupId>jakarta.jws</groupId>
            <artifactId>jakarta.jws-api</artifactId>
            <version>3.0.0</version>
        </dependency>

        <!-- Logging simple (optionnel) -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>${slf4j.version}</version>
            <scope>runtime</scope>
        </dependency>

        <!-- Tests -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>${java.version}</release>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.5.1</version>
            </plugin>

            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>

    <!-- Profils pratiques -->
    <profiles>
        <!-- Server simple (http://localhost:8081/services/hello?wsdl) -->
        <profile>
            <id>server</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>3.5.1</version>
                        <configuration>
                            <mainClass>com.acme.cxf.Server</mainClass>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>

        <!-- Server sécurisé WS-Security UsernameToken (http://localhost:8080/services/hello-secure?wsdl) -->
        <profile>
            <id>secure</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>3.5.1</version>
                        <configuration>
                            <mainClass>com.acme.cxf.SecureServer</mainClass>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>

        <!-- Client demo -->
        <profile>
            <id>client</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>3.5.1</version>
                        <configuration>
                            <mainClass>com.acme.cxf.client.ClientDemo</mainClass>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>
