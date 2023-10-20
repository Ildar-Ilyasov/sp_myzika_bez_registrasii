import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.File;
import javax.sound.sampled.*;

public class Main {

    private static final String IN_FILE_TXT = "C:\\musoc\\inFile.txt";
    private static final String OUT_FILE_TXT = "C:\\musoc\\outFile.txt";
    private static final String PATH_TO_MUSIC = "C:\\musoc\\muzika";
    private static final Pattern URL_PATTERN = Pattern.compile("\\s*(?<=data-url\\s?=\\s?\")[^>]*\\/*(?=\")");

    public static void main(String[] args) {
        try (BufferedReader inFile = new BufferedReader(new FileReader(IN_FILE_TXT));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(OUT_FILE_TXT))) {

            String urlStr;
            while ((urlStr = inFile.readLine()) != null) {
                writeMusicLinksFromUrl(outFile, urlStr);
            }
        } catch (IOException e) {
            System.out.println("Ошибка в чтении файлов: " + e.getMessage());
            e.printStackTrace();
        }

        try (BufferedReader musicFile = new BufferedReader(new FileReader(OUT_FILE_TXT))) {
            String music;
            int count = 0;
            while ((music = musicFile.readLine()) != null) {
                try {
                    downloadUsingHttpURLConn(music, PATH_TO_MUSIC + count + ".mp3");
                    count++;
                } catch (IOException e) {
                    System.out.println("Ошибка скачивания URL: " + music);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка музыки: " + OUT_FILE_TXT);
            e.printStackTrace();
        }
    }

    private static void writeMusicLinksFromUrl(BufferedWriter writer, String urlStr) throws IOException {
        URL url = new URL(urlStr);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String result = bufferedReader.lines().collect(Collectors.joining("\n"));
            Matcher matcher = URL_PATTERN.matcher(result);
            int i = 0;
            while (matcher.find() && i < 6) {
                writer.write(matcher.group() + System.lineSeparator());
                i++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка URL: " + urlStr);
            e.printStackTrace();
        }
    }

    private static void downloadUsingHttpURLConn(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int fileSize = httpURLConnection.getContentLength();

        System.out.println(urlStr);
        try (BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
             FileOutputStream fos = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int count;
            int downloaded = 0;
            double percentDownloaded = 0.0;

            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
                downloaded += count;
                percentDownloaded = (downloaded*100.0)/fileSize;

                String percent = String.format("%.2f", percentDownloaded);
                System.out.println("Скачалось " + percent + "%");
            }

            System.out.println("Файл скачан!!!!!!!!!!!!!!!!");
        }
        httpURLConnection.disconnect();
        try {
            String musicFile = file;

            File file_s = new File(musicFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file_s);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
