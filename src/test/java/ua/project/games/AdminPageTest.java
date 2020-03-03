package ua.project.games;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ua.project.games.repository.TestStatisticRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-statistic-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminPageTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestStatisticRepository testStatisticRepository;

    @Test
    @WithUserDetails("admin")
    public void adminGetAdminTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User")))
                .andExpect(content().string(containsString("TestType")));

    }

    @Test
    @WithUserDetails("user")
    public void userGetAdminTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithUserDetails("admin")
    public void adminGetAdminUserTest() throws Exception {
        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("email2@pavlo")))
                .andExpect(content().string(containsString("admin")));

    }

    @Test
    @WithUserDetails("admin")
    public void adminGetAdminTestTypeTest() throws Exception {
        this.mockMvc.perform(get("/admin/TestType"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ReactionTest")))
                .andExpect(content().string(containsString("RepeatNumbersTest")))
                .andExpect(content().string(containsString("countGreen")));


    }

    @Test
    @WithUserDetails("admin")
    public void validAddinUser() throws Exception {
        this.mockMvc.perform(post("/admin/User/add")
                .with(csrf().asHeader())
                .content(buildUrlEncodedFormEntity(
                        "username", "test",
                        "email", "test@test.com",
                        "password", "test1234",
                        "age", "2020-02-01"
                ))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @Test
    @WithUserDetails("admin")
    public void deleteUser() throws Exception {
        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("userToDelete")));

        this.mockMvc.perform(post("/admin/User/delete/3")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/User"));

        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(containsString("userToDelete"))))
                .andExpect(content().string(containsString("admin")));

    }

    @Test
    @WithUserDetails("admin")
    public void getAddUserTest() throws Exception {
        this.mockMvc.perform(get("/admin/User/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void notValidAddingUser() throws Exception {
        this.mockMvc.perform(post("/admin/User/add")
                .with(csrf().asHeader())
                .content(buildUrlEncodedFormEntity(
                        "username", "test",
                        "email", "test",
                        "password", "test",
                        "age", "2020-02-01"
                ))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/admin/User/add"));

    }

    @Test
    @WithUserDetails("admin")
    public void searchValidString() throws Exception {
        this.mockMvc.perform(get("/admin/User?search=a"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(containsString("email2@pavlo"))))
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    @WithUserDetails("admin")
    public void clearStaticticTest() throws Exception {
        this.mockMvc.perform(post("/admin/User/clearStat/1")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/admin/User"));
        Assert.assertFalse(testStatisticRepository.findAllByUser_Username("admin").isPresent());

    }

    @Test
    @WithUserDetails("admin")
    public void searchNotValidString() throws Exception {
        this.mockMvc.perform(get("/admin/User?search=abcd"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("email2@pavlo")))
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    @WithUserDetails("admin")
    public void getUpdateUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/User/update/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("email@pavlo")))
                .andExpect(content().string(containsString("$2a$10$P/J45xkOuXUa5BvTAYizjuGqRGGlLBbUrqPwM/VVTdctzot6.C5aq")))
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    @WithUserDetails("admin")
    public void postUpdateUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("userToUpdate")));

        this.mockMvc.perform(post("/admin/User/update/4")
                .with(csrf().asHeader())
                .content(buildUrlEncodedFormEntity(
                        "username", "updated",
                        "email", "test",
                        "password", "test",
                        "age", "2020-02-01"
                ))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/admin/User"));

        this.mockMvc.perform(get("/admin/User"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(containsString("userToUpdate"))))
                .andExpect(content().string(containsString("updated")));

    }

    @Test
    @WithUserDetails("admin")
    public void getTestTypePage() throws Exception {
        this.mockMvc.perform(get("/admin/TestType"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("repeatSequence")));
    }

    @Test
    @WithUserDetails("admin")
    public void postDeleteTestType() throws Exception {
        this.mockMvc.perform(post("/admin/TestType/delete/1")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/admin/TestType"));

        this.mockMvc.perform(get("/admin/TestType"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ACTIVATE")));
    }

    @Test
    @WithUserDetails("admin")
    public void postActivateTestType() throws Exception {
        this.mockMvc.perform(post("/admin/TestType/delete/1")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(post("/admin/TestType/activate/1")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/admin/TestType"));

        this.mockMvc.perform(get("/admin/TestType"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(containsString("ACTIVATE"))));

    }


    private String buildUrlEncodedFormEntity(String... params) {
        if ((params.length % 2) > 0) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

}
