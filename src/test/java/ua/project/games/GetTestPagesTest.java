package ua.project.games;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-statistic-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetTestPagesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAnonCountGreenTest() throws Exception {
        this.mockMvc.perform(get("/tests/countGreen"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnonIsPrevGameTest() throws Exception {
        this.mockMvc.perform(get("/tests/isPrevGame"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnonReactionGameTest() throws Exception {
        this.mockMvc.perform(get("/tests/reactionGame"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnonRepeatNumbersTest() throws Exception {
        this.mockMvc.perform(get("/tests/repeatNumbers"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnonRepeatSequenceTest() throws Exception {
        this.mockMvc.perform(get("/tests/repeatSequence"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getCountGreenTest() throws Exception {
        this.mockMvc.perform(get("/tests/countGreen"))
                .andExpect(content().string(containsString("admin")))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getIsPrevGameTest() throws Exception {
        this.mockMvc.perform(get("/tests/isPrevGame"))
                .andExpect(content().string(containsString("admin")))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getReactionGameTest() throws Exception {
        this.mockMvc.perform(get("/tests/reactionGame"))
                .andExpect(content().string(containsString("admin")))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getRepeatNumbersTest() throws Exception {
        this.mockMvc.perform(get("/tests/repeatNumbers"))
                .andExpect(content().string(containsString("admin")))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("admin")
    public void getRepeatSequenceTest() throws Exception {
        this.mockMvc.perform(get("/tests/repeatSequence"))
                .andExpect(content().string(containsString("admin")))
                .andExpect(status().isOk());
    }
}
