package backend.challenge.modules.task.services;

import backend.challenge.modules.task.messages.ErrorMessages;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class DeleteTaskService implements IDeleteTaskService {
	private final ITaskRepository taskRepository;

	@Inject
	public DeleteTaskService(final ITaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public OutputPort execute(InputPort inputPort) {
		try {
			if (!isValidInput(inputPort.getTaskId())) return new OutputPort.Error(ErrorMessages.TASK_ID_IS_REQUIRED);

			Task taskExists = this.taskRepository.index(inputPort.getTaskId());
			if (taskExists == null) return new OutputPort.NotFound((ErrorMessages.TASK_NOT_FOUND));

			this.taskRepository.delete(taskExists.getId());

			return new OutputPort.Ok();
		} catch (Exception e) {
			return new OutputPort.Error(e.getMessage());
		}
	}

	private boolean isValidInput(UUID taskId) {
		return taskId != null;
	}
}
