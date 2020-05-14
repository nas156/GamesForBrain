package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class GameCreationService {

    public void createGameTemplate(String templateName, MultipartFile[] jsFiles, String title){
        try {
            File myObj = new File("src/main/resources/templates/games/" + templateName + ".html");
            if (myObj.createNewFile()) {
                log.warn("File created: " + myObj.getName());
            } else {
                log.warn("File already exists.");
            }
        } catch (IOException e) {
            log.warn("An error occurred.");
        }

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/templates/games/" + templateName + ".html");
            myWriter.write("<!DOCTYPE html> <html lang=\"en\" xmlns:th=\"http://www.w3.org/1999/xhtml\"> <head> <meta charset=\"utf-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"> <meta name=\"username\" th:content=\"${session.username}\"/> <meta name=\"_csrf\" th:content=\"${_csrf.token}\"/> <!-- default header name is X-CSRF-TOKEN --> <meta name=\"_csrf_header\" th:content=\"${_csrf.headerName}\"/> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"> <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\"> <link rel=\"stylesheet\" th:href=\"@{/css/main.css}\"> <script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script> <script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script><script type=\"text/javascript\" th:src=\"@{/js/statistics/commonFunctions.js}\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/p5.js/0.9.0/p5.js\"></script>");
            myWriter.write("<title>" + title + "</title>");
            Arrays.stream(jsFiles).forEach(f -> {
                try {
                    myWriter.write("<script th:src=\"@{/js/" + title + "/" + f.getOriginalFilename() +"}\"" +"></script>");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            myWriter.write("</head> <body th:replace = \"~{games/repeatSequence :: test_body}\"></body> </html>");
            myWriter.close();
            log.info("Successfully wrote to the file.");
        } catch (IOException e) {
            log.warn("An error occurred.");
            e.printStackTrace();
        }
    }
}
