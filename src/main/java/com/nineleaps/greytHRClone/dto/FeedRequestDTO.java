package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.EventType;
import com.nineleaps.greytHRClone.model.FeedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDTO {

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private FeedType feedType;

    private String name;


}
