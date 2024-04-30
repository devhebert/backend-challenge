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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static backend.challenge.modules.task.enums.TaskStatus.PROGRESS;
import static org.mockito.Mockito.when;

@RunWith( KikahaRunner.class )
public class UpdateTaskServiceTest {

	private IUpdateTaskService updateTaskService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		updateTaskService = new UpdateTaskService(taskRepository);
	}

	@Test
	public void shouldBeAbleToUpdateTask() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		TaskDTO taskDTO = TaskDTO.create();
		taskDTO.setTitle("Test rename task");
		taskDTO.setDescription("Test rename description");

		Task task = createTask();

		when(taskRepository.index(taskId)).thenReturn(task);

		IUpdateTaskService.InputPort inputPort = new IUpdateTaskService.InputPort(taskId, taskDTO);

		// Act
		IUpdateTaskService.OutputPort outputPort = updateTaskService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskService.OutputPort.Ok;
		assert task.getTitle().equals("Test rename task");
		assert task.getDescription().equals("Test rename description");
	}

	@Test
	public void shouldNotBeAbleToUpdateATaskThatDoesNotExist() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		TaskDTO taskDTO = TaskDTO.create();
		taskDTO.setTitle("Test rename task");
		taskDTO.setDescription("Test rename description");

		when(taskRepository.index(taskId)).thenReturn(null);

		IUpdateTaskService.InputPort inputPort = new IUpdateTaskService.InputPort(taskId, taskDTO);

		// Act
		IUpdateTaskService.OutputPort outputPort = updateTaskService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskService.OutputPort.NotFound;

	}

	@Test
	public void shouldNotBeAbleToUpdateTaskStatusManually() {
		// Arrange
		UUID taskId = UUID.randomUUID();
		TaskDTO taskDTO = TaskDTO.create();
		taskDTO.setTitle("Test rename task");
		taskDTO.setDescription("Test rename description");

		Task task = createTask();

		when(taskRepository.index(taskId)).thenReturn(task);

		IUpdateTaskService.InputPort inputPort = new IUpdateTaskService.InputPort(taskId, taskDTO);

		// Act
		IUpdateTaskService.OutputPort outputPort = updateTaskService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskService.OutputPort.Ok;
		assert task.getTitle().equals("Test rename task");
		assert task.getDescription().equals("Test rename description");
		assert task.getStatus() == PROGRESS;
	}

	private Task createTask() {
		return new Task(UUID.randomUUID(), "Test Task", "Test Description", 0, PROGRESS, null);
	}
}