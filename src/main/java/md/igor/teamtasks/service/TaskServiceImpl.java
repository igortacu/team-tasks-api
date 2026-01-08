package md.igor.teamtasks.service;

import md.igor.teamtasks.dto.request.CreateTaskRequest;
import md.igor.teamtasks.dto.request.UpdateTaskRequest;
import md.igor.teamtasks.dto.response.TaskResponse;
import md.igor.teamtasks.entity.Task;
import md.igor.teamtasks.entity.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskServiceImpl implements TaskService {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    @Override
    public TaskResponse createTask (CreateTaskRequest request) {
        Long id = idSeq.getAndIncrement();
        Instant now = Instant.now();
        Task task = new Task();
        task.setId(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        task.setStatus(TaskStatus.Todo);

        tasks.put(id, task);
        return toResponse(task);
    }

    @Override
    public List<TaskResponse> getTasks() {
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : tasks.values()) {responses.add(toResponse(task));}
        return responses;
    }

    @Override
    public TaskResponse getTask(Long id) {
        Task task = tasks.get(id);
        if (task == null) throw new NoSuchElementException("No task with id " + id);
        return toResponse(task);
    }

    @Override
    public TaskResponse updateTask(Long id, UpdateTaskRequest taskRequest){
        Task task = tasks.get(id);
        if (task == null) throw new NoSuchElementException("No task with id " + id);

        if(taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if(taskRequest.getPriority() != null) task.setPriority(taskRequest.getPriority());
        if(taskRequest.getDeadline() != null) task.setDeadline(taskRequest.getDeadline());
        // keep createdAt as is
        task.setUpdatedAt(Instant.now());

        tasks.put(id, task);
        return toResponse(task);

    }

    @Override
    public void deleteTask(Long id) {
        if (tasks.remove(id) == null) throw new NoSuchElementException("Task not found");
    }

    private TaskResponse toResponse(Task t) {
        return new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getPriority(),
                t.getStatus(),
                t.getDeadline(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }
}
