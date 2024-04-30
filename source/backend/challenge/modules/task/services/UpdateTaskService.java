package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class UpdateTaskService implements IUpdateTaskService {
    private final ITaskRepository taskRepository;

    @Inject
    public UpdateTaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public OutputPort execute(InputPort inputPort) {
        try {
            if (!isValidInput(inputPort.getId(), inputPort.getTask())) return new OutputPort.Error("Invalid input");

            Task taskToUpdate = this.taskRepository.index(inputPort.getId());
            if (taskToUpdate == null) return new OutputPort.NotFound("Task not found");

            this.updateFields(inputPort.getTask(), taskToUpdate);

            return new OutputPort.Ok(taskRepository.update(taskToUpdate));
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }

    private void updateFields(TaskDTO task, Task taskToUpdate) {
        if (task.getTitle() != null && !task.getTitle().isEmpty()) {
            taskToUpdate.setTitle(task.getTitle());
        }
        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            taskToUpdate.setDescription(task.getDescription());
        }
    }

    private boolean isValidInput(UUID id, TaskDTO task) {
        if (id == null || task == null) return false;
        return task.getTitle() != null && !task.getTitle().isEmpty();
    }
}
