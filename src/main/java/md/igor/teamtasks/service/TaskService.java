package md.igor.teamtasks.service;

import md.igor.teamtasks.dto.request.CreateTaskRequest;
import md.igor.teamtasks.dto.request.UpdateTaskRequest;
import md.igor.teamtasks.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest task);
    TaskResponse updateTask(Long id, UpdateTaskRequest task);
    List<TaskResponse> getTasks();
    TaskResponse getTask(Long id);
    void deleteTask(Long id);
}
