package com.blocklend.lending.protocol.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
      info =  @Info(
           contact = @Contact(
                   name = "peer_lending",
                   email = "peerlending@gmail.com",
                   url = "/"
           ),
              description = "OpenApi Documentation for PeerLending Protocol",
              title = "OpenApi specification - PeerLending Protocol",
              version = "1.0",
              license = @License(
                      name = "license name",
                      url = "https://some-url.com"
              ),
              termsOfService = "Terms of Service"
        ),
        servers = {
              @Server(
                      description = "local ENV",
                      url = "http://localhost:8080"
              ),
                @Server(
                        description = "prod Env",
                        url = "/"
                )
        }
//        security = {
//                @SecurityRequirement(
//                        name = "bearerAuth"
//                )
//
//        }
)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {
}
