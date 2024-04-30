package backend.challenge.modules.task.services;


import backend.challenge.modules.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public interface IDeleteTaskService {
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	class InputPort {
		private UUID taskId;
	}

	interface OutputPort extends Serializable {
		@Getter
		@Setter
		class Ok implements IDeleteTaskService.OutputPort {
			private Task task;
			public Ok() {}
		}

		@Getter
		@Setter
		class NotFound implements IDeleteTaskService.OutputPort {
			private String message;
			public NotFound(String message) {
				this.message = message;
			}
		}

		@Getter
		@Setter
		class Error implements IDeleteTaskService.OutputPort {
			private String message;
			public Error(String message) {
				this.message = message;
			}
		}
	}

	OutputPort execute(InputPort inputPort);

}
