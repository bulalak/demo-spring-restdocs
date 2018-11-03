package sk.vub.demo.demorestdoc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@AutoConfigureMockMvc
public class DemoRestdocApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPersons() throws Exception {
        this.mockMvc.perform(get("/persons")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Janko")))
                .andExpect(jsonPath("$[0].lastName", is("Hrasko")))
                .andExpect(jsonPath("$[1].firstName", is("Jozko")))
                .andExpect(jsonPath("$[1].lastName", is("Mrkvicka")))
                .andDo(document("getPersons",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].firstName").description("first name of person"),
                                fieldWithPath("[].lastName").description("last name of person")
                        )
                        , SpringCloudContractRestDocs.dslContract()
                ))
        ;
    }

    @Test
    public void getPersons2() throws Exception {
        this.mockMvc.perform(get("/persons")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].firstName").isNotEmpty())
                .andExpect(jsonPath("$[*].lastName").isNotEmpty())
        ;
    }

}
