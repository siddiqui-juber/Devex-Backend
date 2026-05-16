package Devex_Solutions.Admin.Controller;


import Devex_Solutions.Admin.DTO.AdminDashboardResponse;
import Devex_Solutions.Admin.DTO.ClientResponse;
import Devex_Solutions.Admin.Service.AdminService;
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
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Admin Dashboard APIs")
public class AdminController {

    private final AdminService adminService;


    //GET ADMIN DASHBOARD
    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard statistics")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard fetched successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation =
                                            AdminDashboardResponse.class
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
    public AdminDashboardResponse getDashboard() {

        return adminService.getDashboard();
    }

    //GET ALL CLIENTS LIST
    @GetMapping("/clients")
    @Operation(summary = "Get all clients")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clients fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })

    public List<ClientResponse> getAllClients() {

        return adminService.getAllClients();
    }
}
