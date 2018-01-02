package app1.controller;

import app1.config.AppConfig;
import app1.config.RepoConfig;
import app1.config.ServiceConfig;
import app1.config.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class,WebSecurityConfig.class, ServiceConfig.class, RepoConfig.class})
public class AppControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setup(){
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(username = "Leszek")
//    @WithUserDetails(value = "Leszek")
    public void shouldReturnLoginViewWithTheRightUserAttribute() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(request().attribute("user","Leszek"))
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("WEB-INF/view/index.jsp"));
    }
    @Test
    public void shouldReturnLoginViewWithoutAuthentification() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("WEB-INF/view/login.jsp"));
    }
    @Test
    @WithMockUser
    public void shouldReturnUserViewWhileLogIn() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnAdminViewForAAdminUser() throws Exception {
        mockMvc.perform(get("/admin")).andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("WEB-INF/view/admin.jsp"));
    }
    @Test
    @WithMockUser
    public void shouldReturnForbiddenStatusForUserAccessingAdminView() throws Exception {
        mockMvc.perform(get("/admin")).andExpect(status().is(403));
    }
}
