package Backend.teampple.domain.stages.dto.request;

import Backend.teampple.domain.stages.dto.StageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostStageDto {
    @Valid
    @NotNull
    @ApiModelProperty(notes = "단계", required = true)
    List<StageDto> stages;
}
