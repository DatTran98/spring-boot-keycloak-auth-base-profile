FROM quay.io/keycloak/keycloak:19.0.3 AS builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Configure a database vendor
ENV KC_DB=mysql
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

WORKDIR /opt/keycloak
#COPY keycloak-custome-provider-1.0.jar /opt/keycloak/standalone/deployments/
#COPY logging.cli /opt/jboss/startup-scripts/logging.cli
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN #keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.p12
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:19.0.3
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# change these values to point to a running mysql instance
#get env variables from docker-compose
ENV KC_DB=mysql
#ENV KC_DB_URL=jdbc:mysql://$DB_ADDR:$DB_PORT/$DB_DATABASE
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=keycloak


ENV KC_DB_URL=jdbc:mysql://mysql-keycloak:3306/keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_PASSWORD=keycloak
ENV KC_ADMIN=admin
ENV KC_ADMIN_PASSWORD=admin
ENV KC_USER=admin
ENV KC_USER_PASSWORD=admin
# environments of keyStore file
ENV KC_HTTPS_KEY_STORE_FILE=/opt/keycloak/conf/server.p12
ENV KC_HTTPS_KEY_STORE_TYPE=PKCS12

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]