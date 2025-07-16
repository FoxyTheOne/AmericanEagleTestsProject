package ui.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountDetails {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String zipCode;
}
