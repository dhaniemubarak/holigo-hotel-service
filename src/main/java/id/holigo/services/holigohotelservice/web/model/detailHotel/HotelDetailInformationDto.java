package id.holigo.services.holigohotelservice.web.model.detailHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailInformationDto {
    
    public String headerUrl;
    public String illustration;
    public String title;
    public String body;
}
