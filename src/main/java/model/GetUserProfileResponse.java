package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
@AllArgsConstructor
public class GetUserProfileResponse {
    UserDetails userDetails;
}