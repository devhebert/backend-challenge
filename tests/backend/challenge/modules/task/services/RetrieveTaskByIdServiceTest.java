package backend.challenge.modules.task.services;

import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(KikahaRunner.class)
public class RetrieveTaskByIdServiceTest {
	private IRetrieveTaskByIdService retrieveTaskByIdService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
	}

	@Test
	public void shouldBeAbleToListTheTaskById() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", 0, TaskStatus.PROGRESS, null);
		when(taskRepository.index(taskId)).thenReturn(task);
		IRetrieveTaskByIdService.InputPort inputPort = new IRetrieveTaskByIdService.InputPort(taskId);

		// Act
		IRetrieveTaskByIdService.OutputPort outputPort = retrieveTaskByIdService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IRetrieveTaskByIdService.OutputPort.Ok);
		assertNotNull(task);
		assertEquals("Test Task", task.getTitle());
		assertEquals(0, task.getProgress());
		assertEquals(TaskStatus.PROGRESS, task.getStatus());
		assertNotNull(task.getId());
	}

	@Test
	public void shouldBeAbleToReturnNotFoundWhenTaskIsNotFound() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		when(taskRepository.index(taskId)).thenReturn(null);
		IRetrieveTaskByIdService.InputPort inputPort = new IRetrieveTaskByIdService.InputPort(taskId);

		// Act
		IRetrieveTaskByIdService.OutputPort outputPort = retrieveTaskByIdService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IRetrieveTaskByIdService.OutputPort.NotFound);
	}

	@Test
	public void shouldBeAbleToReturnErrorWhenTaskIdIsNull() {
		// Arrange
		IRetrieveTaskByIdService.InputPort inputPort = new IRetrieveTaskByIdService.InputPort(null);

		// Act
		IRetrieveTaskByIdService.OutputPort outputPort = retrieveTaskByIdService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IRetrieveTaskByIdService.OutputPort.Error);
	}

	@Test
	public void shouldBeAbleToReturnErrorWhenInputPortIsNull() {
		// Arrange
		IRetrieveTaskByIdService.InputPort inputPort = null;

		// Act
		IRetrieveTaskByIdService.OutputPort outputPort = retrieveTaskByIdService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof IRetrieveTaskByIdService.OutputPort.Error);
	}
}