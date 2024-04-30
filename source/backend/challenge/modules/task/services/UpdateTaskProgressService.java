package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.messages.ErrorMessages;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UpdateTaskProgressService implements IUpdateTaskProgressService {
    private final ITaskRepository taskRepository;

    @Inject
    public UpdateTaskProgressService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            OutputPort validateInput = this.isValidInput(inputPort);
            if (validateInput instanceof OutputPort.Error) return validateInput;

            Task task = this.taskRepository.index(inputPort.getTaskProgressDTO().getId());
            if (task == null) return new OutputPort.NotFound(ErrorMessages.TASK_NOT_FOUND);

            if (task.getStatus() == TaskStatus.COMPLETE) return new OutputPort.Error(ErrorMessages.TASK_IS_ALREADY_COMPLETE);

            int progressToAdd = inputPort.getTaskProgressDTO().getProgress();
            if (task.getProgress() + progressToAdd < 0) return new OutputPort.Error(ErrorMessages.TASK_PROGRESS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0);

            int newProgress = this.calculateProgress(task, inputPort.getTaskProgressDTO());
            task.setProgress(newProgress);
            this.taskRepository.update(task);

            return new OutputPort.Ok();
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }

    }
    private OutputPort isValidInput(InputPort inputPort) {
        if (inputPort.getTaskProgressDTO() == null) return new OutputPort.Error(ErrorMessages.TASK_PROGRESS_IS_REQUIRED);
        if (inputPort.getTaskProgressDTO().getId() == null) return new OutputPort.Error(ErrorMessages.TASK_ID_IS_REQUIRED);
        if (inputPort.getTaskProgressDTO().getProgress() < 0) return new OutputPort.Error(ErrorMessages.TASK_PROGRESS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0);
        if (inputPort.getTaskProgressDTO().getProgress() > 100) return new OutputPort.Error(ErrorMessages.TASK_PROGRESS_MUST_BE_LESS_THAN_OR_EQUAL_TO_100);

        return null;
    }

    private int calculateProgress(Task task, TaskProgressDTO taskProgressDTO) {
        int progress = task.getProgress() + taskProgressDTO.getProgress();
        progress = Math.min(progress, 100);

        if (progress == 100) {
            task.setStatus(TaskStatus.COMPLETE);
        }

        return progress;
    }
}
