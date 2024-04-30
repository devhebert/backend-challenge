package backend.challenge.modules.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@AllArgsConstructor
public class TaskDTO {
	private String title;
	private String description;

}
