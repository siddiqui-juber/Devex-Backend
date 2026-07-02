package Devex_Solutions.Authentication.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentDiscoveryController {

    // 1. Homepage content negotiation and Link headers
    @GetMapping("/")
    public ResponseEntity<String> getHome(HttpServletRequest request,
                                           @RequestHeader(value = "Accept", required = false) String accept) {
        String baseUrl = getBaseUrl(request);
        HttpHeaders headers = new HttpHeaders();
        
        // Add Link headers pointing to metadata
        headers.add("Link", 
            String.format("<%s/.well-known/api-catalog>; rel=\"api-catalog\", <%s/auth.md>; rel=\"describedby\", <%s/.well-known/oauth-protected-resource>; rel=\"describedby\"", 
                          baseUrl, baseUrl, baseUrl));

        if (accept != null && accept.contains("text/markdown")) {
            headers.add("Content-Type", "text/markdown; charset=utf-8");
            headers.add("x-markdown-tokens", "140");
            String markdown = "# Devex Solutions — Backend API Services\n\n" +
                              "Welcome to the Devex Solutions Backend API. This service exposes REST endpoints for modern project management, clients, user authentication, and system metrics.\n\n" +
                              "## Discovery & Authentication\n" +
                              String.format("- API Catalog: [api-catalog](%s/.well-known/api-catalog)\n", baseUrl) +
                              String.format("- OAuth Authorization Server: [oauth-authorization-server](%s/.well-known/oauth-authorization-server)\n", baseUrl) +
                              String.format("- OpenID Connect Configuration: [openid-configuration](%s/.well-known/openid-configuration)\n", baseUrl) +
                              String.format("- OAuth Protected Resource: [oauth-protected-resource](%s/.well-known/oauth-protected-resource)\n", baseUrl) +
                              String.format("- Agent Skills Discovery: [agent-skills](%s/.well-known/agent-skills/index.json)\n", baseUrl) +
                              String.format("- MCP Server Card: [server-card](%s/.well-known/mcp/server-card.json)\n", baseUrl) +
                              String.format("- Auth.md: [auth.md](%s/auth.md)\n", baseUrl);
            return new ResponseEntity<>(markdown, headers, HttpStatus.OK);
        }

        // Return a clean, simple, and modern HTML homepage in case agents/browsers requests HTML
        headers.add("Content-Type", "text/html; charset=utf-8");
        String html = "<!DOCTYPE html>\n" +
                      "<html>\n" +
                      "<head>\n" +
                      "  <meta charset=\"UTF-8\">\n" +
                      "  <title>Devex Solutions Backend</title>\n" +
                      "  <style>body { font-family: sans-serif; padding: 2rem; background: #0f172a; color: #f8fafc; }</style>\n" +
                      "</head>\n" +
                      "<body>\n" +
                      "  <h1>Devex Solutions Backend API</h1>\n" +
                      "  <p>The backend API services are running. Agent discovery is enabled.</p>\n" +
                      "</body>\n" +
                      "</html>";
        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }

    // 2. /auth.md
    @GetMapping(value = "/auth.md", produces = "text/markdown; charset=utf-8")
    public String getAuthMd(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "# auth.md\n\n" +
               "This service supports agentic registration. Follow the steps below.\n\n" +
               "## Step 1 — Discover\n" +
               String.format("Read Protected Resource Metadata at `%s/.well-known/oauth-protected-resource` and OAuth Authorization Server metadata at `%s/.well-known/oauth-authorization-server`.\n\n", baseUrl, baseUrl) +
               "## Step 2 — Pick a method\n" +
               "Supported identity types: anonymous, identity_assertion.\n\n" +
               "## Step 3 — Register\n" +
               "Post to `register_uri` to register.\n";
    }

    // 3. /.well-known/api-catalog
    @GetMapping(value = "/.well-known/api-catalog", produces = "application/linkset+json; charset=utf-8")
    public String getApiCatalog(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"linkset\": [\n" +
               "    {\n" +
               "      \"anchor\": \"" + baseUrl + "/api/v1/\",\n" +
               "      \"service-desc\": [\n" +
               "        {\n" +
               "          \"href\": \"" + baseUrl + "/v3/api-docs\",\n" +
               "          \"type\": \"application/json\"\n" +
               "        }\n" +
               "      ],\n" +
               "      \"service-doc\": [\n" +
               "        {\n" +
               "          \"href\": \"" + baseUrl + "/swagger-ui.html\",\n" +
               "          \"type\": \"text/html\"\n" +
               "        }\n" +
               "      ],\n" +
               "      \"status\": [\n" +
               "        {\n" +
               "          \"href\": \"" + baseUrl + "/api/v1/health\",\n" +
               "          \"type\": \"application/json\"\n" +
               "        }\n" +
               "      ]\n" +
               "    }\n" +
               "  ]\n" +
               "}";
    }

    // 4. /.well-known/oauth-protected-resource
    @GetMapping(value = "/.well-known/oauth-protected-resource", produces = "application/json; charset=utf-8")
    public String getOauthProtectedResource(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"resource\": \"" + baseUrl + "/\",\n" +
               "  \"authorization_servers\": [\"" + baseUrl + "/\"],\n" +
               "  \"scopes_supported\": [\"read\", \"write\"],\n" +
               "  \"bearer_methods_supported\": [\"header\"]\n" +
               "}";
    }

    // 5. /.well-known/oauth-authorization-server
    @GetMapping(value = "/.well-known/oauth-authorization-server", produces = "application/json; charset=utf-8")
    public String getOauthAuthorizationServer(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"issuer\": \"" + baseUrl + "\",\n" +
               "  \"authorization_endpoint\": \"" + baseUrl + "/api/v1/auth/authorize\",\n" +
               "  \"token_endpoint\": \"" + baseUrl + "/api/v1/auth/token\",\n" +
               "  \"jwks_uri\": \"" + baseUrl + "/.well-known/jwks.json\",\n" +
               "  \"grant_types_supported\": [\n" +
               "    \"authorization_code\",\n" +
               "    \"client_credentials\",\n" +
               "    \"urn:ietf:params:oauth:grant-type:jwt-bearer\",\n" +
               "    \"urn:workos:agent-auth:grant-type:claim\"\n" +
               "  ],\n" +
               "  \"response_types_supported\": [\"code\"],\n" +
               "  \"agent_auth\": {\n" +
               "    \"skill\": \"" + baseUrl + "/auth.md\",\n" +
               "    \"register_uri\": \"" + baseUrl + "/api/v1/auth/agent/register\",\n" +
               "    \"claim_uri\": \"" + baseUrl + "/api/v1/auth/agent/claim\",\n" +
               "    \"identity_types_supported\": [\"anonymous\", \"identity_assertion\"],\n" +
               "    \"anonymous\": {\n" +
               "      \"credential_types_supported\": [\"access_token\"]\n" +
               "    },\n" +
               "    \"identity_assertion\": {\n" +
               "      \"assertion_types_supported\": [\"urn:ietf:params:oauth:token-type:id-jag\", \"verified_email\"]\n" +
               "    }\n" +
               "  }\n" +
               "}";
    }

    // 6. /.well-known/openid-configuration
    @GetMapping(value = "/.well-known/openid-configuration", produces = "application/json; charset=utf-8")
    public String getOpenidConfiguration(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"issuer\": \"" + baseUrl + "\",\n" +
               "  \"authorization_endpoint\": \"" + baseUrl + "/api/v1/auth/authorize\",\n" +
               "  \"token_endpoint\": \"" + baseUrl + "/api/v1/auth/token\",\n" +
               "  \"jwks_uri\": \"" + baseUrl + "/.well-known/jwks.json\",\n" +
               "  \"grant_types_supported\": [\n" +
               "    \"authorization_code\",\n" +
               "    \"client_credentials\",\n" +
               "    \"urn:ietf:params:oauth:grant-type:jwt-bearer\",\n" +
               "    \"urn:workos:agent-auth:grant-type:claim\"\n" +
               "  ],\n" +
               "  \"response_types_supported\": [\"code\"],\n" +
               "  \"subject_types_supported\": [\"public\"],\n" +
               "  \"id_token_signing_alg_values_supported\": [\"RS256\"]\n" +
               "}";
    }

    // 7. /.well-known/mcp/server-card.json
    @GetMapping(value = "/.well-known/mcp/server-card.json", produces = "application/json; charset=utf-8")
    public String getMcpServerCard(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"serverInfo\": {\n" +
               "    \"name\": \"Devex Backend MCP Server\",\n" +
               "    \"version\": \"1.0.0\"\n" +
               "  },\n" +
               "  \"endpoint\": \"" + baseUrl + "/api/v1/mcp\",\n" +
               "  \"capabilities\": {\n" +
               "    \"tools\": {},\n" +
               "    \"resources\": {},\n" +
               "    \"prompts\": {}\n" +
               "  }\n" +
               "}";
    }

    // 8. /.well-known/agent-skills/index.json
    @GetMapping(value = "/.well-known/agent-skills/index.json", produces = "application/json; charset=utf-8")
    public String getAgentSkillsIndex(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        return "{\n" +
               "  \"$schema\": \"https://schemas.agentskills.io/discovery/0.2.0/schema.json\",\n" +
               "  \"skills\": [\n" +
               "    {\n" +
               "      \"name\": \"example-skill\",\n" +
               "      \"type\": \"skill-md\",\n" +
               "      \"description\": \"An example agent skill to demonstrate agent capability discovery.\",\n" +
               "      \"url\": \"" + baseUrl + "/skills/example-skill/SKILL.md\",\n" +
               "      \"digest\": \"sha256:8e341edb1af5d564783c7f3167d0e7acb123d01ccb07a87a3f87cfd414c10a4f\"\n" +
               "    }\n" +
               "  ]\n" +
               "}";
    }

    // 9. /skills/example-skill/SKILL.md
    @GetMapping(value = "/skills/example-skill/SKILL.md", produces = "text/markdown; charset=utf-8")
    public String getSkillMd() {
        return "# Example Skill\n\n" +
               "An example agent skill to demonstrate agent capability discovery.\n\n" +
               "## Usage\n" +
               "Agents can perform queries and interact with the API endpoints.\n";
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        if (forwardedProto != null) {
            scheme = forwardedProto;
        }
        
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        if (forwardedHost != null) {
            if (forwardedHost.contains(",")) {
                forwardedHost = forwardedHost.split(",")[0].trim();
            }
            return scheme + "://" + forwardedHost;
        }
        
        String portSuffix = "";
        if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
            portSuffix = ":" + serverPort;
        }
        return scheme + "://" + serverName + portSuffix;
    }
}
