package id.holigo.services.holigohotelservice.web.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestFareDto {
    private UUID inquiryId;
    private UUID roomId;
}
