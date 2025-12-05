package nl.bos.open_pdf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/{version}/pdf", version = "1")
public class PdfController {
    //curl localhost:8080/api/v1/pdf -X POST
    @PostMapping
    public String generatePdf() {
        return "hello world...";
    }

    //curl localhost:8080/api/v1/pdf
    @GetMapping
    public String ping() {
        return "Ping response";
    }
}
