package backend.challenge.modules.task.services;

import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import backend.challenge.modules.task.repositories.TaskRepository;
import kikaha.core.test.KikahaRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(KikahaRunner.class)
public class RetrieveAllTasksServiceTest {

	private IRetrieveAllTasksService retrieveAllTasksService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		retrieveAllTasksService = new RetrieveAllTasksService(taskRepository);
	}

	@Test
	public void shouldBeAbleToListTheTasks() {
		// Arrange
		List<Task> tasks = createTasks();
		IRetrieveAllTasksService.OutputPort outputPort = retrieveAllTasksService.execute();

		// Act
		Mockito.when(taskRepository.show()).thenReturn(tasks);

		// Assert
		assert outputPort instanceof IRetrieveAllTasksService.OutputPort.Ok;
		assert tasks.size() == 3;
		Mockito.verify(taskRepository, Mockito.times(1)).show();
	}

	private List<Task> createTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(UUID.randomUUID(), "Test Task", "Test Description", 0, TaskStatus.PROGRESS, null));
		tasks.add(new Task(UUID.randomUUID(), "Test Task 2", "Test Description 2", 0, TaskStatus.PROGRESS, null));
		tasks.add(new Task(UUID.randomUUID(), "Test Task 3", "Test Description 3", 0, TaskStatus.PROGRESS, null));
		return tasks;
	}

}