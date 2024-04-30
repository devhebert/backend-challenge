package backend.challenge.modules.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@AllArgsConstructor
public class TaskProgressDTO {

	private UUID id;
	private int progress;

}
