package ru.runa.wf.web.ftl.component;

import org.junit.Test;

import static org.junit.Assert.*;

public class EditUserTypeListTest {

    @Test
    public void getReplaceTest() {
        String testString = "ggfdsgfsgfdsgf\ndsgfdsgfsgfhjhlkhkj;lhfgjhg\ngfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkd\"njkgf][ndjgkfndjkgnfdjk\"gnjkfdnjk";
        String resultString = "ggfdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd{}{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkd'njkgf][ndjgkfndjkgnfdjk'gnjkfdnjk";
        assertEquals(resultString, EditUserTypeList.getReplace(testString));
    }
    @Test
    public void getReplaceTest1() {
        String input = "Line1\nLine2\nLine3";
        String output = "Line1Line2Line3";

        assertEquals(output, EditUserTypeList.getReplace(input));
    }

    @Test
    public void getReplaceTest2() {
        String input = "str \"str\" str";
        String output = "str 'str' str";

        assertEquals(output, EditUserTypeList.getReplace(input));
    }

    @Test
    public void getReplaceTest3() {
        String input = "[str[]str[]str]";
        String output = "[str{}str{}str]";

        assertEquals(output, EditUserTypeList.getReplace(input));
    }

    @Test
    public void getReplaceTest4() {
        String input = "line1\nLine2\"str\"\n[]";
        String output = "line1Line2'str'{}";

        assertEquals(output, EditUserTypeList.getReplace(input));
    }
}
