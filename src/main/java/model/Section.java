package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class Section {
    @NonNull
    public int section_id;
    @NonNull
    public String name;
    @NonNull
    public String content;
    public String description;

}
