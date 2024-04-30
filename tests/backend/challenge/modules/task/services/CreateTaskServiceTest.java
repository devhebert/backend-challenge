package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import backend.challenge.modules.task.repositories.TaskRepository;
import kikaha.core.test.KikahaRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(KikahaRunner.class)
public class CreateTaskServiceTest {

	private ICreateTaskService createTaskService;

	@Before
	public void init() {
		final ITaskRepository taskRepository = new TaskRepository();
		createTaskService = new CreateTaskService(taskRepository);
	}

	@Test
	public void shouldBeAbleToCreateANewTask() {
		// Arrange
		TaskDTO taskDTO = TaskDTO.create();
		taskDTO.setTitle("Test Task");
		ICreateTaskService.InputPort inputPort = new ICreateTaskService.InputPort(taskDTO);

		// Act
		ICreateTaskService.OutputPort outputPort = createTaskService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof ICreateTaskService.OutputPort.Ok);
		Task createdTask = ((ICreateTaskService.OutputPort.Ok) outputPort).getTask();
		assertNotNull(createdTask);
		assertEquals("Test Task", createdTask.getTitle());
		assertEquals(0, createdTask.getProgress());
		assertEquals(TaskStatus.PROGRESS, createdTask.getStatus());
		assertNotNull(createdTask.getId());
	}

	@Test
	public void shouldBeAbleToReturnErrorWhenTaskTitleIsEmpty() {
		// Arrange
		TaskDTO taskDTO = TaskDTO.create();
		ICreateTaskService.InputPort inputPort = new ICreateTaskService.InputPort(taskDTO);

		// Act
		ICreateTaskService.OutputPort outputPort = createTaskService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof ICreateTaskService.OutputPort.Error);
		assertEquals("Title is required", ((ICreateTaskService.OutputPort.Error) outputPort).getMessage());
	}

	@Test
	public void shouldBeAbleToReturnErrorWhenTaskTitleIsNull() {
		// Arrange
		TaskDTO taskDTO = TaskDTO.create();
		taskDTO.setTitle(null);
		ICreateTaskService.InputPort inputPort = new ICreateTaskService.InputPort(taskDTO);

		// Act
		ICreateTaskService.OutputPort outputPort = createTaskService.execute(inputPort);

		// Assert
		assertTrue(outputPort instanceof ICreateTaskService.OutputPort.Error);
		assertEquals("Title is required", ((ICreateTaskService.OutputPort.Error) outputPort).getMessage());
	}
}