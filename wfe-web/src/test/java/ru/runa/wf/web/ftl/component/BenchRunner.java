package ru.runa.wf.web.ftl.component;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class BenchRunner {

    private String testString;
    @Setup
    public void setup() {
        testString = "ggfdsgfsgfdsgf\ndsgfdsgfsgfhjhlkhkj;lhfgjhg\ngfgfdgfdgfdgfdgfd[]{}gfdgfdgfddgfdgfdgfdgfdgfdgfdgadjfohrfvnjknjxvnkdfs8778943y2rhrnjgfjksdfhnfgjkfdsy89743r843{}ur434r3{}vgfdgfdndjkgnfdjkgnfjdkngjfkdngjfkdgnjfdkn{}jkngjfkd\"njkgf][ndjgkfndjkgnfdjk\"gnjkfdnjk";
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    @Fork(value = 2, warmups = 1)
    @Threads(4)
    public String benchmarkOld() {
        return testString.replaceAll("\"", "'").replaceAll("\n", "").replace("[]", "{}");
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    @Fork(value = 2, warmups = 1)
    @Threads(4)
    public String benchmarkNew() {
        return EditUserTypeList.getReplace(testString);
    }
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}