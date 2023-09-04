package com.wanted.nution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

@RequiredArgsConstructor
@Service
public class PageServiceImpl implements PageService{

    private final PageRepository pageRepository;

    @Override
    public int save() {
        return 0;
    }

    @Override
    public PagesDTO findOne(Long id) {
        List<PageSummary> subs = pageRepository.findSubPagesById(id);
        Pages page = pageRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        for (int i = 0; i < subs.size(); i++) {
            page.addSubs(subs.get(i));
        }

        return PagesDTO.toDTO(page);
    }

    @Override
    public Stack<PageSummary> findAllParent(Long id) {
        List<PageSummary> res = new ArrayList<>();

        while (id != 0) {
            PageSummary found = pageRepository.findParentById(id);
            res.add(found);
            System.out.println("found = " + found);
            id = found.getParent_id();

        }

        Stack<PageSummary> parents = new Stack<>();

        for (int i = res.size() - 1; i >= 0; i--) {
            parents.add(res.get(i));
        }
        return parents;
    }
}
