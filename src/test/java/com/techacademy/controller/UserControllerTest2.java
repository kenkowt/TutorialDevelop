package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest2 {
    private MockMvc mockMvc;

    private final WebApplicationContext webApplicationContext;

    UserControllerTest2(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Secutiryを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @DisplayName("getList()メソッド")
    @WithMockUser
    void testGetList() throws Exception {
     // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/list")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("userlist")) // Modelの内容を確認
            .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
            .andExpect(view().name("user/list")) // viewの確認
            .andReturn(); // 内容の取得

        // userの検証
        Object userlistObj = result.getModelAndView().getModel().get("userlist");
        assertTrue(userlistObj instanceof List, "userlistはList型であるべき");
        @SuppressWarnings("unchecked")
        List<User> userlist = (List<User>) userlistObj;

        // 件数が3件であることを検証
        assertEquals(3, userlist.size(), "userlistの件数は3件であること");

        // userlistから1件ずつ取り出し、idとnameを検証する
        // ここではテストデータの例として、各ユーザーの期待値を設定してください
        User user0 = userlist.get(0);
        assertEquals(1, user0.getId(), "1件目のユーザーのIDは1であること");
        assertEquals("キラメキ太郎", user0.getName(), "1件目のユーザーの名前はキラメキ太郎であること");

        User user1 = userlist.get(1);
        assertEquals(2, user1.getId(), "2件目のユーザーのIDは2であること");
        assertEquals("キラメキ次郎", user1.getName(), "2件目のユーザーの名前はサンプルユーザー2であること");

        User user2 = userlist.get(2);
        assertEquals(3, user2.getId(), "3件目のユーザーのIDは3であること");
        assertEquals("キラメキ花子", user2.getName(), "3件目のユーザーの名前はサンプルユーザー3であること");
    }
}
