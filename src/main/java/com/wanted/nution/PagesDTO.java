package com.wanted.nution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagesDTO {
    @JsonIgnore
    private Long id;
    private String title;
    private String content;

    @JsonIgnore
    private Long parentId;

    private List<PageSummary> subs;

    @Setter
    private List<PageSummary> breadCrumbs;

    public static PagesDTO toDTO(Pages page) {
        return new PagesDTO(page.getId(), page.getTitle(), page.getContent(), page.getParentId(), page.getSubPages(), null);
    }
}
