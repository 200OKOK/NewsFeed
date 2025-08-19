package org.example.newsfeed;

import org.example.newsfeed.like.controller.LikeController;
import org.example.newsfeed.like.service.LikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LikeService likeService;

    @Test
    void createLike_세션과_pathVariable_정상() throws Exception {
        Long feedId = 10L;
        Long userId = 1L;

        // when (필요하면 doNothing().when(likeService).createLike(...))
        Mockito.doNothing().when(likeService).createLike(userId, feedId);

        mockMvc.perform(post("/feeds/{feedId}/likes", feedId)   // 실제 매핑 경로에 맞춰 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("USERID", userId))         // ★ 세션 주입 포인트
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(String.valueOf(MediaType.APPLICATION_JSON)));

        // then
        Mockito.verify(likeService).createLike(userId, feedId);
    }

    @Test
    void createLike_세션없으면_400() throws Exception {
        Long feedId = 10L;

        mockMvc.perform(post("/feeds/{feedId}/likes", feedId))
                .andExpect(status().isBadRequest()); // @SessionAttribute(required=true) 기본값이라 400
    }
}
