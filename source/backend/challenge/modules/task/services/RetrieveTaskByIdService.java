package backend.challenge.modules.task.services;

import backend.challenge.modules.task.messages.ErrorMessages;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RetrieveTaskByIdService implements IRetrieveTaskByIdService {
    private final ITaskRepository taskRepository;

    @Inject
    public RetrieveTaskByIdService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            OutputPort validateInput = this.isValidInput(inputPort);
            if (validateInput instanceof OutputPort.Error) return validateInput;

            Task task = this.taskRepository.index(inputPort.getTaskId());
            if (task == null) return new OutputPort.NotFound(ErrorMessages.TASK_NOT_FOUND);

           return new OutputPort.Ok(task);
        } catch (Exception e) {
            return new OutputPort.Error(e.getMessage());
        }
    }

    private OutputPort isValidInput(InputPort inputPort) {
        if (inputPort.getTaskId() == null) return new OutputPort.Error(ErrorMessages.TASK_ID_IS_REQUIRED);

        return null;
    }

}
