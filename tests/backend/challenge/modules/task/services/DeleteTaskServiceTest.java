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
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(KikahaRunner.class)
public class DeleteTaskServiceTest {
	private IDeleteTaskService deleteTaskService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		deleteTaskService = new DeleteTaskService(taskRepository);
	}

	@Test
	public void shouldBeAbleToDeleteTaskById() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", 0, TaskStatus.PROGRESS, null);
		when(taskRepository.index(taskId)).thenReturn(task);
		IDeleteTaskService.InputPort inputPort = new IDeleteTaskService.InputPort(taskId);

		// Act
		IDeleteTaskService.OutputPort outputPort = deleteTaskService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IDeleteTaskService.OutputPort.Ok);
		assertNotNull(task);
	}

	@Test
	public void shouldBeAbleToReturnNotFoundWhenTaskIsNotFound() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		when(taskRepository.index(taskId)).thenReturn(null);
		IDeleteTaskService.InputPort inputPort = new IDeleteTaskService.InputPort(taskId);

		// Act
		IDeleteTaskService.OutputPort outputPort = deleteTaskService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IDeleteTaskService.OutputPort.NotFound);
	}

}