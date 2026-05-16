package Devex_Solutions.Admin.Controller;

import Devex_Solutions.Admin.DTO.AdminAnalyticsResponse;
import Devex_Solutions.Admin.Service.AdminAnalyticsService;
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


@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Admin Analytics APIs")
public class AdminAnalyticsController {

    private final AdminAnalyticsService analyticsService;


    //GET ADMIN ANALYTICS
    @GetMapping
    @Operation(summary = "Get admin analytics data")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Analytics fetched successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation =
                                            AdminAnalyticsResponse.class
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
    public AdminAnalyticsResponse getAnalytics() {

        return analyticsService.getAnalytics();
    }
}
