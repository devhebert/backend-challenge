package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.messages.ErrorMessages;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateTaskService implements ICreateTaskService {

	private final ITaskRepository taskRepository;

	@Inject
	public CreateTaskService(final ITaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public OutputPort execute(InputPort inputPort) {
		try {
			if (inputPort.getTaskDTO() == null) return new OutputPort.Error(ErrorMessages.TASK_IS_REQUIRED);

			OutputPort validateInput = this.isValidInput(inputPort);
			if (validateInput instanceof OutputPort.Error) return validateInput;

			Task createdTask = this.taskRepository.create(inputPort.getTaskDTO());

			return new OutputPort.Ok(createdTask);
		} catch (Exception e) {
			return new OutputPort.Error(e.getMessage());
		}
	}

	private OutputPort isValidInput(InputPort inputPort) {
		if (inputPort.getTaskDTO().getTitle() == null || inputPort.getTaskDTO().getTitle().isEmpty() || inputPort.getTaskDTO().getTitle().isBlank() || inputPort.getTaskDTO().getTitle().length() < 4) {
			return new OutputPort.Error(ErrorMessages.TITLE_IS_REQUIRED);
		}

		return null;
	}

}
