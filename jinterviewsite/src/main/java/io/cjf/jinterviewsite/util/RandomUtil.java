package io.cjf.jinterviewsite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Component
public class RandomUtil {

    @Autowired
    private SecureRandom secureRandom;

    public String getRandomStr(){
        byte[] bytes = secureRandom.generateSeed(2);
        String randomStr = DatatypeConverter.printHexBinary(bytes);
        return randomStr;
    }

}
