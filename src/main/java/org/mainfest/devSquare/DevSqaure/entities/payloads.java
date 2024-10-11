package org.mainfest.devSquare.DevSqaure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class payloads {
    private  String mentioned_name;
    private  Querry querry;
}
