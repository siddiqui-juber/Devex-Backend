package Devex_Solutions.ClientManagement.Controller;


import Devex_Solutions.ClientManagement.DTO.ClientDashboardResponse;
import Devex_Solutions.ClientManagement.DTO.ClientProfileResponse;
import Devex_Solutions.ClientManagement.Service.ClientService;
import Devex_Solutions.ProjectManagement.Entity.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Client APIs")
public class ClientController {

    private final ClientService clientService;



    //GET CLIENT PROFILE
    @GetMapping("/profile")
    @Operation(summary = "Get logged-in client profile")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile fetched successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation =
                                            ClientProfileResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    public ClientProfileResponse getProfile() {

        return clientService.getProfile();
    }

    //GET PROJECT
    @GetMapping("/projects")
    @Operation(summary = "Get logged-in client projects")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Projects fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    public List<Project> getProjects() {

        return clientService.getProjects();
    }

    //GET DASHBOARD
    @GetMapping("/dashboard")
    @Operation(summary = "Get client dashboard analytics")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard data fetched successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation =
                                            ClientDashboardResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    public ClientDashboardResponse getDashboard() {

        return clientService.getDashboard();
    }
}