package id.holigo.services.common.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    static final long serialVersionUID = -5815566940065181210L;
    @Null
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Phone number is required")
    // @UniquePhoneNumber
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email
    // @UniqueEmail
    private String email;

    private EmailStatusEnum emailStatus;

    private AccountStatusEnum accountStatus;

    private String mobileToken;

    private String type;

    private Long registerId;

    List<UserDeviceDto> userDevices;

}