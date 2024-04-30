package backend.challenge.modules.task.repositories;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.models.Task;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class TaskRepository implements ITaskRepository {
	private final List<Task> tasks = new ArrayList<>();

	@Override
	public Task index(final UUID taskId) {
		return this.tasks.stream()
				.filter(t -> t.getId().equals(taskId))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Task> show() {
		return new ArrayList<>(this.tasks);
	}

	@Override
	public Task create(final TaskDTO taskDTO) {
		Task newTask = new Task();
		newTask.setTitle(taskDTO.getTitle());
		newTask.setDescription(taskDTO.getDescription());

		this.tasks.add(newTask);

		return newTask;
	}

	@Override
	public Task update(final Task task) {
		tasks.stream()
				.filter(existingTask -> existingTask.getId().equals(task.getId()))
				.findFirst()
				.ifPresent(existingTask -> {
					existingTask.setTitle(task.getTitle());
					existingTask.setDescription(task.getDescription());
				});

		return task;
	}

	@Override
	public void delete(UUID taskId) {
		this.tasks.removeIf(task -> task.getId().equals(taskId));
	}
}
