package com.wanted.nution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pages {

    private Long id;
    private String title;
    private String content;
    private Long parentId;
    private List<PageSummary> subPages = new ArrayList<>();

    public Pages(Long id, String title, String content, Long parentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.parentId = parentId;
    }

    public void addSubs(PageSummary sub) {
        this.subPages.add(sub);
    }

    public static Pages toEntity(PagesDTO dto) {
        return new Pages(dto.getId(), dto.getTitle(), dto.getContent(), dto.getParentId(), dto.getSubs());
    }
}
