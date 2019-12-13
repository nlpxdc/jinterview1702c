package io.cjf.jinterviewback.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String pp) {
        return "aaa";
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("bitcoin_logo.png")) {
            final byte[] data = StreamUtils.copyToByteArray(fileInputStream);

            String filename = "mybitcoin.png";
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename="+ filename)
                    .body(data);
        }
    }
}
