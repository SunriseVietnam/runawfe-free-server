package ru.runa.wf.web.ftl.component;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BenchRunner {

    private String testString;
    @Setup
    public void setup() {
        final String testString1 = "ggfdsgfsgfdsgf\ndsgfdsgfsgfhjhlkhkj;lhfgjhg\ngfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkd\"njkgf][ndjgkfndjkgnfdjk\"gnjkfdnjk" +
                "ggfdsgfsgfdsgf\ndsgfdsgfsgfhjhlkhkjlhfgjhg\ngfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkd\"njkgf][ndjgkfndjkgnfdjk\"gnjkfdnjk" +
                "ggfdsgfsgfdsgfdsgfdsgfsgfhjhlkhkjlhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdg\nfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkfdnjk +ggfdsgfsgfdsgf" +
                "dsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndj\ngkfndjkgnfdjkgnjkfdnjk" +
                "ggfdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjh\nggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfd\ngfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfd\nsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdsgfsgfdsgfdsgfdsgfsgfhjh\"lkhkj;lhfg\"jhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrf\nvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy{}89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgf\ndgfdgfd[]{}gfdgfdgfddgfdgfdgfdgf\ndgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjk{}gnfjdkngjfkdngjfkdgnjfdkn{}jk\nngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdsgfsgfdsgfdsgfdsg\nfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfd\ngfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdsgfsgfdsgfdsgfdsgfs\ngfhjhl khkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkf\ndsy89743r843{}u\nr434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgj hggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgf\ndgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "ggfdfff{}{}sgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfd\ngfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfd[]jkgnfjdkngjfkd{}ngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfs\ngfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfo\nhrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjf[]kdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgf\nhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgn\nfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdg\nfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsg fsgfdsgfdsgfdsgfsgfhj\nhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}g\nfdgfdgfddgfdgfdgfdgfdgfdgfdga\ndjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjk\ngnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgfh jhlkh\nkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgf\"dgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}v\ngfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfs gfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgf\ndgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkng\njfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhf\ngjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadj\nfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhn\nfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfd{}jkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf" +
                "gg  fdsgfsgfdsgfdsgfdsgfsgfhjhlkhkj;lhfgjhggfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkdnjkgf][ndjgkfndjkgnfdjkgnjkf";

        testString = testString1 + testString1 + testString1 + testString1 + testString1 +testString1 +testString1 +testString1 +testString1;
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2)
    @Measurement(iterations = 25)
    @Fork(value = 1, warmups = 1)
    @Threads(4)
    public String benchmarkOld() {
        return testString.replaceAll("\"", "'").replaceAll("\n", "").replace("[]", "{}");
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2)
    @Measurement(iterations = 25)
    @Fork(value = 1, warmups = 1)
    @Threads(4)
    public String benchmarkNew() {
        return EditUserTypeList.getReplace(testString);
    }
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}