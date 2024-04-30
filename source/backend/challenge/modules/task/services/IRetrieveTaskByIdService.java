package backend.challenge.modules.task.services;

import backend.challenge.modules.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public interface IRetrieveTaskByIdService {
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
		class Ok implements OutputPort {
			private Task task;
			public Ok(Task task) {this.task = task;}
		}

		@Getter
		@Setter
		class NotFound implements OutputPort {
			private String message;
			public NotFound(String message) {
				this.message = message;
			}
		}

		@Getter
		@Setter
		class Error implements OutputPort {
			private String message;
			public Error(String message) {
				this.message = message;
			}
		}
	}

	OutputPort execute(InputPort inputPort);

}
