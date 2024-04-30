package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.infra.http.views.TaskProgressView;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@Path("tasks/progress")
public class TaskProgressController {
	private final IUpdateTaskProgressService updateTaskProgressService;

	@Inject
	public TaskProgressController(final IUpdateTaskProgressService updateTaskProgressService) {
		this.updateTaskProgressService = updateTaskProgressService;
	}

	@PUT
	@Path("single/{taskId}")
	public Response updateProgress(@PathParam("taskId") String taskId, TaskProgressView taskProgressView) {
		UUID id = UUID.fromString(taskId);
		TaskProgressDTO taskProgressDTO = new TaskProgressDTO(id, taskProgressView.getProgress());
		IUpdateTaskProgressService.InputPort inputPort = new IUpdateTaskProgressService.InputPort(taskProgressDTO);

		IUpdateTaskProgressService.OutputPort outputPort = this.updateTaskProgressService.execute(inputPort);

		if (outputPort instanceof IUpdateTaskProgressService.OutputPort.Ok) {
			return DefaultResponse.noContent();
		}

		if (outputPort instanceof IUpdateTaskProgressService.OutputPort.NotFound) {
			IUpdateTaskProgressService.OutputPort.NotFound notFound = (IUpdateTaskProgressService.OutputPort.NotFound) outputPort;
			return DefaultResponse.notFound().entity(notFound);
		}

		if (outputPort instanceof IUpdateTaskProgressService.OutputPort.Error) {
			IUpdateTaskProgressService.OutputPort.Error error = (IUpdateTaskProgressService.OutputPort.Error) outputPort;
			return DefaultResponse.badRequest().entity(error);
		}

		return DefaultResponse.serverError().entity("An unexpected error occurred");
	}

}
