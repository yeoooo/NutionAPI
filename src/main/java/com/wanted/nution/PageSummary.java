package com.wanted.nution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PageSummary {
    private Long id;
    private String title;
    private Long parent_id;

    public static PageSummary toSummary(Pages page) {
        return new PageSummary(page.getId(), page.getTitle(), page.getParentId());
    }

}
