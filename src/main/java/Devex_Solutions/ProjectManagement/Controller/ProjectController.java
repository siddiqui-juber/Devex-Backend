package Devex_Solutions.ProjectManagement.Controller;


import Devex_Solutions.ProjectManagement.DTO.ProjectRequest;
import Devex_Solutions.ProjectManagement.DTO.UpdateProjectStatusRequest;
import Devex_Solutions.ProjectManagement.Entity.Project;
import Devex_Solutions.ProjectManagement.Service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/projects")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Project Management APIs")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    @Operation(summary = "Create new project")

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Project created successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = Project.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })

    public Project createProject(
            @RequestBody ProjectRequest request
    ) {

        return projectService.createProject(request);
    }

    // GET ALL PROJECT
    @GetMapping
    @Operation(summary = "Get all projects")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Projects fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    public List<Project> getAllProjects() {

        return projectService.getAllProjects();
    }

    //GET PROJECT BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project fetched successfully",
                    content = @Content(
                            schema = @Schema(
                                    implementation = Project.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found"
            )
    })
    public Project getProjectById(
            @PathVariable UUID id
    ) {

        return projectService.getProjectById(id);
    }

    //UPDATE PROJECT
    @PutMapping("/{id}")
    @Operation(summary = "Update project")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found"
            )
    })
    public Project updateProject(
            @PathVariable UUID id,
            @RequestBody ProjectRequest request
    ) {

        return projectService.updateProject(
                id,
                request
        );
    }

    //UPDATE PROJECT STATUS
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update project status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found"
            )
    })
    public Project updateProjectStatus(
            @PathVariable UUID id,
            @RequestBody UpdateProjectStatusRequest request
    ) {

        return projectService.updateProjectStatus(
                id,
                request
        );
    }

    //DELETE PROJECT
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found"
            )
    })
    public void deleteProject(
            @PathVariable UUID id
    ) {

        projectService.deleteProject(id);
    }
}