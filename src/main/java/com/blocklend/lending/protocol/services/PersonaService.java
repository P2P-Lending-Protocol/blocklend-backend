package com.blocklend.lending.protocol.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class PersonaService {

    @Value("${persona.templateId}")
    private String templateId;

    @Value("${persona.environmentId}")
    private String environmentId;

    @Value("${persona.redirectUri}")
    private String redirectUri;

    public String createInquiryUrl(String referenceId) {
        return String.format(
                "https://withpersona.com/verify?inquiry-template-id=%s&environment-id=%s&reference-id=%s&redirect-uri=%s",
                templateId, environmentId, referenceId, URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
        );
    }
}
