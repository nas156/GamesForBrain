package ua.project.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.flogger.Flogger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.enums.TestType;

import java.util.logging.Logger;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateStatisticTest {

    @Autowired
    private MockMvc mockMvc;
    private TestStatisticDTO newStatisticDTO = new TestStatisticDTO(1, "pavel", TestType.IsPreviousTest.toString());


    @Test
    @WithUserDetails("aaa")
    public void usernamePostTest() throws Exception {
        this.mockMvc.perform(post("/createStatistic")
                .with(csrf().asHeader())
                .content(asJsonString(newStatisticDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithAnonymousUser
    public void anonymousPostTest() throws Exception{
        TestStatisticDTO anonymousStatistic = TestStatisticDTO.builder()
                .score(1)
                .username("anonymousUser")
                .testType(TestType.RepeatNumbersTest.toString())
                .build();
        this.mockMvc.perform(post("/createStatistic")
                .with(csrf().asHeader())
                .content(asJsonString(anonymousStatistic))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("pavel")
    public void getStatisticByUserAndType() throws Exception {
        this.mockMvc.perform(get("/createStatistic/statisticByUserForRepeatNumbers?type=RepeatNumbersTest"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void getStatisticByAnonUserAndType() throws Exception {
        this.mockMvc.perform(get("/createStatistic/statisticByUserForRepeatNumbers?type=RepeatNumbersTest"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/accounts/login"));
    }
}
