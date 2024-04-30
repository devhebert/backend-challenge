package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.infra.http.views.TaskView;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@Path("tasks")
public class TaskController {
	private final ICreateTaskService createTaskService;
	private final IDeleteTaskService deleteTaskService;
	private final IRetrieveAllTasksService retrieveAllTasksService;
	private final IRetrieveTaskByIdService retrieveTaskByIdService;
	private final IUpdateTaskService updateTaskService;

	@Inject
	public TaskController(
			final ICreateTaskService createTaskService,
			final IDeleteTaskService deleteTaskService,
			final IRetrieveAllTasksService retrieveAllTasksService,
			final IRetrieveTaskByIdService retrieveTaskByIdService,
			final IUpdateTaskService updateTaskService
	) {
		this.createTaskService = createTaskService;
		this.deleteTaskService = deleteTaskService;
		this.retrieveAllTasksService = retrieveAllTasksService;
		this.retrieveTaskByIdService = retrieveTaskByIdService;
		this.updateTaskService = updateTaskService;
	}

	@GET
	public Response show() {
		IRetrieveAllTasksService.OutputPort outputPort = this.retrieveAllTasksService.execute();
		return DefaultResponse.ok().entity(outputPort);
	}

	@GET
	@Path("single/{taskId}")
	public Response index(@PathParam("taskId") String taskId) {
		UUID id = UUID.fromString(taskId);
		IRetrieveTaskByIdService.OutputPort outputPort = this.retrieveTaskByIdService.execute(new IRetrieveTaskByIdService.InputPort(id));

		if (outputPort instanceof IRetrieveTaskByIdService.OutputPort.Ok) {
			IRetrieveTaskByIdService.OutputPort.Ok ok = (IRetrieveTaskByIdService.OutputPort.Ok) outputPort;
			return DefaultResponse.ok().entity(ok);
		}

		if (outputPort instanceof IRetrieveTaskByIdService.OutputPort.NotFound) {
			IRetrieveTaskByIdService.OutputPort.NotFound notFound = (IRetrieveTaskByIdService.OutputPort.NotFound) outputPort;
			return DefaultResponse.notFound().entity(notFound);
		}

		if (outputPort instanceof IRetrieveTaskByIdService.OutputPort.Error) {
			IRetrieveTaskByIdService.OutputPort.Error error = (IRetrieveTaskByIdService.OutputPort.Error) outputPort;
			return DefaultResponse.badRequest().entity(error);
		}

		return DefaultResponse.serverError().entity("An unexpected error occurred");
	}

	@POST
	public Response create(TaskView task) {
		TaskDTO inputPort = new TaskDTO(task.getTitle(), task.getDescription());
		ICreateTaskService.OutputPort outputPort = this.createTaskService.execute(new ICreateTaskService.InputPort(inputPort));

		if (outputPort instanceof ICreateTaskService.OutputPort.Ok) {
			ICreateTaskService.OutputPort.Ok ok = (ICreateTaskService.OutputPort.Ok) outputPort;
			return DefaultResponse.created().entity(ok);
		}

		if (outputPort instanceof ICreateTaskService.OutputPort.Error) {
			ICreateTaskService.OutputPort.Error error = (ICreateTaskService.OutputPort.Error) outputPort;
			return DefaultResponse.badRequest().entity(error);
		}

		return DefaultResponse.serverError().entity("An unexpected error occurred");
	}

	@PUT
	@Path("single/{taskId}")
	public Response update(@PathParam("taskId") String taskId, Task task) {
		UUID id = UUID.fromString(taskId);
		TaskDTO inputPort = new TaskDTO(task.getTitle(), task.getDescription());
		IUpdateTaskService.OutputPort outputPort = this.updateTaskService.execute(new IUpdateTaskService.InputPort(id, inputPort));

		if (outputPort instanceof IUpdateTaskService.OutputPort.Ok) {
			return DefaultResponse.noContent();
		}

		if (outputPort instanceof IUpdateTaskService.OutputPort.NotFound) {
			IUpdateTaskService.OutputPort.NotFound notFound = (IUpdateTaskService.OutputPort.NotFound) outputPort;
			return DefaultResponse.notFound().entity(notFound);
		}

		if (outputPort instanceof IUpdateTaskService.OutputPort.Error) {
			IUpdateTaskService.OutputPort.Error error = (IUpdateTaskService.OutputPort.Error) outputPort;
			return DefaultResponse.badRequest().entity(error);
		}

		return DefaultResponse.serverError().entity("An unexpected error occurred");
	}

	@DELETE
	@Path("single/{taskId}")
	public Response delete(@PathParam("taskId") String taskId) {
		UUID id = UUID.fromString(taskId);
		IDeleteTaskService.OutputPort outputPort = this.deleteTaskService.execute(new IDeleteTaskService.InputPort(id));

		if (outputPort instanceof IDeleteTaskService.OutputPort.Ok) {
			IDeleteTaskService.OutputPort.Ok ok = (IDeleteTaskService.OutputPort.Ok) outputPort;
			return DefaultResponse.noContent();
		}

		if (outputPort instanceof IDeleteTaskService.OutputPort.NotFound) {
			IDeleteTaskService.OutputPort.NotFound notFound = (IDeleteTaskService.OutputPort.NotFound) outputPort;
			return DefaultResponse.notFound().entity(notFound);
		}

		if (outputPort instanceof IDeleteTaskService.OutputPort.Error) {
			IDeleteTaskService.OutputPort.Error error = (IDeleteTaskService.OutputPort.Error) outputPort;
			return DefaultResponse.badRequest().entity(error);
		}

		return DefaultResponse.serverError().entity("An unexpected error occurred");
	}
}
