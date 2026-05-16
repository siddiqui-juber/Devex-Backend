package Devex_Solutions.ProjectManagement.DTO;


import Devex_Solutions.ProjectManagement.Enums.ProjectStatus;
import lombok.Data;

@Data
public class UpdateProjectStatusRequest {

    private ProjectStatus status;
}