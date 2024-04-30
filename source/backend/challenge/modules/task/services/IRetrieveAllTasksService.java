package backend.challenge.modules.task.services;

import backend.challenge.modules.task.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public interface IRetrieveAllTasksService {
	interface OutputPort extends Serializable {
		@Getter
		@Setter
		class Ok implements IRetrieveAllTasksService.OutputPort {
			private List<Task> tasks;
			public Ok(List<Task> tasks) {
				this.tasks = tasks;
			}
		}

		@Getter
		@Setter
		class Error implements IRetrieveAllTasksService.OutputPort {
			private String message;
			public Error(String message) {
				this.message = message;
			}
		}
	}

	OutputPort execute();
}
