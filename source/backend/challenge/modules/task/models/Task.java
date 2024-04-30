package backend.challenge.modules.task.models;

import backend.challenge.modules.task.enums.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static backend.challenge.modules.task.enums.TaskStatus.PROGRESS;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
	private UUID id = UUID.randomUUID();
	private String title;
	private String description;
	private int progress = 0;
	private TaskStatus status = PROGRESS;
	private Date createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

}
