package backend.challenge.modules.task.services;


import backend.challenge.modules.task.dtos.TaskProgressDTO;
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

import static org.mockito.Mockito.when;

@RunWith( KikahaRunner.class )
public class UpdateTaskProgressServiceTest {
	private IUpdateTaskProgressService updateTaskProgressService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		updateTaskProgressService = new UpdateTaskProgressService(taskRepository);
	}

	@Test
	public void shouldBeAbleToUpdateTaskProgress() {
		// Arrange
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
		taskProgressDTO.setProgress(50);
		taskProgressDTO.setId(UUID.randomUUID());
		Task task = createTask();

		when(taskRepository.index(taskProgressDTO.getId())).thenReturn(task);

		IUpdateTaskProgressService.InputPort inputPort = new IUpdateTaskProgressService.InputPort(taskProgressDTO);

		// Act
		IUpdateTaskProgressService.OutputPort outputPort = updateTaskProgressService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskProgressService.OutputPort.Ok;
		assert task.getProgress() == 50;
	}

	@Test
	public void shouldBeAbleToUpdateOnlyTaskStatusWhenProgressEqualsOneHundred() {
		// Arrange
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
		taskProgressDTO.setProgress(100);
		taskProgressDTO.setId(UUID.randomUUID());
		Task task = createTask();

		when(taskRepository.index(taskProgressDTO.getId())).thenReturn(task);

		IUpdateTaskProgressService.InputPort inputPort = new IUpdateTaskProgressService.InputPort(taskProgressDTO);

		// Act
		IUpdateTaskProgressService.OutputPort outputPort = updateTaskProgressService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskProgressService.OutputPort.Ok;
		assert task.getProgress() == 100;
		assert task.getStatus() == TaskStatus.COMPLETE;
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskProgressWhenProgressLessThanOneHundred() {
		// Arrange
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
		taskProgressDTO.setProgress(-1);
		taskProgressDTO.setId(UUID.randomUUID());
		Task task = createTask();

		when(taskRepository.index(taskProgressDTO.getId())).thenReturn(task);

		IUpdateTaskProgressService.InputPort inputPort = new IUpdateTaskProgressService.InputPort(taskProgressDTO);

		// Act
		IUpdateTaskProgressService.OutputPort outputPort = updateTaskProgressService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskProgressService.OutputPort.Error;
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskProgressWhenProgressGreaterThanOneHundred() {
		// Arrange
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
		taskProgressDTO.setProgress(101);
		taskProgressDTO.setId(UUID.randomUUID());
		Task task = createTask();

		when(taskRepository.index(taskProgressDTO.getId())).thenReturn(task);

		IUpdateTaskProgressService.InputPort inputPort = new IUpdateTaskProgressService.InputPort(taskProgressDTO);

		// Act
		IUpdateTaskProgressService.OutputPort outputPort = updateTaskProgressService.execute(inputPort);

		// Assert
		assert outputPort instanceof IUpdateTaskProgressService.OutputPort.Error;
	}

	private Task createTask() {
		return new Task(UUID.randomUUID(), "Test Task", "Test Description", 0, TaskStatus.PROGRESS, null);
	}

}
