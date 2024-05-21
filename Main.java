import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Main {
    public static void main(String[] args) {
//        Path fileLocation = Paths.get("/home/sendilien/Downloads/Stand by Me (1986) [1080p]/Stand.by.Me.1986.1080p.BluRay.x264.YIFY.mp4");
//        Path fileLocation = Paths.get("/home/sendilien/Downloads/Frankl, Viktor Emil - Mans Search For Meaning (1995_1988, Pocket Books) - libgen.li.pdf");
        Path fileLocation = Paths.get("/home/sendilien/d/Documents/Books/Knowledge Encyclopedia By DK.pdf");

        byte[] inputFile;
        try {
            inputFile = Files.readAllBytes(fileLocation);
            System.out.println("input files size (length) : " + inputFile.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] compressed = compress(inputFile);
            System.out.println("the length of compressed file: " + compressed.length);
            byte[] decompressed = decompress(compressed);
            System.out.println("the length of decompressed file: " + decompressed.length);

            String inputString = "Baeldung helps developers explore the Java ecosystem and simply be better engineers. "
                    + "We publish to-the-point guides and courses, with a strong focus on building web applications, Spring, "
                    + "Spring Security, and RESTful APIs";

            compressed = compress(inputString.getBytes(StandardCharsets.UTF_8));
            System.out.println("the length of compressed file: " + compressed.length);
            decompressed = decompress(compressed);
            System.out.println("the length of decompressed file: " + decompressed.length);
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.setLevel(9);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        System.out.println("total in: " + deflater.getTotalIn());
        System.out.println("total out: " + deflater.getTotalOut());
        System.out.println("total bytes read: " + deflater.getBytesRead());
        System.out.println("total bytes written: " + deflater.getBytesWritten());

        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }

        return outputStream.toByteArray();
    }

    public static byte[] decompress(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedSize);
        }

        return outputStream.toByteArray();
    }
}
