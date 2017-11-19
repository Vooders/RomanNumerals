package com.vooders.romanNumerals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Test
    public void contextLoads() throws Exception {
    }

    @Autowired
    private MainController controller;

    @Test
    public void controllerLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void outOfbounds() throws Exception {
        assertThat(controller.generate(-1000)).isEqualTo("Out of bounds");
        assertThat(controller.generate(-1)).isEqualTo("Out of bounds");
        assertThat(controller.generate(0)).isEqualTo("Out of bounds");
        assertThat(controller.generate(4000)).isEqualTo("Out of bounds");
    }

    @Test
    public void minMax() throws Exception {
        assertThat(controller.generate(1)).isEqualTo("I");
        assertThat(controller.generate(3999)).isEqualTo("MMMCMXCIX");
    }


    @Test
    public void someFours() throws Exception {
        assertThat(controller.generate(4)).isEqualTo("IV");
        assertThat(controller.generate(14)).isEqualTo("XIV");
        assertThat(controller.generate(24)).isEqualTo("XXIV");
        assertThat(controller.generate(40)).isEqualTo("XL");
        assertThat(controller.generate(400)).isEqualTo("CD");
    }

    @Test
    public void someNines() throws Exception {
        assertThat(controller.generate(9)).isEqualTo("IX");
        assertThat(controller.generate(19)).isEqualTo("XIX");
        assertThat(controller.generate(29)).isEqualTo("XXIX");
        assertThat(controller.generate(90)).isEqualTo("XC");
        assertThat(controller.generate(900)).isEqualTo("CM");
    }

    @Test
    public void illegalSyntax() throws Exception {
        // I, X, C and M cannot repeat more than 3 times so we'll define a pattern to check
        Pattern moreThanThree = Pattern.compile("(?i)([IXCM])\\1\\1{2,}");
        Matcher m;

        // We'll check each numeral against
        for (int i=1; i<4000; i++) {
            String numeral = controller.generate(i);
            m = moreThanThree.matcher(numeral);
            assertThat(m.matches()).isFalse();
            assertThat(numeral.contains("ID")).isFalse();
            assertThat(numeral.contains("XM")).isFalse();
            assertThat(numeral.contains("IC")).isFalse();
            assertThat(numeral.contains("IM")).isFalse();
            assertThat(numeral.contains("VM")).isFalse();
            assertThat(numeral.contains("VD")).isFalse();
            assertThat(numeral.contains("VL")).isFalse();
            assertThat(numeral.contains("IIIV")).isFalse();
            assertThat(numeral.contains("IIIX")).isFalse();
        }

    }
}
