package ua.project.games;

import antlr.build.Tool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-statistic-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticPageTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    public void getStatisticTest() throws Exception {
        this.mockMvc.perform(get("/statistic"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void getAnonStatisticTest() throws Exception {
        this.mockMvc.perform(get("/statistic"))
                .andExpect(status().is3xxRedirection());
    }
}
