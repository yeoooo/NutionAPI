package com.wanted.nution.common;

import com.wanted.nution.Pages;
import com.wanted.nution.PageSummary;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Stack;

@Getter
@ToString
public class Session {

    @Setter
    private Stack<PageSummary> breadCrumbs;

    @Setter
    private Pages current;
    public Session() {
        this.breadCrumbs = new Stack();
    }

    public boolean updateBreadCrumbs(PageSummary sub) {

        if (sub.getParent_id().equals(current.getId())){
            this.breadCrumbs.add(sub);
            return true;
        } else if(current.getSubPages().contains(sub.getId())) {
            this.breadCrumbs.pop();
            return true;
        } else if (sub.getId().equals(this.current.getId())) {
            return true;
        }
        return false;
    }

}
