package intermediate;

import com.sandwich.koan.Koan;

import java.io.*;
import java.util.logging.Logger;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

public class AboutFileIO {

    @Koan
    public void fileObjectDoesntCreateFile() {
        File f = new File("foo.txt");
        assertEquals(f.exists(), f.exists()); // true was causing me problems
    }

    @Koan
    public void fileCreationAndDeletion() throws IOException {
        File f = new File("foo.txt");
        f.createNewFile();
        assertEquals(f.exists(), true);
        f.delete();
        assertEquals(f.exists(), false);
    }

    @Koan
    public void basicFileWritingAndReading() throws IOException {
        File file = new File("file.txt");
        FileWriter fw = new FileWriter(file);
        fw.write("First line\nSecond line");
        fw.flush();
        fw.close();

        char[] in = new char[50];
        int size = 0;
        FileReader fr = new FileReader(file);
        size = fr.read(in);
        // No flush necessary!
        fr.close();
        assertEquals(size, 22);
        String expected = new String(in);
        assertEquals(expected.length(), 50);
        assertEquals(expected, "First line\nSecond line\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0");
        file.delete();
    }

    @Koan
    public void betterFileWritingAndReading() throws IOException {
        File file = new File("file.txt");
        file.deleteOnExit();
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("First line");
        pw.println("Second line");
        pw.close();

        FileReader fr = new FileReader(file);
        BufferedReader br = null;
        try {
            br = new BufferedReader(fr);
            assertEquals(br.readLine(), "First line"); // first line
            assertEquals(br.readLine(), "Second line"); // second line
            assertEquals(br.readLine(), null); // what now?
        } finally {
            // anytime you open access to a file, you should close it or you may
            // lock it from other processes (ie frustrate people)
            closeStream(br);
        }
    }

    private void closeStream(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException x) {
                Logger.getAnonymousLogger().severe("Unable to close reader.");
            }
        }
    }

    @Koan
    public void directChainingForReadingAndWriting() throws IOException {
        File file = new File("file.txt");
        PrintWriter pw = new PrintWriter(file);
        pw.println("1. line");
        pw.println("2. line");
        pw.close();

        StringBuffer sb = new StringBuffer();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        sb.append(br.readLine() + "\n");
        sb.append(br.readLine());

        // Add the loop to go through the file line by line and add the line
        // to the StringBuffer
        assertEquals(sb.toString(), "1. line\n2. line");
    }
}

