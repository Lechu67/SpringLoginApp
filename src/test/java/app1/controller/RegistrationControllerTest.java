package app1.controller;

import app1.config.AppConfig;
import app1.config.RepoConfig;
import app1.config.ServiceConfig;
import app1.config.WebSecurityConfig;
import app1.model.UserEntity;
import app1.service.UserDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class,WebSecurityConfig.class, ServiceConfig.class, RepoConfig.class})
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private UserDetailService service;

    @Mock
    private Validator validator;

    @Mock
    private BindingResult bindingResultMock;

    //@InjectMocks
    private RegistrationController controller;


    @Before
    public void setup(){
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
        controller = new RegistrationController(service,validator); // injectMocks no more needed, injecting dependecies through constructor
    }
    @Test
    public void shouldReturnSignUpView() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(forwardedUrl("WEB-INF/view/signup.jsp"));
    }
    /*@Test
    public void shouldReturnLoginViewWithTheRightUserAttribute() throws Exception {
        mockMvc.perform(post("/signup").param("username","Waldek").param("password","pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }*/
    @Test
    public void shouldReturnSignUpViewWithErrorFieldRequired() throws Exception {
        mockMvc.perform(post("/signup").param("username","").param("password",""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeHasFieldErrorCode("usercustom","username","username.empty"))
                .andExpect(model().attributeHasFieldErrorCode("usercustom","password","password.empty"))
                .andExpect(forwardedUrl("WEB-INF/view/signup.jsp"));
    }
    /*@Test
    @WithMockUser(value = "Heniek")
    public void shouldReturnSignUpViewWithErrorUserExist() throws Exception {
        mockMvc.perform(post("/signup").param("username","Heniek").param("password","Heniek"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeHasFieldErrorCode("usercustom","username","username.exist"))
                .andExpect(forwardedUrl("WEB-INF/view/signup.jsp"));
    }*/
    @Test
    public void shouldReturnSignUpViewWhenHasError() throws Exception {
        when(bindingResultMock.hasErrors()).thenReturn(true);
        assertEquals("signup",controller.addUser(new UserEntity(), bindingResultMock));
    }
    @Test
    public void shouldReturnRedirectToRootWhenNoError() throws Exception {
        UserEntity user = new UserEntity();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        String result = controller.addUser(user, bindingResultMock);
        verify(service).addUser(eq(user));
        assertEquals("redirect:/",result);
    }
}