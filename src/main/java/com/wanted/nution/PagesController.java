package com.wanted.nution;

import com.wanted.nution.common.Session;
import com.wanted.nution.common.SessionManager;
import com.wanted.nution.common.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PagesController {

    private final SessionManager sessionManager;
    private final PageService pageService;

    @GetMapping("/api/page/{id}")
    public ResponseEntity<PagesDTO> findOne(RequestEntity<Map> req, @PathVariable Long id) {


        String sessionKey = SessionUtil.getSessionKey(req);
        Session session = sessionManager.addSession(sessionKey);
        PagesDTO found = pageService.findOne(id);

        if (session.getCurrent() == null) {
            session.setCurrent(Pages.toEntity(found));
            session.setBreadCrumbs(pageService.findAllParent(id));

        }else if (!session.updateBreadCrumbs(new PageSummary(id, found.getTitle(), found.getParentId()))){
            session.setBreadCrumbs(pageService.findAllParent(id));
        }
        found.setBreadCrumbs(session.getBreadCrumbs());

        session.setCurrent(Pages.toEntity(found));

        log.info("[     PageController    ] Sessions --> {}", SessionManager.getSessions().toString());
        log.info("[     PageController    ] Sessions size --> {}", SessionManager.getSessions().size());

        return ResponseEntity.ok(found);
    }

    public ResponseEntity<String> save() {
        return ResponseEntity.ok("gd");
    }

    @GetMapping("/api/check/sessions")
    public ResponseEntity<String> checkSession(RequestEntity<Map> req) {
        log.info("[     PageController    ] Sessions --> {}", SessionManager.getSessions().toString());
        log.info("[     PageController    ] Sessions size --> {}", SessionManager.getSessions().size());
        return ResponseEntity.ok(SessionManager.getSessions().toString());
    }
}
