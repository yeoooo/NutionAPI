package com.wanted.nution;

import java.util.Stack;

public interface PageService {
    int save();

    PagesDTO findOne(Long id);

    Stack<PageSummary> findAllParent(Long id);


}
