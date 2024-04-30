package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public interface IUpdateTaskProgressService {
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	class InputPort {
		private TaskProgressDTO taskProgressDTO;
	}

	interface OutputPort extends Serializable {
		@Getter
		@Setter
		class Ok implements IUpdateTaskProgressService.OutputPort {
			public Ok() {}
		}

		@Getter
		@Setter
		class NotFound implements IUpdateTaskProgressService.OutputPort {
			private String message;
			public NotFound(String message) {
				this.message = message;
			}
		}

		@Getter
		@Setter
		class Error implements IUpdateTaskProgressService.OutputPort {
			private String message;
			public Error(String message) {
				this.message = message;
			}
		}
	}

	OutputPort execute(InputPort inputPort);
}
